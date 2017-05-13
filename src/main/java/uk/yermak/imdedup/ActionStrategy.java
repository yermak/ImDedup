package uk.yermak.imdedup;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by yermak on 08-May-17.
 */
public enum ActionStrategy {
    None {
        @Override
        public void perform(File file, String location) {
            //
        }
    }, Delete {
        @Override
        public void perform(File file, String location) {
            file.delete();
        }
    }, Copy {
        public boolean needsInput() {
            return true;
        }

        @Override
        public void perform(File file, String location) {
            try {
                FileUtils.copyFile(file, new File(location, file.getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }, Move {
        public boolean needsInput() {
            return true;
        }

        @Override
        public void perform(File file, String location) {
            file.renameTo(new File(location, file.getName()));
//            java.nio.file.Files.delete(new );
        }
    };

    public boolean needsInput() {
        return false;
    }

    public abstract void perform(File file, String location);
};


