package schellekens.scorekeeper.service.apicalls;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * schellekens.scorekeeper.service.apicalls Created by bschellekens on 12/5/2017.
 */

public class PostTask extends AsyncTask<PostData, Integer, String> {
    private PostResultHandler mPostResultHandler;
    private final String TAG = this.getClass().getSimpleName();

    public PostTask(PostResultHandler postResultHandler) {
        this.mPostResultHandler = postResultHandler;
    }

    @Override
    protected String doInBackground(PostData... params) {
        try {
            PostData pData = params[0];
            URL url = new URL(pData.getUrl());
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            StringBuffer sb = new StringBuffer();
            String curLine = "";

            urlConn.setDoInput(true);       // We need to write
            urlConn.setDoOutput(true);      // We need to read
            urlConn.setUseCaches(false);    // We don't want to use cached results
            urlConn.setInstanceFollowRedirects(false);  // We don't want to allow redirects
            urlConn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConn.setRequestMethod("POST");

            urlConn.connect();

            OutputStreamWriter writer = new OutputStreamWriter(urlConn.getOutputStream(), "UTF-8");
            writer.write(pData.getJsonData());
            writer.close();

            int statusCode = urlConn.getResponseCode();

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            while ((curLine = reader.readLine()) != null) {
                sb.append(curLine);
            }

            reader.close();

            // Log the response for debug purposes, we aren't doing anything with it
            Log.v(TAG, "Response from POST: " + sb.toString());

            return Integer.toString(statusCode);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Malformed URL: " + e.getMessage());
        } catch (java.io.IOException e) {
            Log.e(TAG, "IO Exception: " + e.getMessage());
        }

        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        mPostResultHandler.getResult(result);
    }

    public interface PostResultHandler {
        void getResult(String result);
    }
}