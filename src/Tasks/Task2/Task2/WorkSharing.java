package Tasks.Task2.Task2;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class WorkSharingThread extends Thread {
    private static final int DIFF_THRESHOLD = 10;
    private final BlockingQueue<IMyTask<?>> taskQueue;
    private final Random random;

    public WorkSharingThread(BlockingQueue<IMyTask<?>> taskQueue) {
        this.taskQueue = taskQueue;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                IMyTask<?> task = taskQueue.take();
                if (task != null) {
                    task.start();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void balance(BlockingQueue<IMyTask<?>> queue1, BlockingQueue<IMyTask<?>> queue2) {
        int diff = queue1.size() - queue2.size();
        if (Math.abs(diff) < DIFF_THRESHOLD) {
            return;
        }
        BlockingQueue<IMyTask<?>> bigQueue = diff < 0 ? queue2 : queue1;
        BlockingQueue<IMyTask<?>> smallQueue = diff < 0 ? queue1 : queue2;
        while (bigQueue.size() > smallQueue.size()) {
            smallQueue.put(bigQueue.popTail());
        }
    }
}
