package services;

import com.google.inject.Singleton;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

@Singleton
public class ResourceLoaderService {
    private final Image applicationIcon;
    private final Image wordmarkImage;
    private String version = "0.0.0";

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

    public String getVersion() {
        return version;
    }

    public void setVersion(Class<?> value) {
        boolean isRelease = true;
        try (InputStream inputStream = value.getResourceAsStream("META-INF/maven/com.blossomishymae/equinox/pom.xml")) {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model model = reader.read(inputStream);
            version = model.getVersion();
        } catch (IOException | XmlPullParserException e) {
            isRelease = false;
        }
        if (isRelease) return;
        try {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model model = reader.read(new FileReader("pom.xml"));
            version = model.getVersion();
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace(System.out);
        }
    }
}
