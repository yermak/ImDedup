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
    private boolean stopped;
    private long finish;


    public DedupObserver(JLabel statusLabel, JProgressBar progress, JButton dedupButton) {
        this.statusLabel = statusLabel;
        this.progress = progress;
        this.dedupButton = dedupButton;
    }

    public void setStatus(String text) {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText(text);
        });
    }

    public void setProgress(int value) {
        SwingUtilities.invokeLater(() -> {
            progress.setValue(value);
        });
    }

    public void setMaxProgress(int value) {
        SwingUtilities.invokeLater(() -> {
            progress.setMaximum(value);
        });
    }

    public void started() {
        start = System.currentTimeMillis();
        started.set(true);
        dedupButton.setText("Stop");
    }

    public boolean isStarted() {
        return started.get();
    }

    public void stopped() {
        started.set(false);
    }

    public void checkStopped() throws StopException {
        if (!isStarted()) throw new StopException();

    }

    public void finished() {
        finish = System.currentTimeMillis();
    }


    public long getTime() {
        return (finish - start) / 1000;
    }
}
