package system;

import api.Task;
import space.SpaceImpl;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Kyrre on 23.04.2015.
 */
public class Closure implements Serializable {

    private long id;
    private CilkThread cilkThread;
    private AtomicInteger missingArgsCount;
    private Object[] arguments;
    private boolean isAncestor;

    public Closure(int missingArgsCount, Object... arguments) {
        this.arguments = arguments;
        this.missingArgsCount = new AtomicInteger(missingArgsCount);
        if (arguments.length > 1) {
            if (missingArgsCount != 2) {
                System.out.println();
            }
        }
        this.cilkThread = null;
        this.isAncestor = false;
        this.id = this.hashCode();
        //TODO: can this casue error?
        try {
            this.id = SpaceImpl.getInstance().getNextID();
            SpaceImpl.getInstance().put(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private synchronized void ready() {
        //TODO: kopier variabler in i Cilk thread
  //      System.out.println("Closure; In ready()");
        if (missingArgsCount.get() == 0 && cilkThread != null){
            try {
//                System.out.println("Closure: In ready(), putting closure in space readyQueue");

                isArgsPresent();
                SpaceImpl.getInstance().putClosureInReady(this);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public long getId() {
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
        if (k.getReturnVal() == null){
            System.out.println("dada");
        }
        arguments[k.offset] = k.getReturnVal();
        missingArgsCount.decrementAndGet();
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

    public boolean isArgsPresent() {
        for (Object argument : arguments) {
            if (argument == null){
                return false;
            }
        }
        return true;
    }
}
