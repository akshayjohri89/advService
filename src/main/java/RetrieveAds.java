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
import org.json.JSONException;
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
    private static final String rpcUser = "multichainrpc";
    private static final String rpcPassword = "Apb8LDVcPtsCqBeDKBVCtpNRx4GnCPpK2fBJ2eE1S8uK";

    public static String getAll() {
        String method = "liststreamkeys";
        String id = "1";
        List<Object> params = new ArrayList<Object>();
        params.add("adstream1");
        params.add(false);
        List<String> keys = rpcGetAllKeys(id,method,params,"adchain1");
        JSONObject toReturn = new JSONObject();
        for (String key: keys) {
            toReturn.put(key,getAd(key));
        }
        return toReturn.toString();
    }

    public static List<String> rpcGetAllKeys (String id, String method, List<Object> params, String chainName){
        List<String> toReturn = new ArrayList<String>();
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
                    new UsernamePasswordCredentials(rpcUser, rpcPassword)
            );

            StringEntity myEntity = new StringEntity(jsonObject.toString());

            HttpPost httpPost = new
                    HttpPost("http://"+rpcUser+":"+rpcPassword+"@localhost:4356");
            httpPost.setEntity(myEntity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            System.out.println("------------------");
            System.out.println(httpResponse.getStatusLine());

            if (httpEntity != null) {
                System.out.println("Response content length: " + httpEntity.getContentLength());
                String retSrc = EntityUtils.toString(httpEntity);
                JSONObject result = new JSONObject(retSrc); //Convert String to JSON Object
                JSONArray tokenList = result.getJSONArray("result");
                for (int index=0;index<tokenList.length();index++) {
                    try {
                        JSONObject ob = tokenList.getJSONObject(index);
//                        String data = ob.getString("data");
//
//                        StringBuilder output = new StringBuilder();
//                        for (int i = 0; i < data.length(); i+=2) {
//                            String str = data.substring(i, i+2);
//                            output.append((char)Integer.parseInt(str, 16));
//                        }
//                        System.out.println("AllAds:Data:"+output.toString());
//                        JSONObject returnJson = new JSONObject(output.toString());
//                        returnJson.put("key",ob.getString("key"));
//                        toReturn.put(ob.getString("key"),returnJson);
                        toReturn.add(ob.getString("key"));
                    } catch (JSONException e) {
                    }
                }
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

    public static JSONObject getAd(String key) {
        String method = "liststreamkeyitems";
        List<Object> params = new ArrayList<Object>();
        params.add("adstream1");
        params.add(key);
        params.add(false);
        params.add(1);
        return rpcGetAd(method,params,"adchain1");
    }

    public static JSONObject rpcGetAd (String method, List< Object > params, String chainName){
        JSONObject returnJson = null,oj =null;
        HttpClient httpClient = HttpClientBuilder.create().build();
        JSONObject jsonObject = new JSONObject();
        String id = "1";
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
                    new UsernamePasswordCredentials(rpcUser, rpcPassword)
            );

            StringEntity myEntity = new StringEntity(jsonObject.toString());

            HttpPost httpPost = new
                    HttpPost("http://"+rpcUser+":"+rpcPassword+"@localhost:4356");       //HttpPost("http://multichainrpc:EZikv3MtoKA2yjrG6T7eTkPZMXntwr9k1ft7ja3jLLaA@192.168.1.6:9732");
            httpPost.setEntity(myEntity);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            System.out.println("------------------");
            System.out.println(httpResponse.getStatusLine());

            if (httpEntity != null) {
                System.out.println("Response content length: " + httpEntity.getContentLength());
                String retSrc = EntityUtils.toString(httpEntity);
                // parsing JSON
                System.out.println(retSrc);
                JSONObject result = new JSONObject(retSrc); //Convert String to JSON Object
                JSONArray tokenList = result.getJSONArray("result");

//                int index = (int)(Math.random()*tokenList.length());
                oj = tokenList.getJSONObject(0);
                System.out.println("oj:"+oj);
                String data = oj.getString("data");
                StringBuilder output = new StringBuilder();
                for (int i = 0; i < data.length(); i+=2) {
                    String str = data.substring(i, i+2);
                    output.append((char)Integer.parseInt(str, 16));
                }
                System.out.println("original Object:"+output.toString().trim());
                returnJson = new JSONObject(output.toString().trim());
                return returnJson;
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
        return returnJson;
    }

}
