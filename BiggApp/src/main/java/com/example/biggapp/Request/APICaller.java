package com.example.biggapp.Request;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.ssl.*;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.io.*;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Objects;

public class APICaller {
    private final String loginURL = "https://uxhackathon.ro/api/login.php";
    private final String activeSessionURL = "https://uxhackathon.ro/api/activeSession.php";
    private final String logoutURL = "https://uxhackathon.ro/api/logout.php";
    private CloseableHttpClient httpClient = null;

    public APICaller(){
        try {
            httpClient = getHttpClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CloseableHttpClient getHttpClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] chain, String authType) {
                return true;
            }
        }).build();

        HostnameVerifier hostnameVerifier = new NoopHostnameVerifier();
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
    }

    private JSONObject callAPI(String URL, StringEntity body) throws RuntimeException{

        HttpPost activeSessionRequest = new HttpPost(URL);
        activeSessionRequest.addHeader("content-type", "application/x-www-form-urlencoded");
        activeSessionRequest.setEntity(body);

        try {
            HttpResponse response = httpClient.execute(activeSessionRequest);
            String responseJSON = EntityUtils.toString(response.getEntity());
            JSONObject responseJSONObj = new JSONObject(responseJSON);

            //System.out.println("ActiveSession response: \n" + responseJSONObj + "\n");

            return responseJSONObj;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public boolean checkActiveSessionStatus(String username) throws RuntimeException{
        if (httpClient != null) {
            try {
                StringEntity requestBody = new StringEntity("{\"username\":\"" + username + "\"}");
                JSONObject obj = callAPI(activeSessionURL, requestBody);

                return obj.getInt("loginSession") == 1;
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            throw new RuntimeException("HTTP Client was not initialised!");
        }
    }

    public boolean login(String username, String password) throws RuntimeException{
        if (httpClient != null) {
            try {
                StringEntity requestBody = new StringEntity("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}");;
                JSONObject obj = callAPI(loginURL, requestBody);

                return obj.getInt("loginSession") == 1;
            }catch (JSONException e){
                return false;
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            throw new RuntimeException("HTTP Client was not initialised!");
        }
    }

    public boolean logout(String username){
        if (httpClient != null) {
            try {
                StringEntity requestBody = new StringEntity("{\"username\":\"" + username + "\"}");
                JSONObject obj = callAPI(logoutURL, requestBody);

                return obj.getInt("loginSession") == 0;
            } catch (Exception e) {
                return false;
            }
        }
        else{
            return false;
        }
    }
}
