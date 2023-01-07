package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import views.pages.AboutPage;
import views.pages.NotificationsPage;
import views.pages.SettingsPage;

import javax.swing.*;
import java.util.HashMap;

@Singleton
public class PageService {
    private final HashMap<Class<? extends JPanel>, JPanel> pageHashMap = new HashMap<>();

    @Inject
    public PageService(SettingsPage settingsPage, AboutPage aboutPage, NotificationsPage notificationsPage) {
        pageHashMap.put(NotificationsPage.class, notificationsPage);
        pageHashMap.put(SettingsPage.class, settingsPage);
        pageHashMap.put(AboutPage.class, aboutPage);
    }

    public JPanel getPage(Class<? extends JPanel> page) {
        if (!pageHashMap.containsKey(page)) {
            throw new RuntimeException("Page does not exist in page service. Did you forget to add it? owo");
        }
        return pageHashMap.get(page);
    }
}
