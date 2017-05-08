package uk.yermak.imdedup.ui;

import uk.yermak.imdedup.*;
import uk.yermak.imdedup.compare.ComparatorFactory;
import uk.yermak.imdedup.compare.ImageComparator;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by yermak on 13-Oct-16.
 */
public class DedupWindow {


    private JPanel contentPane;
    private JButton dedupButton;
    private JTextField location1Field;
    private JTextField location2Field;
    private JButton browseButton1;
    private JButton browseButton2;
    private JCheckBox location1Subfolders;
    private JCheckBox location2Subfolders;
    private JProgressBar progress;
    private JTabbedPane settingsTab;
    private JLabel statusLabel;
    private JLabel timerLabel;
    private JCheckBox sumCheck;
    private JCheckBox fileCheck;
    private JCheckBox imageCheck;
    private JComboBox duplicatesActionCombo1;
    private JComboBox duplicatesActionCombo2;
    private JComboBox uniquesActionCombo1;
    private JComboBox uniquesActionCombo2;
    private JButton duplicatesActionBrowse1;
    private JButton duplicatesActionBrowse2;
    private JButton uniquesActionBrowse1;
    private JButton uniquesActionBrowse2;
    private JTextField duplicatesActionLocation1Field;
    private JTextField uniquesActionLocation1Field;
    private JTextField duplicatesActionLocation2Field;
    private JTextField uniquesActionLocation2Field;
    private DedupObserver observer;


    public DedupWindow() {

        buildLocationCombo(duplicatesActionCombo1, duplicatesActionBrowse1, duplicatesActionLocation1Field);
        buildLocationCombo(duplicatesActionCombo2, duplicatesActionBrowse2, duplicatesActionLocation2Field);
        buildLocationCombo(uniquesActionCombo1, uniquesActionBrowse1, uniquesActionLocation1Field);
        buildLocationCombo(uniquesActionCombo2, uniquesActionBrowse2, uniquesActionLocation2Field);

        DedupSettings data = new DedupSettings();
        setData(data);


        observer = new DedupObserver(statusLabel, progress, this.dedupButton, this.timerLabel);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        dedupButton.addActionListener(e -> {
            if (observer.isStarted()) {
                observer.stop();
            } else {
                getData(data);
                ComparisionParams param = data.getComparisonParam();
                ImageComparator imageComparator = ComparatorFactory.comparator(param);
                Dedupler dedupler = new Dedupler(observer, imageComparator, data.getConfiguration1(), data.getConfiguration2());
                executorService.submit(new FutureTask<List>(dedupler, new ArrayList()));
                observer.started();
            }
        });

        browseButton1.addActionListener(e -> selectFolder(browseButton1, location1Field));
        browseButton2.addActionListener(e -> selectFolder(browseButton2, location2Field));
    }

    private void buildLocationCombo(JComboBox actionCombo, JButton actionBrowse, JTextField actionLocation) {
        actionCombo.setModel(new ActionComboModel());
        actionCombo.addActionListener(e -> {
            toggleLocationInputs(actionCombo, actionBrowse, actionLocation);
        });
        actionBrowse.addActionListener(e -> selectFolder(actionBrowse, actionLocation));
    }

    private void toggleLocationInputs(JComboBox duplicatesActionCombo, JButton duplicatesActionBrowse, JTextField duplicatesActionLocationField) {
        ActionStrategy selectedItem = (ActionStrategy) duplicatesActionCombo.getSelectedItem();
        duplicatesActionBrowse.setEnabled(selectedItem.needsInput());
        duplicatesActionLocationField.setEnabled(selectedItem.needsInput());
    }

    private void selectFolder(JButton browseButton, JTextField locationField) {
        JFileChooser fileChooser = new JFileChooser();
        if (locationField.getText() != null) {
            fileChooser.setCurrentDirectory(new File(locationField.getText()));
        }
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (JFileChooser.APPROVE_OPTION == fileChooser.showDialog(browseButton.getParent(), null)) {
            try {
                locationField.setText(fileChooser.getSelectedFile().getCanonicalPath());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ImDedup");
        frame.setContentPane(new DedupWindow().contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void setData(DedupSettings data) {
        location1Field.setText(data.getLocation1());
        location1Subfolders.setSelected(data.isSubfolders1());
        location2Field.setText(data.getLocation2());
        location2Subfolders.setSelected(data.isSubfolders2());
        imageCheck.setSelected(data.isSmartCheck());
        sumCheck.setSelected(data.isChecksumCheck());
        fileCheck.setSelected(data.isDataCheck());
        duplicatesActionCombo1.setSelectedItem(data.getDuplicatesAction1());
        duplicatesActionCombo2.setSelectedItem(data.getDuplicatesAction2());
        uniquesActionCombo1.setSelectedItem(data.getUniquesAction1());
        uniquesActionCombo2.setSelectedItem(data.getUniquesAction2());
        duplicatesActionLocation1Field.setText(data.getDuplicatesLocation1());
        duplicatesActionLocation2Field.setText(data.getDuplicatesLocation2());
        uniquesActionLocation1Field.setText(data.getUniquesLocation1());
        uniquesActionLocation2Field.setText(data.getUniquesLocation2());
    }

    public void getData(DedupSettings data) {
        data.setLocation1(location1Field.getText());
        data.setSubfolders1(location1Subfolders.isSelected());
        data.setLocation2(location2Field.getText());
        data.setSubfolders2(location2Subfolders.isSelected());
        data.setSmartCheck(imageCheck.isSelected());
        data.setChecksumCheck(sumCheck.isSelected());
        data.setDataCheck(fileCheck.isSelected());
        data.setDuplicatesAction1((ActionStrategy) duplicatesActionCombo1.getSelectedItem());
        data.setDuplicatesAction2((ActionStrategy) duplicatesActionCombo2.getSelectedItem());
        data.setUniquesAction1((ActionStrategy) uniquesActionCombo1.getSelectedItem());
        data.setUniquesAction2((ActionStrategy) uniquesActionCombo2.getSelectedItem());
        data.setDuplicatesLocation1(duplicatesActionLocation1Field.getText());
        data.setDuplicatesLocation2(duplicatesActionLocation2Field.getText());
        data.setUniquesLocation1(uniquesActionLocation1Field.getText());
        data.setUniquesLocation2(uniquesActionLocation2Field.getText());
    }

    public boolean isModified(DedupSettings data) {
        if (location1Field.getText() != null ? !location1Field.getText().equals(data.getLocation1()) : data.getLocation1() != null)
            return true;
        if (location1Subfolders.isSelected() != data.isSubfolders1()) return true;
        if (location2Field.getText() != null ? !location2Field.getText().equals(data.getLocation2()) : data.getLocation2() != null)
            return true;
        if (location2Subfolders.isSelected() != data.isSubfolders2()) return true;
        if (imageCheck.isSelected() != data.isSmartCheck()) return true;
        if (sumCheck.isSelected() != data.isChecksumCheck()) return true;
        if (fileCheck.isSelected() != data.isDataCheck()) return true;
        return false;
    }


}
