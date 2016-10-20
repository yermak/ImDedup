package uk.yermak.imdedup;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * Created by yermak on 18-Oct-16.
 */
public class FileEntry {
    private File file;
    private String location;
    private long crc32 = 0;
    LinkedHashSet<FileEntry> duplicates = new LinkedHashSet<>();
    //    private static ExecutorService pool = Executors.newFixedThreadPool(1);
//    private CountDownLatch latch;

    public FileEntry(File file, String location) {
        this.file = file;
        this.location = location;
//        latch = new CountDownLatch(1);
//        pool.submit(this);
    }

    public void addDuplicate(FileEntry entry) {
        if (this == entry || duplicates.contains(entry)) return;
        entry.duplicates.addAll(duplicates);
        duplicates = entry.duplicates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileEntry fileEntry = (FileEntry) o;

        return file.equals(fileEntry.file);

    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return file.getPath();
    }

    public boolean isDuplicate(FileEntry entry) {
        return duplicates.contains(entry);
    }

    public long getCrc32() throws IOException {
//        try {
//            latch.await();
        return crc32;
//        } catch (InterruptedException e) {
//            return 0;
//        }
    }

    public void load() {
        if (crc32 == 0) {
            try {
                Checksum checksum = new CRC32();
                byte[] ownBytes = FileUtils.readFileToByteArray(file);
                checksum.update(ownBytes, 0, ownBytes.length);
                crc32 = checksum.getValue();
                System.out.println("Checksum calculated for = " + file.getPath() + " :" + crc32);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            latch.countDown();
        }
    }

    public long getLength() {
        return file.length();
    }

    public byte[] getBytes() throws IOException {
        return FileUtils.readFileToByteArray(file);
    }

    public String getFileName() {
        return file.getName();
    }
}
