package com.example.cyhunt.cyhunt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ApiAuthenticationClient {

    private String baseURL;
    private String username;
    private String password;
    private String urlResource;
    private String httpMethod; //GET, POST, PUT, DELETE
    private String urlPath;
    private String lastResponse;
    private String payload;
    private HashMap<String, String> parameters;
    private Map<String, List<String>> headerFields;

    /**
     *
     * @param baseURL
     * @param username
     * @param password
     */
    public ApiAuthenticationClient(String baseURL, String username, String password) {
        setBaseURL(baseURL);
        this.username = username;
        this.password = password;
        this.urlResource = "";
        this.httpMethod = "GET";
        parameters = new HashMap<>();
        lastResponse = "";
        payload = "";
        headerFields = new HashMap<>();

        System.setProperty("jsse.enableSNIExtension", "false");
    }

    /**
     *
     * @param baseURL
     * @return
     */
    public ApiAuthenticationClient setBaseURL(String baseURL) {
        this.baseURL = baseURL;
        if (!baseURL.substring(baseURL.length() - 1).equals("/")) {
            this.baseURL += "/";
        }
        return this;
    }

    /**
     *
     * @param urlResource
     * @return
     */
    public ApiAuthenticationClient setURLResrouce(String urlResource) {
        this.urlResource = urlResource;
        return this;
    }

    /**
     *
     * @param urlPath
     * @return
     */
    public final ApiAuthenticationClient setURLPath(String urlPath) {
        this.urlPath = urlPath;
        return this;
    }

    /**
     *
     * @return
     */
    public String getLastResponse() {
        return lastResponse;
    }

    /**
     *
     * @return
     */
    public Map<String, List<String>> getHeaderFields() {
        return headerFields;
    }

    /**
     *
     * @param parameters
     * @return
     */
    public ApiAuthenticationClient setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
        return this;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public ApiAuthenticationClient setParameter (String key, String value) {
        this.parameters.put(key, value);
        return this;
    }

    /**
     *
     * @param key
     * @return
     */
    public ApiAuthenticationClient removeParamter (String key) {
        this.parameters.remove(key);
        return this;
    }

    /**
     *
     * @return
     */
    public ApiAuthenticationClient clearAll() {
        parameters.clear();
        this.username = "";
        this.password = "";
        this.urlResource = "";
        this.urlPath = "";
        this.httpMethod = "";
        lastResponse = "";
        payload = "";
        headerFields.clear();
        return this;
    }

    public JSONObject getLasResponseAsJsonObject() {
        try {
            return  new JSONObject(String.valueOf(lastResponse));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public JSONArray getLastResponseAsJsonArray() {
        try {
            return new JSONArray(String.valueOf(lastResponse));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return
     */
    private String getPayloadAsString() {
        StringBuilder stringBuffer = new StringBuilder();
        Iterator it = parameters.entrySet().iterator();
        int count = 0;

        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (count > 0) {
                stringBuffer.append("&");
            }
            stringBuffer.append(pair.getKey()).append("=").append(pair.getValue());
            it.remove();
            count++;
        }

        return stringBuffer.toString();
    }

    public String execute() {
        String line;
        StringBuilder outputStringBuilder = new StringBuilder();

        try {
            StringBuilder urlString = new StringBuilder(baseURL + urlResource);

            if (!urlPath.equals("")){
                urlString.append("/" + urlPath);
            }

            if (parameters.size() > 0 && httpMethod.equals("GET")) {
                payload = getPayloadAsString();
                urlString.append("?" + payload);
            }

            URL url = new URL(urlString.toString());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(httpMethod);
            connection.setRequestProperty("Authorization", "Basic");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "text/plain");

            if (httpMethod.equals("POST") || httpMethod.equals("PUT")) {
                payload = getPayloadAsString();

                connection.setDoInput(true);
                connection.setDoOutput(true);

                try {
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
                    writer.write(payload);

                    headerFields = connection.getHeaderFields();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line = reader.readLine()) != null) {
                        outputStringBuilder.append(line);
                    }
                } catch (Exception e) {
                    connection.disconnect();
                }
            } else {
                InputStream content = connection.getInputStream();

                headerFields = connection.getHeaderFields();

                BufferedReader reader = new BufferedReader(new InputStreamReader(content));

                while ((line = reader.readLine()) != null) {
                    outputStringBuilder.append(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!outputStringBuilder.toString().equals("")) {
            lastResponse = outputStringBuilder.toString();
        }

        return outputStringBuilder.toString();
    }


}
