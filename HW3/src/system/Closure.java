package system;

import api.Task;
import space.SpaceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kyrre on 23.04.2015.
 */
public class Closure {

    private final long id;
    private CilkThread cilkThread;
    private int missingArgsCount;
    private Object[] arguments;
    private boolean isAncestor;

    public Closure(int missingArgsCount, Object... arguments) {
        this.arguments = arguments;
        this.missingArgsCount = missingArgsCount;
        this.id = this.hashCode();
        this.cilkThread = null;
        this.isAncestor = false;
    }

    private void ready() {
        //TODO: kopier variabler in i Cilk thread
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
        arguments[k.offset] = k.getReturnVal();
        missingArgsCount--;
        ready();
    }

    public Object getArgument(int i) {
        return arguments[i];
    }

    public List getArguments() {
        return Arrays.asList(arguments);
    }

    public boolean isAncestor() {
        return isAncestor;
    }

    public void setIsAncestor(boolean isAncestor) {
        this.isAncestor = isAncestor;
    }
}
