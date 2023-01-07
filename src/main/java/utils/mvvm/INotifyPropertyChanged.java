package utils.mvvm;

import utils.SimpleEventHandler;

public interface INotifyPropertyChanged {
    void registerPropertyChanged(SimpleEventHandler<PropertyChangedEventArgs> propertyChanged);
    void notifyPropertyChanged(String callerName);
}
