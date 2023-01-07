package services;

import com.google.inject.Singleton;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@Singleton
public class ResourceLoaderService {
    private final Image applicationIcon;
    private final Image wordmarkImage;

    public ResourceLoaderService() throws URISyntaxException, IOException {
        URL applicationIconUrl = getClass().getResource("/images/equinox.png");
        applicationIcon = Toolkit.getDefaultToolkit().getImage(applicationIconUrl);
        URL wordmarkImageUrl = getClass().getResource("/images/equinox_wordmark.png");
        wordmarkImage = Toolkit.getDefaultToolkit().getImage(wordmarkImageUrl);
    }

    public Image getApplicationIcon() {
        return applicationIcon;
    }

    public Image getWordmarkImage() {
        return wordmarkImage;
    }
}
