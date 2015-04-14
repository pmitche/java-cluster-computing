package api;
import java.io.Serializable;
import java.util.concurrent.Callable;

public interface Task<V> extends Serializable, Callable<V>
{
    @Override
    V call();
}