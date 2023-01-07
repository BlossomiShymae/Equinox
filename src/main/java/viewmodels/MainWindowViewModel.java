package viewmodels;

import apis.LeagueClientApi;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import events.FriendStatusChangedEvent;
import events.NotificationDisplayEvent;
import models.Friend;
import models.Notification;
import preferences.RootFolderPreference;
import services.EventAggregatorService;
import utils.SimpleEventHandler;
import utils.mvvm.ObservableObject;
import viewmodels.ui.PageNavigationViewModel;
import views.pages.AboutPage;
import views.pages.NotificationsPage;
import views.pages.SettingsPage;

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Singleton
public class MainWindowViewModel extends ObservableObject {
    private final String title = "Equinox";
    private final ArrayList<PageNavigationViewModel> pageNavigationList = new ArrayList<>();
    private GetFriendsWorker getFriendsWorker;
    private final Timer getFriendsTimer;
    private final HashMap<String, Friend> friendHashMap = new HashMap<>();
    private final EventAggregatorService eventAggregator;
    private final RootFolderPreference rootFolderPreference = new RootFolderPreference();

    @Inject
    public MainWindowViewModel(EventAggregatorService eventAggregatorService) {
        super();
        eventAggregator = eventAggregatorService;
        pageNavigationList.add(new PageNavigationViewModel("Notifications", NotificationsPage.class));
        pageNavigationList.add(new PageNavigationViewModel("Settings", SettingsPage.class));
        pageNavigationList.add(new PageNavigationViewModel("About", AboutPage.class));

        getFriendsWorker = new GetFriendsWorker();
        getFriendsWorker.finished.subscribe(this::onFinishedFriendsWorker);
        getFriendsWorker.execute();
        getFriendsTimer = new Timer(1000 * 20, e -> {
            if (getFriendsWorker.isDone()) {
                getFriendsWorker.finished.clear();
                getFriendsWorker = new GetFriendsWorker();
                getFriendsWorker.finished.subscribe(this::onFinishedFriendsWorker);
                getFriendsWorker.execute();
            }
        });
        getFriendsTimer.start();
    }

    private void onFinishedFriendsWorker(List<Friend> friends) {
        for (var friend: friends) {
            if (!friendHashMap.containsKey(friend.puuid())) {
                friendHashMap.put(friend.puuid(), friend);
                continue;
            }
            var friendSpan = friendHashMap.get(friend.puuid());
            // Friend status changed
            if (!friendSpan.availability().equals(friend.availability())) {
                // Return if friend is not online using the League client
                if (!friend.product().equals("league_of_legends")) return;
                System.out.println(friend);
                // From old friend status
                switch(friendSpan.availability()) {
                    case "mobile", "offline" -> {
                        switch (friend.availability()) {
                            // Friend is online
                            case "chat", "dnd", "away" -> {
                                var notification = new Notification(friend, String.format("%s is now online", friend.name()), null, System.currentTimeMillis());
                                eventAggregator.getEvent(NotificationDisplayEvent.class).invoke(notification);
                                eventAggregator.getEvent(FriendStatusChangedEvent.class).invoke(notification);
                            }
                            default -> {}
                        }
                    }
                    default -> {}
                }
                friendHashMap.put(friend.puuid(), friend);
            }
        }

    }

    private class GetFriendsWorker extends SwingWorker<List<Friend>, Void> {
        public SimpleEventHandler<List<Friend>> finished = new SimpleEventHandler<>();
        public boolean isStarted = false;

        @Override
        protected List<Friend> doInBackground() throws Exception {
            isStarted = true;
            try (Stream<String> stream = Files.lines(Paths.get(rootFolderPreference.get() + "Riot Games\\League of Legends\\lockfile"))) {
                String data = stream.findFirst().orElseThrow();
                var arguments = data.split(":");
                if (arguments.length != 5) throw new RuntimeException("bad lockfile");
                var port = Integer.parseInt(arguments[2]);
                var password = arguments[3];

                var api = new LeagueClientApi(port, password);
                return api.getFriendList();
            }
        }

        @Override
        protected void done() {
            try {
                List<Friend> result = get();
                finished.invoke(result);
            } catch(Exception e) {
                System.out.println(e);
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<PageNavigationViewModel> getPageNavigationList() {
        return pageNavigationList;
    }
}
