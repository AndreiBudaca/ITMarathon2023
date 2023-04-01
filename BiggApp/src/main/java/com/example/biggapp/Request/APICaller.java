package com.example.biggapp.Request;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.ssl.*;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.io.*;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Objects;

public class APICaller {
    public CloseableHttpClient getHttpClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
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

    public void start() throws IOException {
        final String loginURL = "https://uxhackathon.ro/api/login.php";
        final String activeSessionURL = "https://uxhackathon.ro/api/activeSession.php";
        final String logoutURL = "https://uxhackathon.ro/api/logout.php";

        StringEntity loginRequestBody;
        StringEntity activeSessionAndLogoutRequestBody;
        try {
            loginRequestBody = new StringEntity("{\"username\":\"test\",\"password\":\"test\"}");
            activeSessionAndLogoutRequestBody = new StringEntity("{\"username\":\"test\"}");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }


        CloseableHttpClient httpClient = null;
        try {
            httpClient = getHttpClient();
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpPost loginRequest = new HttpPost(loginURL);
        loginRequest.addHeader("content-type", "application/x-www-form-urlencoded");
        loginRequest.setEntity(loginRequestBody);
        HttpResponse loginResponse = Objects.requireNonNull(httpClient).execute(loginRequest);
        String loginResponseJSON = EntityUtils.toString(loginResponse.getEntity());
        JSONObject loginResponseJSONObj = new JSONObject(loginResponseJSON);
        System.out.println("Login response: \n" + loginResponseJSONObj + "\n");


        HttpPost activeSessionRequest = new HttpPost(activeSessionURL);
        activeSessionRequest.addHeader("content-type", "application/x-www-form-urlencoded");
        activeSessionRequest.setEntity(activeSessionAndLogoutRequestBody);
        HttpResponse activeSessionResponse = httpClient.execute(activeSessionRequest);
        String activeSessionResponseJSON = EntityUtils.toString(activeSessionResponse.getEntity());
        JSONObject activeSessionResponseJSONObj = new JSONObject(activeSessionResponseJSON);
        System.out.println("ActiveSession response: \n" + activeSessionResponseJSONObj + "\n");


        HttpPost logoutRequest = new HttpPost(logoutURL);
        logoutRequest.addHeader("content-type", "application/x-www-form-urlencoded");
        logoutRequest.setEntity(activeSessionAndLogoutRequestBody);
        HttpResponse logoutResponse = httpClient.execute(logoutRequest);
        String logoutResponseJSON = EntityUtils.toString(logoutResponse.getEntity());
        JSONObject logoutResponseJSONObj = new JSONObject(logoutResponseJSON);
        System.out.println("Logout response: \n" + logoutResponseJSONObj + "\n");
    }
}
