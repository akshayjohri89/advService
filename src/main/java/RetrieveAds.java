import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

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

import java.io.*;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
/**
 * Created by akjohri on 7/25/2018.
 */
public class RetrieveAds {


    public static List<String> send() {
        String method = "liststreamitems";
        String id = "1";
        List<Object> params = new ArrayList<Object>();
        params.add("adstream1");
        return invokeRPC(id,method,params,"adchain1");
    }

    public static List<String> invokeRPC (String id, String method, List< Object > params, String chainName){
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
                    new AuthScope("localhost", 4356),
                    new UsernamePasswordCredentials("multichainrpc", "3WsnHdSsUFgp3umeQbd3Hd3mrvNbQQpPoPTs285Up8eV")
            );

            StringEntity myEntity = new StringEntity(jsonObject.toString());

            HttpPost httpPost = new
                    HttpPost("http://multichainrpc:3WsnHdSsUFgp3umeQbd3Hd3mrvNbQQpPoPTs285Up8eV@localhost:4356");       //HttpPost("http://multichainrpc:EZikv3MtoKA2yjrG6T7eTkPZMXntwr9k1ft7ja3jLLaA@192.168.1.6:9732");
            httpPost.setEntity(myEntity);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            System.out.println("------------------");
            System.out.println(httpResponse.getStatusLine());

            if (httpEntity != null) {
                System.out.println("Response content length: " + httpEntity.getContentLength());
                String retSrc = EntityUtils.toString(httpEntity);
                // parsing JSON
                JSONObject result = new JSONObject(retSrc); //Convert String to JSON Object

                JSONArray tokenList = result.getJSONArray("result");

                List<String> toReturn = new ArrayList<String>();
                for (int index=0;index<=tokenList.length();index++) {
                    JSONObject ob = tokenList.getJSONObject(index);
                    String data = oj.getString("data");
                    StringBuilder output = new StringBuilder();
                    for (int i = 0; i < data.length(); i+=2) {
                        String str = data.substring(i, i+2);
                        output.append((char)Integer.parseInt(str, 16));
                    }
                    JSONObject returnJson = new JSONObject(output.toString());
                    returnJson.put("key",oj.getString("key"));
                    toReturn.add(returnJson.toString());
                }
//                    toReturn = oj.getString("data");
//                    return output.toString();
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
