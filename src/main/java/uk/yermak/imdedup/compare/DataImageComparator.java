package uk.yermak.imdedup.compare;

import uk.yermak.imdedup.FileEntry;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by yermak on 20-Oct-16.
 */
public class DataImageComparator implements ImageComparator {
    @Override
    public boolean compare(FileEntry source, FileEntry target) throws IOException {
        if (source.getLength() != target.getLength()) {
            return false;
        }
        byte[] sourceBytes = source.getBytes();
        byte[] targetBytes = target.getBytes();
        return Arrays.equals(sourceBytes, targetBytes);
    }
}
