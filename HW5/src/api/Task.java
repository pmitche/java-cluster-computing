package api;
import system.Closure;
import system.Continuation;
import system.Global;

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
    void decompose();

    /**
     * composition happens in this method
     */
    void compose();

    Closure getClosure();
}
