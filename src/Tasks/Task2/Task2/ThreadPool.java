package Tasks.Task2.Task2;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool implements AutoCloseable  {
    private final int threadCount;
    private List<Thread> threads;

    public boolean isTerminated;

    private final BlockingQueue<IMyTask<?>> taskQueue;

    public ThreadPool(int threadCount) {
        if (threadCount < 1) {
            throw new IllegalArgumentException("Thread count must be positive");
        }
        this.threadCount = threadCount;
        this.isTerminated = false;
        this.taskQueue = new LinkedBlockingQueue<>();
        this.threads = new ArrayList<>(threadCount);

        for (int i = 0; i < this.threadCount; i++) {
//            switch (balancingStrategy) {
//                case WORK_SHARING:
//                    break;
//                case WORK_STEALING:
//                    break;
//            }
        }
    }

    public <TResult> void enqueue(IMyTask<TResult> task) {
        if (this.isTerminated) {
            throw new IllegalStateException("Thread pool is closed");
        }
        this.taskQueue.add(task);
    }

    @Override
    public void close() throws Exception {
        this.isTerminated = true;
        threads.forEach(Thread::interrupt);
    }
}
