package system;

import api.Task;
import space.SpaceImpl;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Kyrre on 25.04.2015.
 */
public abstract class CilkThread implements Runnable, Task {

    protected final Closure closure;

    public CilkThread(Closure closure){
        this.closure = closure;
        this.closure.setCilkThread(this);
    }

    protected long spawn(Object... arguments){

        Closure c = new Closure((int) Arrays.stream(arguments).filter(e -> !Optional.ofNullable(e).isPresent()).count(),arguments);
        SpaceImpl.getInstance().putClosure(c);
        return c.getId();
    }
    protected long spawnNext(Object... arguments){
        Closure c = new Closure((int) Arrays.stream(arguments).filter(e -> !Optional.ofNullable(e).isPresent()).count(),arguments);
        SpaceImpl.getInstance().putClosure(c);
        return c.getId();
    }
    protected void sendArgument(Continuation k){
        SpaceImpl.getInstance().receiveArgument(k);
    }
}
