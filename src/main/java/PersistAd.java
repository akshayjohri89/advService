import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by akjohri on 8/1/2017.
 */
public class PersistAd {
    public static String send(String heading, String body, String url) {
        JSONObject jsonObject = new JSONObject();
        System.out.println("PersistAd:"+heading+","+body+","+url);
        jsonObject.put("heading", heading);
        jsonObject.put("body", body);
        jsonObject.put("url", url);

        String hex = toHex(jsonObject.toString());

        String method = "publish";
        String id = "1";
        List<Object> params = new ArrayList<Object>();
        params.add("adstream2");
        Long uuid = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        params.add(uuid.toString());
        params.add(hex);
        return invokeRPC(id,method,params,"adchain1");
    }

    public static String toHex(String arg) {
        return String.format("%040x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
    }



    public static String invokeRPC (String id, String method, List< Object > params, String chainName){
        String toReturn = null;
        HttpClient httpClient = HttpClientBuilder.create().build();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("method", method);
        if (params != null && params.size() != 0) {
            jsonObject.put("params", params);
        }
        jsonObject.put("chain_name", chainName);
        JSONObject responseJSONObject = new JSONObject();
        try {
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(
                    new AuthScope("localhost", 7174),
                    new UsernamePasswordCredentials("multichainrpc", "7ZRmD89NCqKgsaSmLpCUm575j7YEw6qbk9cqcgrCN5S3")
            );

            StringEntity myEntity = new StringEntity(jsonObject.toString());

            HttpPost httpPost = new
                    HttpPost("http://multichainrpc:7ZRmD89NCqKgsaSmLpCUm575j7YEw6qbk9cqcgrCN5S3@localhost:7174");       //HttpPost("http://multichainrpc:EZikv3MtoKA2yjrG6T7eTkPZMXntwr9k1ft7ja3jLLaA@192.168.1.6:9732");
            httpPost.setEntity(myEntity);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            System.out.println("------------------");
            System.out.println(httpResponse.getStatusLine());

            if (httpEntity != null) {
                System.out.println("Response content length: " + httpEntity.getContentLength());
                String retSrc = EntityUtils.toString(httpEntity);
                // parsing JSON
                return retSrc;

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        return toReturn;
    }
}
