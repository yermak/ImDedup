package uk.yermak.imdedup.compare;

import uk.yermak.imdedup.FileEntry;

import java.io.IOException;

/**
 * Created by Yermak on 20-Oct-16.
 */
public class AndImageComparator implements ImageComparator {

    private ImageComparator[] comparators;

    public AndImageComparator(ImageComparator... comparators) {
        this.comparators = comparators;
    }

    @Override
    public boolean compare(FileEntry source, FileEntry target) throws IOException {
        boolean result = true;
        for (ImageComparator comparator : comparators) {
            result &= comparator.compare(source, target);
            if (!result) return false;
        }
        return result;
    }
}
