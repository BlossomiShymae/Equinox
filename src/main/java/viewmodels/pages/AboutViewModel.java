package viewmodels.pages;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import services.ResourceLoaderService;

import javax.swing.*;
import java.awt.*;

@Singleton
public class AboutViewModel {
    private final ImageIcon wordmarkImageIcon;

    @Inject
    public AboutViewModel(ResourceLoaderService resourceLoaderService) {
        Image image = resourceLoaderService.getWordmarkImage();
        wordmarkImageIcon = new ImageIcon(image);
    }

    public ImageIcon getWordmarkImageIcon() {
        return wordmarkImageIcon;
    }
}
