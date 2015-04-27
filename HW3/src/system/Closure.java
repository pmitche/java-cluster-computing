package system;

import api.Task;
import space.SpaceImpl;

import java.util.ArrayList;

/**
 * Created by Kyrre on 23.04.2015.
 */
public class Closure {

    private final long id;
    private CilkThread cilkThread;
    private int missingArgsCount;
    private Object[] arguments;

    public Closure(int missingArgsCount, Object... arguments) {
        this.arguments = arguments;
        this.missingArgsCount = missingArgsCount;
        this.id = this.hashCode();
        this.cilkThread = null;
    }

    private void ready() {
        if (missingArgsCount == 0 && cilkThread != null){
            SpaceImpl.getInstance().putClosureInReady(this);
        }
    }

    public long getId() {
        return id;
    }

    public synchronized void call(){
        cilkThread.call();
    }

    public synchronized void setCilkThread(CilkThread cilkThread) {
        this.cilkThread = cilkThread;
        ready();
    }

    public synchronized void setArgument(Continuation k) {
        arguments[k.offset] = k.argument;
        missingArgsCount--;
        ready();
    }
}
