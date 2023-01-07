package utils;

import java.util.ArrayList;

public class SimpleEventHandler<T> {
    private final ArrayList<ISimpleEventSubscription<T>> subscriptionList = new ArrayList<>();

    public void subscribe(ISimpleEventSubscription<T> subscription) {
        subscriptionList.add(subscription);
    }

    public void invoke(T args) {
        for (var subscription: subscriptionList) {
            subscription.invoke(args);
        }
    }

    public void clear() {
        subscriptionList.clear();
    }
}
