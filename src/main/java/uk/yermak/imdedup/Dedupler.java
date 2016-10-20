package uk.yermak.imdedup;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

/**
 * Created by yermak on 12-Oct-16.
 */
public class Dedupler implements Runnable {

    private DedupObserver observer;
    private DedupConfiguration[] configurations;
    private LinkedList<FileEntry> targets = new LinkedList<>();


    public Dedupler(DedupObserver observer, DedupConfiguration... configurations) {
        this.observer = observer;
        this.configurations = configurations;
    }

    public void run() {
        try {
            int total = findScope();
            observer.setMaxProgress(total);
            observer.setStatus("Found: " + total + " files");

            loadScope();
            observer.setStatus("Loaded: " + targets.size() + " files");

            observer.setProgress(0);

            int i = 0;
            int j = 0;
            for (FileEntry entry = targets.pollFirst(); entry != null; entry = targets.pollFirst()) {
                observer.checkStopped();
                j++;
                if (checkDuplicates(targets, entry)) {
                    i++;
                    observer.setStatus("Found duplicates: " + i);
                }
                observer.setProgress(j);
            }

            observer.finished();
            observer.setStatus("Found duplicates: " + i + " out of " + j + " files in " + observer.getTime() + " sec");
        } catch (StopException se) {
            return;
        }

    }

    private void loadScope() throws StopException {
        for (DedupConfiguration configuration : configurations) {
            observer.checkStopped();
            String location = configuration.getLocation();
            File targetDirectory = new File(location);
            loadFilesFromDirectory(targetDirectory);
        }
    }

    private int findScope() throws StopException {
        int total = 0;
        for (DedupConfiguration configuration : configurations) {
            total += countTotalFiles(new File(configuration.getLocation()));
            observer.checkStopped();
        }
        return total;
    }

    private int countTotalFiles(File root) throws StopException {
        int i = 0;
        String[] files = root.list(FileFileFilter.FILE);
        i += files.length;
        File[] dirs = root.listFiles((FileFilter) DirectoryFileFilter.DIRECTORY);
        observer.checkStopped();

        for (File dir : dirs) {
            i += countTotalFiles(dir);
        }
        return i;
    }

    private void loadFilesFromDirectory(File targetDirectory) throws StopException {
        File[] files = targetDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                observer.checkStopped();
                if (file.isFile()) {
                    FileEntry fileEntry = new FileEntry(file, targetDirectory.getPath());
                    fileEntry.load();
                    targets.add(fileEntry);
                    observer.setStatus("Loaded: " + targets.size() + " files");
                    observer.setProgress(targets.size());
                } else if (file.isDirectory()) {
                    loadFilesFromDirectory(file);
                } else {
                    System.out.println("Not Supported!!!");
                }
            }
        }
        observer.setStatus("Loaded: " + targets.size() + " files");
    }

    private boolean checkDuplicates(List<FileEntry> fileEntries, FileEntry fileEntry) {
        for (FileEntry entry : fileEntries) {
            if (fileEntry == entry) {
                continue;
            }
            if (fileEntry.isDuplicate(entry)) {
                return true;
            }
            if (fileEntry.equals(entry)) {
                System.out.println("File 1:" + fileEntry.toString() + " File 2: " + entry.toString());
                fileEntry.addDuplicate(entry);
                return true;
            }
        }
        return false;
    }
}
