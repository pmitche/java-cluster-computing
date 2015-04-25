package api;
import java.io.Serializable;
import java.util.concurrent.Callable;

public interface Task<V> extends Serializable, Callable<V>, Runnable
{
    @Override
    V call();
}
