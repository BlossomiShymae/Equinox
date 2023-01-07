package viewmodels.pages;

import apis.DataDragonApi;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import events.FriendStatusChangedEvent;
import events.NotificationRemoveEvent;
import models.Notification;
import services.EventAggregatorService;
import utils.mvvm.ObservableObject;
import utils.mvvm.PropertyChangedEventArgs;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Singleton
public class NotificationsViewModel extends ObservableObject {
    private final EventAggregatorService eventAggregator;
    private final DataDragonApi dataDragonApi;
    private static final HashMap<Integer, byte[]> profileIconHashMap = new HashMap<>();
    private final ArrayList<Notification> notificationHistory = new ArrayList<>();

    @Inject
    public NotificationsViewModel(EventAggregatorService eventAggregatorService, DataDragonApi dataDragonApi) {
        super();
        eventAggregator = eventAggregatorService;
        this.dataDragonApi = dataDragonApi;

        eventAggregator.getEvent(FriendStatusChangedEvent.class).subscribe(this::updateNotificationHistory);
        eventAggregator.getEvent(NotificationRemoveEvent.class).subscribe(this::removeNotification);
    }

    public void clearNotificationsCommand() {
        clearNotifications();
    }

    private void updateNotificationHistory(Notification notification) {
        var id = notification.friend().icon();
        if (profileIconHashMap.containsKey(id)) {
            addNotification(new Notification(notification.friend(), notification.message(), profileIconHashMap.get(id), notification.timestamp()));
            return;
        }
        var worker = new SwingWorker<Notification, Void>() {
            @Override
            protected Notification doInBackground() throws Exception {
                var profileIcon = dataDragonApi.getProfileIcon(notification.friend().icon());
                return new Notification(notification.friend(), notification.message(), profileIcon, notification.timestamp());
            }

            @Override
            protected void done() {
                try {
                    var processedNotification = get();
                    profileIconHashMap.put(id, processedNotification.profileIcon());
                    addNotification(processedNotification);
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        worker.execute();
    }

    public ArrayList<Notification> getNotificationHistory() {
        var history = notificationHistory;
        Collections.reverse(history);
        return history;
    }

    private void removeNotification(Notification notification) {
        notificationHistory.remove(notification);
        notifyPropertyChanged("removeNotification");
    }

    private void addNotification(Notification notification) {
        notificationHistory.add(notification);
        notifyPropertyChanged("addNotification");
    }

    private void clearNotifications() {
        notificationHistory.clear();
        notifyPropertyChanged("clearNotifications");
    }
}
