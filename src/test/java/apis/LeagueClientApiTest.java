package apis;

import models.Friend;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LeagueClientApiTest {
    private static LeagueClientApi api;

    @BeforeAll
    static void setUp() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        try (Stream<String> stream = Files.lines(Paths.get("C:\\Riot Games\\League of Legends\\lockfile"))) {
            String data = stream.findFirst().orElseThrow();
            var arguments = data.split(":");
            if (arguments.length != 5) throw new RuntimeException("bad lockfile");
            var port = Integer.parseInt(arguments[2]);
            var password = arguments[3];

            api = new LeagueClientApi(port, password);
        }
    }

    @Test
    void getFriendList() throws IOException {
        List<Friend> friends = api.getFriendList();
        friends.forEach(System.out::println);
        assertTrue(friends.size() > 0);
    }
}