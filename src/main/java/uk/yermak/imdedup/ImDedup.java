package uk.yermak.imdedup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by yermak on 12-Oct-16.
 */
public class ImDedup {

    public static void main(String[] args) throws IOException {

        Path path = File.createTempFile("bla", "bla").toPath();
        System.out.println("path = " + path);
        File file = new File("C:\\Users\\Yaroslav");
        Path relativize = file.toPath().relativize(path);
        System.out.println("relativize = " + relativize);
        Path resolve = new File("C:\\Temp\\").toPath().resolve(relativize);
        System.out.println("resolve = " + resolve);
    }
}
