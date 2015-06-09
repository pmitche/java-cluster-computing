package system;

import api.Task;
import computer.SpaceProxy;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Kyrre on 25.04.2015.
 */
public abstract class CilkThread implements Runnable, Task {

    protected Closure closure;
    /**
     * Lower number is better
     */
    protected double heuristic;

    public CilkThread(Closure closure){
        this.closure = closure;
        heuristic = Double.MAX_VALUE;
        if (this.closure != null){
            this.closure.setCilkThread(this);
        }
    }

    public double getHeuristic() {
        return heuristic;
    }

    /**
     * puts the closure in the map
     * @param t
     * @param arguments
     * @return
     */
    protected String spawn(CilkThread t, Object... arguments){
        Closure c = new Closure((int) Arrays.stream(arguments).filter(e -> e == null).count(), closure.getGlobal(), arguments);
        c.setCilkThread(t);
        t.setClosure(c);
        t.heuristic = t.genHeuristic();
        try {
            SpaceProxy.getInstance().put(c);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return c.getId();
    }

    protected abstract double genHeuristic();

    /**
     * sets the ancestor and putting the closure in map
     * @param t
     * @param arguments
     * @return
     */
    protected String spawnNext(CilkThread t, Object... arguments){
        if (arguments == null){
            arguments = new Object[]{null};
        }
        Closure c = new Closure((int) Arrays.stream(arguments).filter(e -> e == null).count(), closure.getGlobal(), arguments);
        c.setIsAncestor(true);
        t.setClosure(c);
        c.setCilkThread(t);
        t.heuristic = 0;
        try {
            SpaceProxy.getInstance().put(c);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return c.getId();
    }

    /**
     * method for sending continuation to Space
     * @param k
     */
    protected void sendArgument(Continuation k){
        try {
            SpaceProxy.getInstance().receiveArgument(k);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setClosure(Closure closure) {
        this.closure = closure;
    }

    /**
     * Runs the CilkThread task instance and decides weather it should decompose or compose.
     */
    @Override
    public void run() {
        //Decide what to do
        if(closure.isAncestor()) compose();
        else {
            decompose();
        }

        //Register closure as done
        try {
            SpaceProxy.getInstance().closureDone(closure.getId());
        } catch (RemoteException e) {e.printStackTrace();}
    }

    /**
     * Starting the Thread
     * @return nullsafe Optional object.
     */
    @Override
    public Object call() {
        Thread t = new Thread(this);
        t.start();
        return Optional.empty();
    }

    @Override
    public Closure getClosure() {
        return closure;
    }
}
