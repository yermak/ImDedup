package uk.yermak.imdedup.compare;

import uk.yermak.imdedup.ComparisionParams;

/**
 * Created by yermak on 04-Nov-16.
 */
public class ComparatorFactory {
    public static ImageComparator comparator(ComparisionParams params) {
        if (params.isDataCheck()) {
            return new AndImageComparator(new ChecksumImageComparator(), new DataImageComparator());
        }
        if (params.isChecksumCheck()) {
            return new AndImageComparator(new ChecksumImageComparator(), new DataImageComparator());
        }
        return new BasicImageComparator();
    }
}
