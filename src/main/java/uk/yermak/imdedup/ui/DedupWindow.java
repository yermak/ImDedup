package uk.yermak.imdedup.ui;

import uk.yermak.imdedup.DedupObserver;
import uk.yermak.imdedup.DedupSettings;
import uk.yermak.imdedup.Dedupler;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private DedupObserver observer;


    public DedupWindow() {
        DedupSettings data = new DedupSettings();
        setData(data);

        observer = new DedupObserver(statusLabel, progress, this.dedupButton, this.timerLabel);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        dedupButton.addActionListener(e -> {
            if (observer.isStarted()) {
                observer.stop();
            } else {
                getData(data);
                Dedupler dedupler = new Dedupler(observer, data.getConfiguration1(), data.getConfiguration2());
                executorService.submit(dedupler);
                observer.started();
            }
        });


        browseButton1.addActionListener(e -> selectFolder(browseButton1, location1Field));
        browseButton2.addActionListener(e -> selectFolder(browseButton2, location2Field));
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
    }

    public void getData(DedupSettings data) {
        data.setLocation1(location1Field.getText());
        data.setSubfolders1(location1Subfolders.isSelected());
        data.setLocation2(location2Field.getText());
        data.setSubfolders2(location2Subfolders.isSelected());
    }

    public boolean isModified(DedupSettings data) {
        if (location1Field.getText() != null ? !location1Field.getText().equals(data.getLocation1()) : data.getLocation1() != null)
            return true;
        if (location1Subfolders.isSelected() != data.isSubfolders1()) return true;
        if (location2Field.getText() != null ? !location2Field.getText().equals(data.getLocation2()) : data.getLocation2() != null)
            return true;
        return location2Subfolders.isSelected() != data.isSubfolders2();
    }
}
