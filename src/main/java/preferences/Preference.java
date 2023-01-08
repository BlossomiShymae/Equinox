package preferences;

import java.util.prefs.Preferences;

public class Preference {
    private final String key;
    private final String defaultValue;
    private final Preferences preferences;

    public Preference(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
        preferences = Preferences.userRoot().node("com/blossomishymae/prefs");
    }

    public String get() {
        return preferences.get(key, defaultValue);
    }

    public void set(String value) {
        preferences.put(key, value);
    }
}
