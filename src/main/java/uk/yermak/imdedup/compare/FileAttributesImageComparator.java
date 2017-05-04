package uk.yermak.imdedup.compare;

import uk.yermak.imdedup.FileEntry;

import java.io.IOException;

/**
 * Created by Yermak on 20-Oct-16.
 */
public class FileAttributesImageComparator implements ImageComparator {
    @Override
    public boolean compare(FileEntry source, FileEntry target) throws IOException {
        if (source == target) return true;

        return source.getFileName().equals(target.getFileName()) && source.getLength() == target.getLength();

    }
}
