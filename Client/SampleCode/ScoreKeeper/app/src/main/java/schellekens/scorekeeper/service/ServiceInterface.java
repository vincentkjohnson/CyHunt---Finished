package schellekens.scorekeeper.service;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ${PACKAGE_NAME} Created by bschellekens on 10/27/2017.
 */

public class ServiceInterface extends AsyncTask<URL, Integer, String> {

    private ResultHandler mResultHandler;
    private static final String TAG = ServiceInterface.class.getSimpleName();

    public ServiceInterface(ResultHandler handler){
        this.mResultHandler = handler;
    }

    @Override
    protected String doInBackground(URL... urls) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) urls[0].openConnection();
            return readStream(httpURLConnection.getInputStream());
        } catch (java.io.IOException e) {
            Log.e(TAG, e.toString());
        }

        return null;
    }

    protected void onPostExecute(String result) {
        mResultHandler.handleResult(result);
    }

    private String readStream(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String nextLine = "";
            while ((nextLine = bufferedReader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (java.io.IOException e) {
            Log.e(TAG, e.toString());
        }

        return sb.toString();
    }

    public interface ResultHandler {
        public void handleResult(String result);
    }
}