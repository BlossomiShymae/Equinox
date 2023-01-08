package views.pages;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import services.EventAggregatorService;
import utils.mvvm.PropertyChangedEventArgs;
import viewmodels.controls.NotificationCardViewModel;
import viewmodels.pages.NotificationsViewModel;
import views.controls.NotificationCard;

import javax.swing.*;

@Singleton
public class NotificationsPage extends Page {
    private final NotificationsViewModel viewModel;
    private final EventAggregatorService eventAggregator;
    private JPanel rootPanel;
    private JPanel controlPanel;
    private JButton clearNotificationsButton;
    private JScrollPane scrollPanel;
    private JPanel notificationsPanel;

    @Inject
    public NotificationsPage(NotificationsViewModel notificationsViewModel, EventAggregatorService eventAggregatorService) {
        super();
        add(rootPanel);
        viewModel = notificationsViewModel;
        eventAggregator = eventAggregatorService;

        notificationsPanel.setLayout(new BoxLayout(notificationsPanel, BoxLayout.PAGE_AXIS));
        notificationsPanel.setAlignmentY(JPanel.TOP_ALIGNMENT);
        viewModel.propertyChanged.subscribe(this::onPropertyChanged);
        clearNotificationsButton.addActionListener(e -> viewModel.clearNotificationsCommand());
    }

    private void onPropertyChanged(PropertyChangedEventArgs e) {
        notificationsPanel.removeAll();
        var notifications = viewModel.getNotificationHistory();
        for (var notification: notifications) {
            var card = new NotificationCard(new NotificationCardViewModel(notification, eventAggregator));
            notificationsPanel.add(card);
        }
    }
}
