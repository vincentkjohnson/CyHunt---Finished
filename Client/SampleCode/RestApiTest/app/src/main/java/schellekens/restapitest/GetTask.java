package schellekens.restapitest;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Handles HTTP communication
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
        String body;
        try {
            if (params.length == 3) {

                url = new URL(params[0]);
                body = params[1];

                // Setup connection
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(params[2]);
                conn.setRequestProperty("Content-length", body.getBytes().length + "");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Set content body
                OutputStream os = conn.getOutputStream();
                os.write(body.getBytes("UTF-8"));
                os.close();

                conn.connect();

                int responseCode = conn.getResponseCode();

                if(responseCode == 200){
                    this.mGetResultHandler.getResult(readStream(conn.getInputStream()));
                } else {
                    this.mGetResultHandler.getResult("Response Code: " + Integer.toString(responseCode));
                }
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