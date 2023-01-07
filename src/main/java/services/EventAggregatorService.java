package services;

import com.google.inject.Singleton;
import utils.SimpleEventHandler;

import java.util.HashMap;

@Singleton
public class EventAggregatorService {
    private final HashMap<Class<? extends SimpleEventHandler<?>>, SimpleEventHandler<?>> eventHashMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> SimpleEventHandler<T> getEvent(Class<? extends SimpleEventHandler<T>> event) {
        if (!eventHashMap.containsKey(event)) {
            try {
                eventHashMap.put(event, event.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return (SimpleEventHandler<T>) eventHashMap.get(event);
    }
}
