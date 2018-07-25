import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import net.minidev.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

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
    public String createAd(@QueryParam("AdId") String id, @QueryParam("heading") String heading, @QueryParam("body") String body, @QueryParam("url") String url) {
        System.out.println("stage1");
        return PersistAd.send(id, heading, body, url, "0");
        //return new AdText(11l, "Dummy Ad", "Random Ad body", "www.bing.com");
    }

    @GET
    @Timed
    @Path("registerClick")
    public String registerClick(@QueryParam("AdId") String id) {
        System.out.println("Registering click for "+id);
        return PersistAd.addClick(id);
    }

    @GET
    @Timed
    @Path("allAds")
    public String getAllAds() {
        return RetrieveAds.send();
    }
}
