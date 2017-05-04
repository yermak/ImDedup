package uk.yermak.imdedup;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import uk.yermak.imdedup.compare.ImageComparator;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by yermak on 12-Oct-16.
 */
public class Dedupler implements Runnable {

    private static final FilenameFilter IMAGE_FILE_FILTER = new AndFileFilter(FileFileFilter.FILE, new SuffixFileFilter(new String[]{"jpg", "jpeg"}, IOCase.INSENSITIVE));
    private DedupObserver observer;
    private ImageComparator comparator;
    private DedupConfiguration[] configurations;
    private LinkedList<FileEntry> targets = new LinkedList<>();
    private ArrayList<FileEntry> duplicates = new ArrayList<>();


    public Dedupler(DedupObserver observer, ImageComparator comparator, DedupConfiguration... configurations) {
        this.observer = observer;
        this.comparator = comparator;
        this.configurations = configurations;
    }

    public void run() {
        try {
            findScope();

            loadScope();

            observer.setProgress(0);
            int i = 0;
            int j = 0;
            for (FileEntry entry = targets.pollFirst(); entry != null; entry = targets.pollFirst()) {
                observer.checkStopped();
                j++;
                FileEntry duplicate = checkDuplicates(targets, entry);
                if (duplicate != null) {
                    i++;
                    j++;
                    duplicates.add(duplicate);
                    //might be needed if can not remove by iterator. e.g from another thread.
                    //targets.remove(duplicate);

                    //TODO: move text to observer
                    observer.setStatus("Found duplicates: " + i);
                }
                observer.setProgress(j);
            }

            observer.finished();

            //TODO: move text to observer
            observer.setStatus("Found duplicates: " + i + " out of " + j + " files in " + observer.getTime() + " sec");
        } catch (StopException se) {
            observer.stopped();
        }

    }

    private void loadScope() throws StopException {
        for (DedupConfiguration configuration : configurations) {
            observer.checkStopped();
            String location = configuration.getLocation();
            File targetDirectory = new File(location);
            loadFilesFromDirectory(targetDirectory);
        }
        observer.setStatus("Loaded: " + targets.size() + " files");
    }

    private int findScope() throws StopException {
        observer.lookingForScope();
        int total = 0;
        for (DedupConfiguration configuration : configurations) {
            total += countTotalFiles(new File(configuration.getLocation()));
            observer.checkStopped();
        }
        observer.scopeFound(total);
        return total;
    }

    private int countTotalFiles(File root) throws StopException {
        int i = 0;
        String[] files = root.list(IMAGE_FILE_FILTER);
        i += files.length;
        observer.setFoundMoreFiles(files.length);
        File[] dirs = root.listFiles((FileFilter) DirectoryFileFilter.DIRECTORY);
        observer.checkStopped();

        for (File dir : dirs) {
            i += countTotalFiles(dir);
        }
        return i;
    }

    private void loadFilesFromDirectory(File targetDirectory) throws StopException {
        File[] files = targetDirectory.listFiles(IMAGE_FILE_FILTER);

        for (File file : files) {
            observer.checkStopped();
            FileEntry fileEntry = new FileEntry(file, targetDirectory.getPath());
            fileEntry.load();
            targets.add(fileEntry);
            //todo move to observer, keep number only
            observer.setStatus("Loaded: " + targets.size() + " files");
            observer.setProgress(targets.size());
        }

        File[] dirs = targetDirectory.listFiles((FileFilter) DirectoryFileFilter.DIRECTORY);
        for (File dir : dirs) {
            loadFilesFromDirectory(dir);
        }
        observer.setStatus("Loaded: " + targets.size() + " files");
    }

    private FileEntry checkDuplicates(LinkedList<FileEntry> fileEntries, FileEntry fileEntry) {
        for (Iterator<FileEntry> iterator = fileEntries.iterator(); iterator.hasNext(); ) {
            FileEntry entry = iterator.next();
            if (fileEntry == entry) {
                continue;
            }
            if (fileEntry.isDuplicate(entry)) {
                return fileEntry;
            }

            if (compareFileEntries(fileEntry, entry)) {
                System.out.println("File 1:" + fileEntry.toString() + " File 2: " + entry.toString());
                fileEntry.addDuplicate(entry);
                iterator.remove();
                return fileEntry;
            }
        }
        return null;
    }

    private boolean compareFileEntries(FileEntry source, FileEntry target) {
        try {
            return comparator.compare(source, target);
//            return new FileAttributesImageComparator().compare(source, target);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
