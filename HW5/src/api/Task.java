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

    /**
     * Decomposition happens in this method
     * @param c
     */
    public void decompose(Continuation c);

    /**
     * composition happens in this method
     */
    public void compose();
}
