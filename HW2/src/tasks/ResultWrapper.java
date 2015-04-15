package tasks;

import java.io.Serializable;

/**
 * Created by Kyrre on 15.04.2015.
 */
public class ResultWrapper<T,N> implements Serializable {
    private N n;
    private final T taskReturnValue;

    public ResultWrapper(T taskReturnValue, N n) {
        this.taskReturnValue = taskReturnValue;
        this.n = n;
    }

    public N getN() {
        return n;
    }

    public T getTaskReturnValue() {
        return taskReturnValue;
    }
}
