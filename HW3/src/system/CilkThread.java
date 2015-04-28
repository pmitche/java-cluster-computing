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

    protected Closure closure;

    public CilkThread(Closure closure){
        this.closure = closure;
        if (this.closure != null){
            this.closure.setCilkThread(this);
        }
    }

    protected long spawn(CilkThread t, Object... arguments){
        Closure c = new Closure((int) Arrays.stream(arguments).filter(e -> !Optional.ofNullable(e).isPresent()).count(),arguments);
        c.setCilkThread(t);
        t.setClosure(c);
        SpaceImpl.getInstance().put(c);
        return c.getId();
    }
    protected long spawnNext(CilkThread t, Object... arguments){
        Closure c = new Closure((int) Arrays.stream(arguments).filter(e -> !Optional.ofNullable(e).isPresent()).count(),arguments);
        c.setCilkThread(t);
        c.setIsAncestor(true);
        t.setClosure(c);
        SpaceImpl.getInstance().put(c);
        return c.getId();
    }
    protected void sendArgument(Continuation k){
        SpaceImpl.getInstance().receiveArgument(k);
    }

    public void setClosure(Closure closure) {
        this.closure = closure;
    }
}
