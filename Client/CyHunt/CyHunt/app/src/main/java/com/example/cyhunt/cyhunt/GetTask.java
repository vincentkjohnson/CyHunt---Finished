package com.example.cyhunt.cyhunt;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetTask extends AsyncTask<String, Integer, String> {

    private GetResultHandler mGetResultHandler;
    private final String TAG = this.getClass().getSimpleName();

    public GetTask(GetResultHandler getResultHandler) {
        this.mGetResultHandler = getResultHandler;
    }



    @Override
    protected String doInBackground(String... strings) {
        try {
            if (strings.length == 3) {
                URL url = new URL(strings[0]);
                String body = strings[1];
                String method = strings[2];

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(method);
                connection.setRequestProperty("Accept", "application/json");
                connection.setUseCaches(false);

                if (body.length() > 0 && !method.equals("GET")) {
                    connection.setRequestProperty("Content-length", body.getBytes().length + "");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    OutputStream output = connection.getOutputStream();
                    output.write(body.getBytes("UTF-8"));
                    output.close();
                    connection.connect();
                }

                int responseCode = connection.getResponseCode();

                if(responseCode == 200) {
                    this.mGetResultHandler.getResult(readStream(connection.getInputStream()));
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

    private String readStream(InputStream input) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String nextLine = "";

            while ((nextLine = reader.readLine())  != null) {
                builder.append(nextLine);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    public interface GetResultHandler {
        void getResult(String result);
    }

}
