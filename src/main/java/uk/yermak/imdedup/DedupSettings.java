package uk.yermak.imdedup;

import java.util.prefs.Preferences;

/**
 * Created by yermak on 13-Oct-16.
 */
public class DedupSettings {
    DedupConfiguration configuration1 = new DedupConfiguration();
    DedupConfiguration configuration2 = new DedupConfiguration();

    private final Preferences preferences;

    public DedupSettings() {
        preferences = Preferences.userNodeForPackage(DedupSettings.class);

    }

    public String getLocation1() {
        if (configuration1.getLocation() != null) {
            return configuration1.getLocation();
        } else {
            return preferences.get("settings.location1.name", "");
        }
    }

    public String getLocation2() {
        if (configuration2.getLocation() != null) {
            return configuration1.getLocation();
        } else {
            return preferences.get("settings.location2.name", "");
        }
    }

    public void setLocation1(String location) {
        configuration1.setLocation(location);
        preferences.put("settings.location1.name", location);
    }

    public void setLocation2(String location) {
        configuration2.setLocation(location);
        preferences.put("settings.location2.name", location);
    }

    public DedupConfiguration getConfiguration1() {
        return configuration1;
    }

    public DedupConfiguration getConfiguration2() {
        return configuration2;
    }

    public boolean isSubfolders1() {
        if (configuration1.isSubfolders()) {
            return configuration1.isSubfolders();
        } else {
            return preferences.getBoolean("settings.location1.name", true);
        }

    }

    public void setSubfolders1(boolean subfolders1) {
        configuration1.setSubfolders(subfolders1);
        preferences.putBoolean("settings.location1.subfolders", subfolders1);
    }

    public boolean isSubfolders2() {
        if (configuration2.isSubfolders()) {
            return configuration2.isSubfolders();
        } else {
            return preferences.getBoolean("settings.location2.subfolders", true);
        }
    }

    public void setSubfolders2(boolean subfolders2) {
        configuration2.setSubfolders(subfolders2);
        preferences.putBoolean("settings.location2.subfolders", subfolders2);

    }
}
