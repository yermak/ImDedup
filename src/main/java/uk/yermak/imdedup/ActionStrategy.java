package uk.yermak.imdedup;

/**
 * Created by yermak on 08-May-17.
 */
public enum ActionStrategy {
    None, Delete, Copy {
        public boolean needsInput() {
            return true;
        }
    }, Move {
        public boolean needsInput() {
            return true;
        }
    };

    public boolean needsInput() {
        return false;
    }
};


