package uk.yermak.imdedup.compare;

import uk.yermak.imdedup.FileEntry;

import java.io.IOException;

/**
 * Created by Yermak on 20-Oct-16.
 */
public class BasicImageComparator implements ImageComparator {
    @Override
    public boolean compare(FileEntry source, FileEntry target) throws IOException {
        if (source == target) return true;

        if (!source.getFileName().equals(target.getFileName()) || source.getLength() != target.getLength()) {
            return false;
        }

        return true;
    }
}
