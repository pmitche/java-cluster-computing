package system;

import api.Space;
import api.Task;
import computer.SpaceProxy;
import space.SpaceImpl;

import java.rmi.RemoteException;
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

    /**
     * puts the closure in the map
     * @param t
     * @param arguments
     * @return
     */
    protected String spawn(CilkThread t, Object... arguments){
        Closure c = new Closure((int) Arrays.stream(arguments).filter(e -> e == null).count(),arguments);
        c.setCilkThread(t);
        t.setClosure(c);
        try {
            SpaceProxy.getInstance().put(c);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return c.getId();
    }
    /**
     * sets the ancestor and putting the closure in map
     * @param t
     * @param arguments
     * @return
     */
    protected String spawnNext(CilkThread t, Object... arguments){
        Closure c = new Closure((int) Arrays.stream(arguments).filter(e -> e == null).count(),arguments);
        c.setIsAncestor(true);
        t.setClosure(c);
        c.setCilkThread(t);
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
        if(closure.isAncestor()) compose();
        else decompose((Continuation)closure.getArgument(0));

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

}
