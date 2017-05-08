package uk.yermak.imdedup;

/**
 * Created by yermak on 12-Oct-16.
 */
public class DedupConfiguration {
    private String location;
    private boolean subfolders;
    private String duplicatesLocation;
    private String uniquesLocation;

    ;
    private ActionStrategy duplicatesAction;
    private ActionStrategy uniquesAction;

    public DedupConfiguration() {

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isSubfolders() {
        return subfolders;
    }

    public void setSubfolders(boolean subfolders) {
        this.subfolders = subfolders;
    }

    public String getDuplicatesLocation() {
        return duplicatesLocation;
    }

    public void setDuplicatesLocation(String duplicatesLocation) {
        this.duplicatesLocation = duplicatesLocation;
    }

    public String getUniquesLocation() {
        return uniquesLocation;
    }

    public void setUniquesLocation(String uniquesLocation) {
        this.uniquesLocation = uniquesLocation;
    }

    public ActionStrategy getDuplicatesAction() {
        return duplicatesAction;
    }

    public void setDuplicatesAction(ActionStrategy duplicatesAction) {
        this.duplicatesAction = duplicatesAction;
    }

    public ActionStrategy getUniquesAction() {
        return uniquesAction;
    }

    public void setUniquesAction(ActionStrategy uniquesAction) {
        this.uniquesAction = uniquesAction;
    }
}
