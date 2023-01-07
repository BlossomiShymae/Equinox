package viewmodels.pages;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import preferences.RootFolderPreference;
import services.EventAggregatorService;
import utils.mvvm.ObservableObject;

import java.io.File;

@Singleton
public class SettingsViewModel extends ObservableObject {
    private final EventAggregatorService eventAggregator;
    private final RootFolderPreference rootFolderPreference = new RootFolderPreference();

    @Inject
    public SettingsViewModel(EventAggregatorService eventAggregatorService) {
        eventAggregator = eventAggregatorService;
    }

    public String getSelectedFolder() {
        return rootFolderPreference.get();
    }

    public void setSelectedFolder(String value) {
        rootFolderPreference.set(value);
        notifyPropertyChanged("setSelectedFolder");
    }

    public void saveSelectedFolder(File selectedFile) {
        setSelectedFolder(selectedFile.getAbsolutePath());
    }
}
