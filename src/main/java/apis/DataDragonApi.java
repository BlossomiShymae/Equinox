package apis;

import com.google.gson.Gson;
import com.google.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

@Singleton
public class DataDragonApi {
    private final String url = "https://ddragon.leagueoflegends.com";
    private final String currentVersion;
    private final OkHttpClient client;

    public DataDragonApi() throws IOException {
        client = new OkHttpClient();

        // Setup current version
        Request request = new Request.Builder()
                .url(url + "/api/versions.json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Failed to get versions from Data Dragon");
            var data = response.body().string();
            String[] versions = new Gson().fromJson(data, String[].class);
            currentVersion = versions[0];
        }
    }

    private Response run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(this.url + "/cdn/" + currentVersion + url)
                .build();
        return client.newCall(request).execute();
    }

    public byte[] getProfileIcon(int id) throws IOException {
        try (var response = run("/img/profileicon/" + id + ".png")) {
            return Objects.requireNonNull(response.body()).bytes();
        }
    }
}
