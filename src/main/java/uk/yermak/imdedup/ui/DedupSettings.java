package uk.yermak.imdedup.ui;

import uk.yermak.imdedup.DedupConfiguration;

/**
 * Created by yermak on 13-Oct-16.
 */
public class DedupSettings {
    DedupConfiguration configuration1 = new DedupConfiguration();
    DedupConfiguration configuration2 = new DedupConfiguration();
    private boolean subfolders1;
    private boolean subfolders2;


    public String getLocation1(){
        return  configuration1.getLocation();
    }
    public String getLocation2(){
        return  configuration2.getLocation();
    }

    public void setLocation1(String location){
        configuration1.setLocation(location);
    }

    public void setLocation2(String location){
        configuration2.setLocation(location);
    }

    public DedupConfiguration getConfiguration1() {
        return configuration1;
    }

    public DedupConfiguration getConfiguration2() {
        return configuration2;
    }

    public boolean isSubfolders1() {
        return subfolders1;
    }

    public void setSubfolders1(final boolean subfolders1) {
        this.subfolders1 = subfolders1;
    }

    public boolean isSubfolders2() {
        return subfolders2;
    }

    public void setSubfolders2(final boolean subfolders2) {
        this.subfolders2 = subfolders2;
    }
}
