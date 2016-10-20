package uk.yermak.imdedup.compare;

import uk.yermak.imdedup.FileEntry;

import java.io.IOException;

/**
 * Created by Yermak on 20-Oct-16.
 */
public class ChecksumImageComparator implements ImageComparator {
    @Override
    public boolean compare(FileEntry source, FileEntry target) throws IOException {

        return source.getLength() == target.getLength() && source.getCrc32() == target.getCrc32();

    }
}
