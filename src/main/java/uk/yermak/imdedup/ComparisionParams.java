package uk.yermak.imdedup;

/**
 * Created by yermak on 04-Nov-16.
 */
public class ComparisionParams {

    private boolean smartCheck;
    private boolean basicCheck;
    private boolean checksumCheck;
    private boolean dataCheck;

    public boolean isSmartCheck() {
        return smartCheck;
    }

    public void setSmartCheck(boolean smartCheck) {
        this.smartCheck = smartCheck;
    }

    public boolean isBasicCheck() {
        return basicCheck;
    }

    public void setBasicCheck(boolean basicCheck) {
        this.basicCheck = basicCheck;
    }

    public boolean isChecksumCheck() {
        return checksumCheck;
    }

    public void setChecksumCheck(boolean checksumCheck) {
        this.checksumCheck = checksumCheck;
    }

    public boolean isDataCheck() {
        return dataCheck;
    }

    public void setDataCheck(boolean dataCheck) {
        this.dataCheck = dataCheck;
    }
}
