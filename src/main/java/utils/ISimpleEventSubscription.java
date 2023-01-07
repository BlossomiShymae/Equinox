package utils;

@FunctionalInterface
public interface ISimpleEventSubscription<T extends Object> {
    void invoke(T args);
}
