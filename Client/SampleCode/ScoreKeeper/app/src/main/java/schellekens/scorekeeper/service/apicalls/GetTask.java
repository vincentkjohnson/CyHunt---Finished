package schellekens.scorekeeper.service.apicalls;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * schellekens.scorekeeper.schellekens.scorekeeper.service.schellekens.scorekeeper.service.apicalls Created by bschellekens on 11/29/2017.
 */

public class GetTask extends AsyncTask<String, Integer, String> {
    private GetResultHandler mGetResultHandler;
    private final String TAG = this.getClass().getSimpleName();

    public GetTask(GetResultHandler getResultHandler) {
        this.mGetResultHandler = getResultHandler;
    }

    @Override
    protected String doInBackground(String... params) {
        // If there is only one parameter, it's the URL
        URL url;
        try {
            if (params.length == 1) {

                url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setUseCaches(false);
                return readStream(httpURLConnection.getInputStream());
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Malformed URL: " + e.getMessage());
        } catch (java.io.IOException e) {
            Log.e(TAG, "IO Exception: " + e.getMessage());
        }

        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        mGetResultHandler.getResult(result);
    }

    private String readStream(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String nextLine = "";

            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public interface GetResultHandler {
        void getResult(String result);
    }
}