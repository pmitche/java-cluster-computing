package system;

import computer.SpaceProxy;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 * Created by Kyrre on 23.04.2015.
 */
public class Closure implements Serializable, Comparable<Closure> {

    private String id;
    private CilkThread cilkThread;
    private int missingArgsCount;
    private Object[] arguments;
    private boolean isAncestor;
    private Global global;

    public Closure(int missingArgsCount, Global global, Object... arguments) {
        this.arguments = arguments;
        this.missingArgsCount = missingArgsCount;
        this.global = global;
        this.cilkThread = null;
        this.isAncestor = false;
        this.id =  UUID.randomUUID().toString();
        try {
            SpaceProxy.getInstance().put(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Marks this closure as ready in space
     */
    private synchronized void ready() {
        if (missingArgsCount == 0 && cilkThread != null){
            try {
                SpaceProxy.getInstance().putClosureInReady(this);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns the closure ID
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * Calls the Thread associated with the closure.
     */
    public synchronized void call(){
        cilkThread.setClosure(this);
        cilkThread.call();
    }

    /**
     * Sets the thread
     * @param cilkThread Thread to be set
     */
    public synchronized void setCilkThread(CilkThread cilkThread) {
        this.cilkThread = cilkThread;
        ready();
    }

    /**
     * Sets an argument to the continuation
     * @param k continuation
     */
    public synchronized void setArgument(Continuation k) {
        arguments[k.offset] = k.getReturnVal();
        missingArgsCount--;
        ready();
    }

    /**
     * @param i index of the argumtent
     * @return  The argument requested
     */
    public Object getArgument(int i) {
        return arguments[i];
    }

    /**
     * Tells you if the closure is an ancestor
     */
    public boolean isAncestor() {
        return isAncestor;
    }

    /**
     * @param isAncestor    Boolean value marking the closure an ancestor or not.
     */
    public void setIsAncestor(boolean isAncestor) {
        this.isAncestor = isAncestor;
    }

    public Global getGlobal() {
        return global;
    }


    public void updateGlobal(Global global) {
        this.global = global.findBest(this.global);
        try {
            SpaceProxy.getInstance().updateGlobal(this.global);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Should only ever be used by Space. If the global variable should be updated, this should happen via updateGlobal().
     * @param global
     */
    public void setGlobal(Global global) {
        this.global = global.findBest(this.global);
    }

    public double getHeuristic(){
        if(cilkThread==null) return Double.MAX_VALUE; //TODO HAX
        return cilkThread.getHeuristic();
    }

    @Override
    public int compareTo(Closure o) {
        if (getHeuristic() - o.getHeuristic() < 0){
            return -1;
        }else if (getHeuristic() - o.getHeuristic() > 0){
            return 1;
        }
        return 0;
    }
}
