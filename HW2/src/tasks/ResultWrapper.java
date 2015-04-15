package tasks;

/**
 * Created by Kyrre on 15.04.2015.
 */
public class ResultWrapper<T> {
    private int n;
    private final T taskReturnValue;

    public ResultWrapper(T taskReturnValue, int n) {
        this.taskReturnValue = taskReturnValue;
        this.n = n;
    }

    public int getN() {
        return n;
    }

    public T getTaskReturnValue() {
        return taskReturnValue;
    }
}
