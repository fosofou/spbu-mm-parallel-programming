package Tasks.Task2.Task2;


import java.util.function.Function;

public interface IMyTask<TResult> {
    boolean isCompleted();

    void start();

    TResult getResult();

    <TNewResult> IMyTask<TNewResult> continueWith(Function<TResult, TNewResult> task);
}
