import com.google.inject.Guice;
import com.google.inject.Injector;
import org.pushingpixels.radiance.theming.api.skin.RadianceNightShadeLookAndFeel;
import services.ApplicationInjectorService;
import views.MainWindowView;
import views.SystemTray;

import javax.swing.*;

public class Main implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }

    @Override
    public void run() {
        try {
            UIManager.setLookAndFeel(new RadianceNightShadeLookAndFeel());
        } catch (Exception exception) {
            exception.printStackTrace(System.out);
        }
        Injector injector = Guice.createInjector(new ApplicationInjectorService());

        // Get system tray for init.
        SystemTray tray = injector.getInstance(SystemTray.class);

        // Get main window for init.
        MainWindowView window = injector.getInstance(MainWindowView.class);
    }
}