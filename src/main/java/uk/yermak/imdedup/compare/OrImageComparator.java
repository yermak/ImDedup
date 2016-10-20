package uk.yermak.imdedup.compare;

import uk.yermak.imdedup.FileEntry;

import java.io.IOException;

/**
 * Created by Yermak on 20-Oct-16.
 */
public class OrImageComparator implements ImageComparator {


    private ImageComparator[] comparators;

    public OrImageComparator(ImageComparator... comparators) {
        this.comparators = comparators;
    }

    @Override
    public boolean compare(FileEntry source, FileEntry target) throws IOException {
        boolean result = false;
        for (ImageComparator comparator : comparators) {
            result |= comparator.compare(source, target);
            if (result) return true;
        }
        return result;

    }
}
