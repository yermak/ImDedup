package uk.yermak.imdedup;

/**
 * Created by yermak on 12-Oct-16.
 */
public class DedupConfiguration {
    private String location;

    public DedupConfiguration(String location) {

        this.location = location;
    }

    public DedupConfiguration() {

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
