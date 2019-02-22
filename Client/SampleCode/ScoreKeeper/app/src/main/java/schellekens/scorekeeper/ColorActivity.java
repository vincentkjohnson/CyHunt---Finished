package schellekens.scorekeeper;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class ColorActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    public static String TEAM_COLOR = "TEAM_COLOR";

    SeekBar mRed;
    SeekBar mGreen;
    SeekBar mBlue;
    TextView tvRed;
    TextView tvGreen;
    TextView tvBlue;
    TextView tvHexColor;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        mRed = (SeekBar) findViewById(R.id.sbRed);
        mGreen = (SeekBar) findViewById(R.id.sbGreen);
        mBlue = (SeekBar) findViewById(R.id.sbBlue);
        tvRed = (TextView) findViewById(R.id.tvRed);
        tvGreen = (TextView) findViewById(R.id.tvGreen);
        tvBlue = (TextView) findViewById(R.id.tvBlue);
        tvHexColor = (TextView) findViewById(R.id.tvColorHex);
        btnSave = (Button) findViewById(R.id.btnSave);

        Intent intent = getIntent();
        if (intent.hasExtra(TEAM_COLOR)) {
            initColors(intent);
        }

        mRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvRed.setText(Integer.toString(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setColorTextview();
            }
        });

        mGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvGreen.setText(Integer.toString(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setColorTextview();
            }
        });

        mBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvBlue.setText(Integer.toString(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setColorTextview();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSave.setEnabled(false);  // No double taps
                Intent intent = getIntent();
                intent.putExtra(TEAM_COLOR, getColorInt());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void setColorTextview() {
        String red = this.tvRed.getText().toString();
        String green = this.tvGreen.getText().toString();
        String blue = this.tvBlue.getText().toString();

        red = Integer.toHexString(Integer.parseInt(red));
        green = Integer.toHexString(Integer.parseInt(green));
        blue = Integer.toHexString(Integer.parseInt(blue));

        red = this.padLeft(red, "0", 2);
        green = this.padLeft(green, "0", 2);
        blue = this.padLeft(blue, "0", 2);

        String color = "#" + red + green + blue;
        this.tvHexColor.setText(color.toUpperCase());

        try {
            this.tvHexColor.setBackgroundColor(Color.parseColor(color));
        } catch (Exception ex){
            // Sometimes the color isn't right...yet.  Log it and move on.
            Log.e(TAG, "Failed to parse '" + color + "' for the following reason: " + ex.getMessage());
        }
    }

    private void initColors(Intent intent){
        int teamColor = intent.getIntExtra(TEAM_COLOR, Integer.MIN_VALUE);
        if (teamColor > Integer.MIN_VALUE) {
            String color = "#" + Integer.toHexString(teamColor);

            String red = color.substring(3, 5);
            String green = color.substring(5,7);
            String blue = color.substring(7,9);

            this.tvRed.setText(Integer.toString(Integer.parseInt(red, 16)));
            this.tvGreen.setText(Integer.toString(Integer.parseInt(green, 16)));
            this.tvBlue.setText(Integer.toString(Integer.parseInt(blue, 16)));

            this.mRed.setProgress(Integer.parseInt(red, 16));
            this.mGreen.setProgress(Integer.parseInt(green, 16));
            this.mBlue.setProgress(Integer.parseInt(blue, 16));

            setColorTextview();
        }
    }

    private String padLeft(String str, String padChar, int newLength){
        StringBuilder result = new StringBuilder();
        result.append(str);

        while(result.length() < newLength){
            result.insert(0, padChar);
        }

        return result.toString();
    }

    private int getColorInt() {
        String color = "#" + Integer.toHexString(Integer.parseInt(this.tvRed.getText().toString()));
        color += Integer.toHexString(Integer.parseInt(this.tvGreen.getText().toString()));
        color += Integer.toHexString(Integer.parseInt(this.tvBlue.getText().toString()));

        // Parse it first so we can see what it is an debug
        int result = Color.parseColor(color);
        return result;
    }
}
