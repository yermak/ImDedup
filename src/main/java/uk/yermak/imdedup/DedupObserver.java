package uk.yermak.imdedup;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by yermak on 18-Oct-16.
 */
public class DedupObserver {

    private JLabel statusLabel;
    private JProgressBar progress;
    private JButton dedupButton;
    private long start;
    private AtomicBoolean started = new AtomicBoolean(false);
    private long finish;
    private Timer watchdog;
    private JLabel timerLabel;
    private int fileCounter;

    public DedupObserver(JLabel statusLabel, JProgressBar progress, JButton dedupButton, JLabel timerLabel) {
        this.statusLabel = statusLabel;
        this.progress = progress;
        this.dedupButton = dedupButton;
        this.watchdog = new Timer(1000, e -> timerLabel.setText((System.currentTimeMillis() - start)/1000+" sec"));
        this.timerLabel = timerLabel;
        watchdog.setInitialDelay(2000);

    }

    public void setStatus(String text) {
        SwingUtilities.invokeLater(() -> statusLabel.setText(text));
    }

    public void setProgress(int value) {
        SwingUtilities.invokeLater(() -> progress.setValue(value));
    }

    private void setMaxProgress(int value) {
        SwingUtilities.invokeLater(() -> progress.setMaximum(value));
    }

    public void started() {
        start = System.currentTimeMillis();
        started.set(true);
        dedupButton.setText("Stop");
        watchdog.start();
    }

    public boolean isStarted() {
        return started.get();
    }

    public void stop() {
        started.set(false);
        watchdog.stop();
        timerLabel.setText("0 sec");
    }

    public void checkStopped() throws StopException {
        if (!isStarted()) throw new StopException();

    }

    public void finished() {
        finish = System.currentTimeMillis();
        watchdog.stop();
        dedupButton.setText("Dedup");
        timerLabel.setText("0 sec");
        started.set(false);
    }


    public long getTime() {
        return (finish - start) / 1000;
    }

    public void stopped() {
        dedupButton.setText("Dedup");
        setStatus("Cancelled");
        setProgress(0);
    }

    public void setFoundMoreFiles(int increment) {
        fileCounter+=increment;
        setStatus("Found "+ fileCounter + " files");
    }

    public void lookingForScope() {
        progress.setIndeterminate(true);
    }

    public void scopeFound(int total) {
        progress.setIndeterminate(false);
        setMaxProgress(total);
    }
}
