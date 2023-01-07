package viewmodels.controls;

import events.NotificationRemoveEvent;
import models.Notification;
import services.EventAggregatorService;
import utils.mvvm.ObservableObject;

import javax.swing.*;
import java.awt.*;

public class NotificationCardViewModel extends ObservableObject {
    private final Notification notification;
    private ImageIcon profileImageIcon;
    public EventAggregatorService eventAggregator;

    public NotificationCardViewModel(Notification notification, EventAggregatorService eventAggregatorService) {
        this.notification = notification;
        eventAggregator = eventAggregatorService;

        var profileIcon = notification.profileIcon();
        if (profileIcon != null) {
            ImageIcon imageIcon = new ImageIcon(profileIcon);
            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(100, -1, Image.SCALE_SMOOTH);
            profileImageIcon = new ImageIcon(scaledImage);
        }
    }

    public Notification getNotification() {
        return notification;
    }

    public ImageIcon getProfileImageIcon() {
        return profileImageIcon;
    }

    public void clearNotificationCommand() {
        eventAggregator.getEvent(NotificationRemoveEvent.class).invoke(notification);
    }
}
