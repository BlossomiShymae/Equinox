package views;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import events.NotificationDisplayEvent;
import events.TrayLeftClickEvent;
import services.EventAggregatorService;
import services.ResourceLoaderService;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Singleton
public class SystemTray {
    private final java.awt.SystemTray tray;
    private final TrayIcon icon;

    @Inject
    public SystemTray(ResourceLoaderService resourceLoader, EventAggregatorService eventAggregatorService) throws AWTException {
        tray = java.awt.SystemTray.getSystemTray();
        Image trayImage = resourceLoader.getApplicationIcon();
        Dimension trayIconSize = tray.getTrayIconSize();
        trayImage = trayImage.getScaledInstance(trayIconSize.width, trayIconSize.height, Image.SCALE_SMOOTH);

        PopupMenu popup = new PopupMenu();
        MenuItem titleItem = new MenuItem("Equinox");
        MenuItem defaultItem = new MenuItem("Exit");
        defaultItem.addActionListener(e -> System.exit(0));
        titleItem.setEnabled(false);
        popup.add(titleItem);
        popup.addSeparator();
        popup.add(defaultItem);
        icon = new TrayIcon(trayImage, "Equinox");
        icon.setPopupMenu(popup);
        tray.add(icon);

        icon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // On left click
                if (e.getButton() == MouseEvent.BUTTON1) eventAggregatorService.getEvent(TrayLeftClickEvent.class).invoke(null);
            }
        });
        eventAggregatorService.getEvent(NotificationDisplayEvent.class).subscribe(notification -> icon.displayMessage("Equinox", notification.message(), TrayIcon.MessageType.INFO));
    }
}
