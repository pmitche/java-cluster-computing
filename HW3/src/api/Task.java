package api;
import system.Continuation;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Callable;

public interface Task<V> extends Serializable, Callable<V>, Runnable
{   /**
    * 
    **/
    @Override
    V call();

    public void decompose(Continuation c);

    public void compose(List<Continuation> list);
}
