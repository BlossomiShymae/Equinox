package views.controls;

import viewmodels.controls.NotificationCardViewModel;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationCard extends JPanel {
    private final NotificationCardViewModel viewModel;
    private JPanel rootPanel;
    private JPanel profileIconPanel;
    private JLabel profileIcon;
    private JPanel contentPanel;
    private JLabel messageLabel;
    private JLabel timeLabel;
    private JPanel buttonPanel;
    private JButton removeButton;

    public NotificationCard(NotificationCardViewModel notificationCardViewModel) {
        super();
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(new GridBagLayout());
        constraints.insets = new Insets(8,8,8,8);
        revalidate();
        add(rootPanel, constraints);
        viewModel = notificationCardViewModel;

        setView();
        removeButton.addActionListener(e -> viewModel.clearNotificationCommand());
    }

    private void setView() {
        var notification = viewModel.getNotification();
        SimpleDateFormat dt = new SimpleDateFormat("EEE, d MMM HH:mm:ss");
        String time = dt.format(new Date(notification.timestamp()));
        var profileImageIcon = viewModel.getProfileImageIcon();

        timeLabel.setText(time);
        messageLabel.setText(notification.message());
        if (profileImageIcon != null) profileIcon.setIcon(profileImageIcon);
    }
}
