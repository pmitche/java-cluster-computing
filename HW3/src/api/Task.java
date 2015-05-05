package api;
import system.Continuation;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * interface for decomposing and composing tasks
 **/
public interface Task<V> extends Serializable, Callable<V>, Runnable
{
    @Override
    V call();

    public void decompose(Continuation c);

    public void compose();
}
