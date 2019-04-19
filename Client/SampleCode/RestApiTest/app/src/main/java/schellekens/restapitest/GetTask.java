package schellekens.restapitest;

import android.os.AsyncTask;
import android.util.Log;
import schellekens.restapitest.models.ResponseWrapper;

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
public class GetTask extends AsyncTask<String, Integer, ResponseWrapper> {
    private GetResultHandler mGetResultHandler;
    private final String TAG = this.getClass().getSimpleName();

    public GetTask(GetResultHandler getResultHandler) {
        this.mGetResultHandler = getResultHandler;
    }

    @Override
    protected ResponseWrapper doInBackground(String... params) {
        try {
            if (params.length == 4) {

                URL url = new URL(params[0]);
                String body = params[1];
                String method = params[2];
                String returnType = params[3];
                int rType = Integer.parseInt(returnType);

                // Setup connection
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(method);
                conn.setRequestProperty("Accept", "application/json");
                conn.setUseCaches(false);

                if(body.length() > 0 && !method.equals("GET")) {
                    conn.setRequestProperty("Content-length", body.getBytes().length + "");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    // Set content body
                    OutputStream os = conn.getOutputStream();
                    os.write(body.getBytes("UTF-8"));
                    os.close();
                    conn.connect();
                }

                int responseCode = conn.getResponseCode();

                if(responseCode == 200){
                    this.mGetResultHandler.getResult(readStream(conn.getInputStream()), rType);
                } else {
                    this.mGetResultHandler.getResult("Response Code: " + Integer.toString(responseCode), rType);
                }
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Malformed URL: " + e.getMessage());
        } catch (java.io.IOException e) {
            Log.e(TAG, "IO Exception: " + e.getMessage());
        }

        return new ResponseWrapper("Error", -1);
    }

    @Override
    protected void onPostExecute(ResponseWrapper resp) {
        // mGetResultHandler.getResult(resp.response, resp.responseType);
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
        void getResult(String result, int resultType);
    }
}