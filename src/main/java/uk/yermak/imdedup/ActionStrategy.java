package uk.yermak.imdedup;

/**
 * Created by yermak on 08-May-17.
 */
public enum ActionStrategy {
    None {
        @Override
        public void perform(FileEntry fileEntry, boolean duplicate) {

        }
    }, Delete {
        @Override
        public void perform(FileEntry fileEntry, boolean duplicate) {
            fileEntry.performDelete();
        }
    }, Copy {
        public boolean needsInput() {
            return true;
        }

        @Override
        public void perform(FileEntry fileEntry, boolean duplicate) {
            fileEntry.performCopy(duplicate);
        }
    }, Move {
        public boolean needsInput() {
            return true;
        }

        @Override
        public void perform(FileEntry fileEntry, boolean duplicate) {
            fileEntry.performMove(duplicate);
        }
    };

    public boolean needsInput() {
        return false;
    }

    public abstract void perform(FileEntry fileEntry, boolean duplicate);
};


