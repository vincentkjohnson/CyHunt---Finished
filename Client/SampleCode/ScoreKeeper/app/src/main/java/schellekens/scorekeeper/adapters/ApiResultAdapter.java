package schellekens.scorekeeper.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import schellekens.scorekeeper.R;
import schellekens.scorekeeper.service.types.ApiResult;
import schellekens.scorekeeper.service.types.Game;
import schellekens.scorekeeper.service.types.Team;

/**
 * schellekens.scorekeeper.viewmodels Created by bschellekens on 11/30/2017.
 */

public class ApiResultAdapter extends ArrayAdapter<ApiResult> {
    private final String TAG = this.getClass().getSimpleName();

    private int _resourceId;
    private Context _context;
    private List<? extends ApiResult> _data;
    private int _myTeamId;

    public ApiResultAdapter(@NonNull Context context,
                            int resource,
                            @NonNull List<? extends ApiResult> objects,
                            int myTeamId) {
        super(context, resource, (List<ApiResult>)objects);

        this._resourceId = resource;
        this._context = context;
        this._data = objects;
        this._myTeamId = myTeamId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ApiResultViewModel holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) this._context).getLayoutInflater();
            row = inflater.inflate(this._resourceId, parent, false);

            //create a new ViewModel and set it to the fields the row in the list view
            holder = new ApiResultViewModel();

            holder.caption = (TextView)row.findViewById(R.id.tvCaption);
            holder.id = (TextView)row.findViewById(R.id.tvId);

            row.setTag(holder);
        } else {
            holder = (ApiResultViewModel) row.getTag();
        }

        //get the current position from the list
        ApiResult record = this._data.get(position);

        // Set the text for the row
        switch (record.getDataType()){
            case Game:
                Game g = (Game)record;
                String teamName = g.getHomeTeamName();
                if(g.getHomeTeamId() == this._myTeamId){
                    teamName = g.getVisitingTeamName();
                }

                String dateString = getDateCurrentTimeZone(g.getKickOff());

                holder.caption.setText(dateString + "\nvs\n" + teamName);
                holder.id.setText(Integer.toString(g.getId()));
                break;
            case Team:
                Team t = (Team)record;
                holder.caption.setText(t.getName());
                holder.id.setText(Integer.toString(t.getId()));
                break;
            default:
                holder.caption.setText("???");
                holder.id.setText("???");
                break;
        }

        return row;
    }

    public  String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        }catch (Exception e) {
        }
        return "";
    }

    /**
     * A class to represent the fields in the row layout
     */
    static class ApiResultViewModel
    {
        TextView caption;
        TextView id;
    }

    public int getPositionForId(int id){
        for(int i = 0; i < this._data.size(); i++){
            if(this._data.get(i).getDataType() == ApiResult.DataTypes.Game){
                Game itm = (Game)this._data.get(i);
                if(itm.getId() == id) return i;
            } else if (this._data.get(i).getDataType() == ApiResult.DataTypes.Team){
                Team itm = (Team)this._data.get(i);
                if(itm.getId() == id) return i;
            }
        }

        return -1;
    }
}
