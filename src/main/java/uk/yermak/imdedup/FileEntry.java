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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * Created by yermak on 18-Oct-16.
 */
public class FileEntry {
    private File file;
    private DedupConfiguration configuration;
    private long crc32 = 0;
    private LinkedHashSet<FileEntry> duplicates = new LinkedHashSet<>();
    //    private static ExecutorService pool = Executors.newFixedThreadPool(1);
//    private CountDownLatch latch;

    public FileEntry(File file, DedupConfiguration configuration) {
        this.file = file;
        this.configuration = configuration;
    }


    public void addDuplicate(FileEntry entry) {
        if (this == entry || duplicates.contains(entry)) return;
        LinkedHashSet<FileEntry> tmp = new LinkedHashSet<>();
        tmp.addAll(duplicates);
        tmp.addAll(entry.duplicates);
        tmp.add(this);
        tmp.add(entry);

        duplicates = tmp;
        entry.duplicates = tmp;

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

    public boolean isDuplicated(FileEntry entry) {
        return duplicates.contains(entry);
    }

    public long getCrc32()  {
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
                crc32 = FileUtils.checksumCRC32(file);
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
                for (String name : names) {
                    System.out.println("Format name: " + name);
                    displayMetadata(metadata.getAsTree(name));
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


    private void displayMetadata(Node root) {
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

    public void performAction(boolean duplicate) {
        if (duplicate) {
            System.out.println("Duplicate file = " + file + " was " + configuration.getDuplicatesAction() + " to  " + configuration.getDuplicatesLocation());
            configuration.getDuplicatesAction().perform(this, duplicate);
        } else {
            System.out.println("Unique file = " + file + " was " + configuration.getUniquesAction() + " to  " + configuration.getUniquesLocation());
            configuration.getUniquesAction().perform(this, duplicate);
        }
    }

    public LinkedHashSet<FileEntry> getDuplicates() {
        return duplicates;
    }

    void performCopy(boolean duplicate) {
        try {
            Path resolved = getResolvedPath(duplicate);
            Files.copy(file.toPath(), resolved, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path getResolvedPath(boolean duplicate) throws IOException {
        String dist = duplicate ? configuration.getDuplicatesLocation() : configuration.getUniquesLocation();
        Path source = new File(configuration.getLocation()).toPath();
        Path relativize = source.relativize(file.toPath());
        Path resolve = new File(dist).toPath().resolve(relativize);
        Files.createDirectories(resolve.getParent());
        return resolve;
    }

    void performMove(boolean duplicate) {
        try {
            Path resolved = getResolvedPath(duplicate);
            Files.move(file.toPath(), resolved, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void performDelete() {
        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
