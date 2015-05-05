package system;

import api.Task;
import space.SpaceImpl;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Kyrre on 23.04.2015.
 */
public class Closure implements Serializable {

    private String id;
    private CilkThread cilkThread;
    private int missingArgsCount;
    private Object[] arguments;
    private boolean isAncestor;

    public Closure(int missingArgsCount, Object... arguments) {
        this.arguments = arguments;
        this.missingArgsCount = missingArgsCount;
        this.cilkThread = null;
        this.isAncestor = false;
        this.id =  UUID.randomUUID().toString();
        try {
            SpaceImpl.getInstance().put(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private synchronized void ready() {
        if (missingArgsCount == 0 && cilkThread != null){
            try {
                SpaceImpl.getInstance().putClosureInReady(this);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public String getId() {
        return id;
    }

    public synchronized void call(){
        cilkThread.setClosure(this);
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

    public Object[] getArguments() {
        return arguments.clone();
    }

    public boolean isAncestor() {
        return isAncestor;
    }

    public void setIsAncestor(boolean isAncestor) {
        this.isAncestor = isAncestor;
    }
}
