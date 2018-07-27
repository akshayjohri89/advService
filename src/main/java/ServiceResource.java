import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
/**
 * Created by akjohri on 7/28/2017.
 */
@Path("/service")
@Produces(MediaType.TEXT_PLAIN)
public class ServiceResource {
    public ServiceResource() {

    }

    @GET
    @Timed
    public String createAd(@QueryParam("AdId") String id, @QueryParam("advertiser") String advertiser, @QueryParam("heading") String heading, @QueryParam("body") String body, @QueryParam("url") String url) {
        System.out.println("stage1");
        return PersistAd.createAd(id, advertiser, heading, body, url, "0");

        //return new AdText(11l, "Dummy Ad", "Random Ad body", "www.bing.com");
    }

    @GET
    @Timed
    @Path("registerImp")
    public String registerImp(@QueryParam("AdId") String key) {
        System.out.println("Registering impression for "+key);
        JSONObject jsonObject = (JSONObject) RetrieveAds.getAd(key);
        Integer imps = null,clicks=null;
        try {
            clicks = Integer.parseInt(jsonObject.get("clicks").toString());
        } catch (JSONException ex) {
            clicks = new Integer(0);
        }
        try {
            imps = Integer.parseInt(jsonObject.get("clicks").toString())+1;
        } catch (JSONException ex) {
            imps = new Integer(1);
        }
        String advertiser ="";
        try {
            advertiser = jsonObject.get("advertiser").toString();
        } catch (JSONException ex) {
        }

        return PersistAd.addClickImp(key,
                advertiser,
                jsonObject.get("heading").toString(),
                jsonObject.get("body").toString(),
                jsonObject.get("url").toString(),
                jsonObject.get("score").toString(),
                clicks.toString(),
                imps.toString());
    }

    @GET
    @Timed
    @Path("registerClick")
    public String registerClick(@QueryParam("AdId") String key) {
        System.out.println("Registering click for "+key);
        JSONObject jsonObject = (JSONObject) RetrieveAds.getAd(key);
        Integer clicks = null,imps=null;
        try {
            clicks = Integer.parseInt(jsonObject.get("clicks").toString())+1;
        } catch (JSONException ex) {
            clicks = new Integer(1);
        }
        try {
            imps = Integer.parseInt(jsonObject.get("imps").toString());
        } catch (JSONException ex) {
            imps = new Integer(1);
        }
        String advertiser ="";
        try {
            advertiser = jsonObject.get("advertiser").toString();
        } catch (JSONException ex) {
        }
        return PersistAd.addClickImp(key,
                advertiser,
                jsonObject.get("heading").toString(),
                jsonObject.get("body").toString(),
                jsonObject.get("url").toString(),
                jsonObject.get("score").toString(),
                clicks.toString(),
                imps.toString());
    }

    @GET
    @Timed
    @Path("evalAd")
    public String evalAd(@QueryParam("AdId") String key, @QueryParam("status") String status) {
        JSONObject jsonObject = (JSONObject) RetrieveAds.getAd(key);
        Integer clicks = null,imps=null;
        try {
            clicks = Integer.parseInt(jsonObject.get("clicks").toString());
        } catch (JSONException ex) {
            clicks = new Integer(0);
        }
        try {
            imps = Integer.parseInt(jsonObject.get("imps").toString());
        } catch (JSONException ex) {
            imps = new Integer(0);
        }
        String advertiser ="";
        try {
            advertiser = jsonObject.get("advertiser").toString();
        } catch (JSONException ex) {
        }
        Integer score = null;
        try {
            score = Integer.parseInt(jsonObject.get("score").toString());
        } catch (JSONException ex) {
            score = new Integer(0);
        }
        if (status.equals("approve")) {
            score = new Integer(1);
        } else {
            score = new Integer(-1);
        }
        return PersistAd.addClickImp(key,
                advertiser,
                jsonObject.get("heading").toString(),
                jsonObject.get("body").toString(),
                jsonObject.get("url").toString(),
                score.toString(),
                clicks.toString(),
                imps.toString());
    }


    @GET
    @Timed
    @Path("getAdDetails")
    public AdText getAdDetails(@QueryParam("AdId") String key) {
        JSONObject jsonObject = (JSONObject) RetrieveAds.getAd(key);
        Integer clicks = null,imps=null;
        try {
            clicks = Integer.parseInt(jsonObject.get("clicks").toString());
        } catch (JSONException ex) {
            clicks = new Integer(0);
        }
        try {
            imps = Integer.parseInt(jsonObject.get("imps").toString());
        } catch (JSONException ex) {
            imps = new Integer(0);
        }
        String advertiser ="";
        try {
            advertiser = jsonObject.get("advertiser").toString();
        } catch (JSONException ex) {
        }
        Integer score = null;
        try {
            score = Integer.parseInt(jsonObject.get("score").toString());
        } catch (JSONException ex) {
            score = new Integer(0);
        }
        AdText toReturn = new AdText("1",
                advertiser,
                jsonObject.get("heading").toString(),
                jsonObject.get("body").toString(),
                jsonObject.get("url").toString(),
                key);
        toReturn.setClicks(clicks.toString());
        toReturn.setImps(imps.toString());
        toReturn.setScore(score.toString());
        return toReturn
    }

    @GET
    @Timed
    @Path("allAds")
    public String getAllAds() {
        return RetrieveAds.getAll();
    }
}
