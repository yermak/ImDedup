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
        if (comparisonParam.isSmartCheck()) {
            return comparisonParam.isSmartCheck();
        } else {
            return preferences.getBoolean("settings.smartcheck.value", false);
        }
    }

    public void setSmartCheck(boolean smartCheck) {
        comparisonParam.setSmartCheck(smartCheck);
        preferences.put("settings.smartcheck.value", String.valueOf(smartCheck));
    }

    public boolean isChecksumCheck() {
        if (comparisonParam.isChecksumCheck()) {
            return comparisonParam.isChecksumCheck();
        } else {
            return preferences.getBoolean("settings.checksumcheck.value", false);
        }
    }

    public void setChecksumCheck(boolean checksumCheck) {
        comparisonParam.setChecksumCheck(checksumCheck);
        preferences.put("settings.checksumcheck.value", String.valueOf(checksumCheck));
    }

    public boolean isDataCheck() {
        if (comparisonParam.isDataCheck()) {
            return comparisonParam.isDataCheck();
        } else {
            return preferences.getBoolean("settings.datacheck.value", false);
        }
    }

    public void setDataCheck(boolean dataCheck) {
        comparisonParam.setDataCheck(dataCheck);
        preferences.put("settings.datacheck.value", String.valueOf(dataCheck));

    }

    public ComparisionParams getComparisonParam() {
        return comparisonParam;
    }

    public String getDuplicatesLocation1() {
        if (configuration1.getDuplicatesLocation() != null) {
            return configuration1.getDuplicatesLocation();
        } else {
            return preferences.get("settings.duplicates1.location", "");
        }
    }

    public void setDuplicatesLocation1(String location) {
        configuration1.setDuplicatesLocation(location);
        preferences.put("settings.duplicates1.location", location);
    }

    public String getUniquesLocation1() {
        if (configuration1.getUniquesLocation() != null) {
            return configuration1.getUniquesLocation();
        } else {
            return preferences.get("settings.uniques1.location", "");
        }
    }

    public void setUniquesLocation1(String location) {
        configuration1.setUniquesLocation(location);
        preferences.put("settings.uniques1.location", location);
    }

    public ActionStrategy getDuplicatesAction1() {
        if (configuration1.getDuplicatesAction() != null) {
            return configuration1.getDuplicatesAction();
        } else {
            return ActionStrategy.valueOf(preferences.get("settings.duplicates1.action", String.valueOf(ActionStrategy.None)));
        }
    }

    public void setDuplicatesAction1(ActionStrategy actionStrategy) {
        configuration1.setDuplicatesAction(actionStrategy);
        preferences.put("settings.duplicates1.action", String.valueOf(actionStrategy));
    }

    public ActionStrategy getUniquesAction1() {
        if (configuration1.getUniquesAction() != null) {
            return configuration1.getUniquesAction();
        } else {
            return ActionStrategy.valueOf(preferences.get("settings.uniques1.action", String.valueOf(ActionStrategy.None)));
        }
    }

    public void setUniquesAction1(ActionStrategy actionStrategy) {
        configuration1.setUniquesAction(actionStrategy);
        preferences.put("settings.uniques1.action", String.valueOf(actionStrategy));
    }

    public String getDuplicatesLocation2() {
        if (configuration2.getLocation() != null) {
            return configuration2.getDuplicatesLocation();
        } else {
            return preferences.get("settings.duplicates2.location", "");
        }
    }

    public void setDuplicatesLocation2(String location) {
        configuration2.setDuplicatesLocation(location);
        preferences.put("settings.duplicates2.location", location);
    }

    public String getUniquesLocation2() {
        if (configuration2.getUniquesLocation() != null) {
            return configuration2.getUniquesLocation();
        } else {
            return preferences.get("settings.uniques2.location", "");
        }
    }

    public void setUniquesLocation2(String location) {
        configuration2.setUniquesLocation(location);
        preferences.put("settings.uniques2.location", location);
    }

    public ActionStrategy getDuplicatesAction2() {
        if (configuration2.getDuplicatesAction() != null) {
            return configuration2.getDuplicatesAction();
        } else {
            return ActionStrategy.valueOf(preferences.get("settings.duplicates2.action", String.valueOf(ActionStrategy.None)));
        }
    }

    public void setDuplicatesAction2(ActionStrategy actionStrategy) {
        configuration2.setDuplicatesAction(actionStrategy);
        preferences.put("settings.duplicates2.action", String.valueOf(actionStrategy));

    }

    public ActionStrategy getUniquesAction2() {
        if (configuration2.getUniquesAction() != null) {
            return configuration2.getUniquesAction();
        } else {
            return ActionStrategy.valueOf(preferences.get("settings.uniques2.action", String.valueOf(ActionStrategy.None)));
        }
    }

    public void setUniquesAction2(ActionStrategy actionStrategy) {
        configuration2.setUniquesAction(actionStrategy);
        preferences.put("settings.uniques2.action", String.valueOf(actionStrategy));
    }
}
