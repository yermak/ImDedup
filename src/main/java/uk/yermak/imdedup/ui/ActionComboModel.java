package uk.yermak.imdedup.ui;

import uk.yermak.imdedup.ActionStrategy;

import javax.swing.*;
import javax.swing.event.ListDataListener;

/**
 * Created by yermak on 08-May-17.
 */
class ActionComboModel implements ComboBoxModel {

    private ActionStrategy selectedItem;

    @Override
    public void setSelectedItem(Object selectedItem) {
        this.selectedItem = (ActionStrategy) selectedItem;
    }

    @Override
    public Object getSelectedItem() {
        return selectedItem;
    }

    @Override
    public int getSize() {
        return ActionStrategy.values().length;
    }

    @Override
    public Object getElementAt(int index) {
        return ActionStrategy.values()[index];
    }

    @Override
    public void addListDataListener(ListDataListener l) {

    }

    @Override
    public void removeListDataListener(ListDataListener l) {

    }
}
