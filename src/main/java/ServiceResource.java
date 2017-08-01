import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import net.minidev.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
    public String createAd(@QueryParam("heading") String heading, @QueryParam("body") String body, @QueryParam("url") String url) {
        //return PersistAd.send(heading, body, url);
        return "dummy";
        //return new AdText(11l, "Dummy Ad", "Random Ad body", "www.bing.com");
    }
}
