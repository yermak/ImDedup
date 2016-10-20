package uk.yermak.imdedup;


import org.apache.commons.io.FileUtils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
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
//        readMetadata();


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

    private void readMetadata() {
        try {
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

            if (readers.hasNext()) {

                // pick the first available ImageReader
                ImageReader reader = readers.next();

                // attach source to the reader
                reader.setInput(iis, true);

                // read metadata of first image
                IIOMetadata metadata = reader.getImageMetadata(0);

                String[] names = metadata.getMetadataFormatNames();
                int length = names.length;
                for (int i = 0; i < length; i++) {
                    System.out.println( "Format name: " + names[ i ] );
                    displayMetadata(metadata.getAsTree(names[i]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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


    void displayMetadata(Node root) {
        displayMetadata(root, 0);
    }

    void indent(int level) {
        for (int i = 0; i < level; i++)
            System.out.print("    ");
    }

    void displayMetadata(Node node, int level) {
        // print open tag of element
        indent(level);
        System.out.print("<" + node.getNodeName());
        NamedNodeMap map = node.getAttributes();
        if (map != null) {

            // print attribute values
            int length = map.getLength();
            for (int i = 0; i < length; i++) {
                Node attr = map.item(i);
                System.out.print(" " + attr.getNodeName() +
                        "=\"" + attr.getNodeValue() + "\"");
            }
        }

        Node child = node.getFirstChild();
        if (child == null) {
            // no children, so close element and return
            System.out.println("/>");
            return;
        }

        // children, so close current tag
        System.out.println(">");
        while (child != null) {
            // print children recursively
            displayMetadata(child, level + 1);
            child = child.getNextSibling();
        }

        // print close tag of element
        indent(level);
        System.out.println("</" + node.getNodeName() + ">");
    }
}
