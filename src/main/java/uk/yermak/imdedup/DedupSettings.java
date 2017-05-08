package uk.yermak.imdedup;

import java.util.prefs.Preferences;

/**
 * Created by yermak on 13-Oct-16.
 */
public class DedupSettings {
    private DedupConfiguration configuration1 = new DedupConfiguration();
    private DedupConfiguration configuration2 = new DedupConfiguration();
    private ComparisionParams comparisonParam = new ComparisionParams();

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

    public boolean isSmartCheck() {
        return comparisonParam.isSmartCheck();
    }

    public void setSmartCheck(boolean smartCheck) {
        comparisonParam.setSmartCheck(smartCheck);
    }

    public boolean isChecksumCheck() {
        return comparisonParam.isChecksumCheck();
    }

    public void setChecksumCheck(boolean checksumCheck) {
        comparisonParam.setChecksumCheck(checksumCheck);
    }

    public boolean isDataCheck() {
        return comparisonParam.isDataCheck();
    }

    public void setDataCheck(boolean dataCheck) {
        comparisonParam.setDataCheck(dataCheck);
    }

    public ComparisionParams getComparisonParam() {
        return comparisonParam;
    }

    public String getDuplicatesLocation1() {
        return configuration1.getDuplicatesLocation();
    }

    public void setDuplicatesLocation1(String duplicatesLocation) {
        configuration1.setDuplicatesLocation(duplicatesLocation);
    }

    public String getUniquesLocation1() {
        return configuration1.getUniquesLocation();
    }

    public void setUniquesLocation1(String uniquesLocation) {
        configuration1.setUniquesLocation(uniquesLocation);
    }

    public DedupConfiguration.ActionStrategy getDuplicatesAction1() {
        return configuration1.getDuplicatesAction();
    }

    public void setDuplicatesAction1(DedupConfiguration.ActionStrategy duplicatesAction) {
        configuration1.setDuplicatesAction(duplicatesAction);
    }

    public DedupConfiguration.ActionStrategy getUniquesAction1() {
        return configuration1.getUniquesAction();
    }

    public void setUniquesAction1(DedupConfiguration.ActionStrategy uniquesAction) {
        configuration1.setUniquesAction(uniquesAction);
    }

    public String getDuplicatesLocation2() {
        return configuration2.getDuplicatesLocation();
    }

    public void setDuplicatesLocation2(String duplicatesLocation) {
        configuration2.setDuplicatesLocation(duplicatesLocation);
    }

    public String getUniquesLocation2() {
        return configuration2.getUniquesLocation();
    }

    public void setUniquesLocation2(String uniquesLocation) {
        configuration2.setUniquesLocation(uniquesLocation);
    }

    public DedupConfiguration.ActionStrategy getDuplicatesAction2() {
        return configuration2.getDuplicatesAction();
    }

    public void setDuplicatesAction2(DedupConfiguration.ActionStrategy duplicatesAction) {
        configuration2.setDuplicatesAction(duplicatesAction);
    }

    public DedupConfiguration.ActionStrategy getUniquesAction2() {
        return configuration2.getUniquesAction();
    }

    public void setUniquesAction2(DedupConfiguration.ActionStrategy uniquesAction) {
        configuration2.setUniquesAction(uniquesAction);
    }
}
