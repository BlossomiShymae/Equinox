package apis;

import com.google.gson.Gson;
import com.google.inject.Singleton;
import models.Friend;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Singleton
public class LeagueClientApi {
    private final String url = "https://127.0.0.1";
    private final int port;
    private final String authorization;
    private final OkHttpClient client;

    public LeagueClientApi(int port, String password) throws NoSuchAlgorithmException, KeyManagementException {
        this.port = port;
        String authentication = "riot:" + password;
        authorization = Base64.getEncoder().encodeToString(authentication.getBytes());

        TrustManager TRUST_ALL_CERTS = new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }
        };

        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new TrustManager[] { TRUST_ALL_CERTS }, new java.security.SecureRandom());
        client = new OkHttpClient.Builder().sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) TRUST_ALL_CERTS).hostnameVerifier((hostname, session) -> true).build();
    }

    private Response run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(this.url + ":" + port + url)
                .addHeader("Authorization", "Basic " + authorization)
                .build();

        return client.newCall(request).execute();
    }

    public List<Friend> getFriendList() throws IOException {
        try (var response = run("/lol-chat/v1/friends")) {
            var responseData = response.body().string();
            Friend[] friendArray = new Gson().fromJson(responseData, Friend[].class);
            return Arrays.asList(friendArray);
        }
    }
}
