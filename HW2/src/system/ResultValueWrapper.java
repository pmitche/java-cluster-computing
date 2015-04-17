package system;

import java.io.Serializable;

/**
 * Created by Kyrre on 15.04.2015.
 * A simple wrapper class. Its is used as taskReturnValue in the Result class.
 * Its function is to allow for additional information to be passed through a Result object.
 */
public class ResultValueWrapper<T,N> implements Serializable {
    private N n;
    private final T taskReturnValue;

    public ResultValueWrapper(T taskReturnValue, N n) {
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
