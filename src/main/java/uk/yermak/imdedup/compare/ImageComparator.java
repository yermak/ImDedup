package uk.yermak.imdedup.compare;

import uk.yermak.imdedup.FileEntry;

import java.io.IOException;

/**
 * Created by yermak on 20-Oct-16.
 */
public interface ImageComparator {


    boolean compare(FileEntry source, FileEntry target) throws IOException;

}
