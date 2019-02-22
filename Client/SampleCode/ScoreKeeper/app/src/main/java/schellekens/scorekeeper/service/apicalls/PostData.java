package schellekens.scorekeeper.service.apicalls;

/**
 * schellekens.scorekeeper.service.apicalls Created by bschellekens on 12/5/2017.
 */
public class PostData {

    private String url;
    private String jsonData;

    public PostData(String url, String jsonData) {
        this.url = url;
        this.jsonData = jsonData;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}