package Tasks.Task2.Task2;

import java.util.concurrent.Callable;
import java.util.function.Function;

public class MyTask<TResult> implements IMyTask<TResult> {
    private final Callable<TResult> task;
    private volatile TResult result = null;
    private volatile Exception suppressedException = null;


    public MyTask(Callable<TResult> task) {
        this.task = task;
    }

    @Override
    public boolean isCompleted() {
        return result!=null;
    }

    @Override
    public TResult getResult() {
        return result;
    }

    public void setResult(TResult value) {
        this.result = value;
    }

    public void start() {
        try {
            result = task.call();
        } catch (Exception exc) {
            suppressedException = exc;
        }
    }

    @Override
    public <TNewResult> IMyTask<TNewResult> continueWith(Function<TResult, TNewResult> task) {
        return new MyTask(() -> task.apply(this.getResult()));
    }
}
