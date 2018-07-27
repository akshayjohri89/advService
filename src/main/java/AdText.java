import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by akjohri on 7/28/2017.
 */
public class AdText {
    private long id;

    private String heading;
    private String body;
    private String url;
    private String key;
    private String clicks;
    private String imps;
    private String advertiser;
    private String score;

    public AdText() {
        this.heading = "";
        this.body = "";
        this.advertiser = "";
        this.url = "";
        this.key = "";
        this.clicks = "";
        this.imps = "";
        this.score = "";
        this.id = 0;
    }
    public AdText(long id, String advertiser, String heading, String body, String url, String key) {
        this.id = id;
        this.advertiser = advertiser;
        this.heading = heading;
        this.body = body;
        this.url = url;
        this.key = key;
        this.score = "0";
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getHeading() {
        return heading;
    }

    @JsonProperty
    public String getBody() {
        return body;
    }

    @JsonProperty
    public String getUrl() {
        return url;
    }

    @JsonProperty
    public String getKey() {
        return key;
    }

    @JsonProperty
    public String getScore() {
        return score;
    }

    @JsonProperty
    public String getClicks() {
        return clicks;
    }
    @JsonProperty
    public String getImps() {
        return imps;
    }
    @JsonProperty
    public String getAdvertiser() {
        return advertiser;
    }

    public void setClicks(String clicks) {
        this.clicks = clicks;
    }

    public void setImps(String imps) {
        this.imps = imps;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
