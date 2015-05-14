package system;

import api.Space;
import api.Task;
import computer.ComputerImpl;
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
    protected String spawn(CilkThread t, boolean local, Object... arguments){
        Closure c = new Closure((int) Arrays.stream(arguments).filter(e -> e == null).count(), local, arguments);
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
    protected String spawnNext(CilkThread t, boolean local, Object... arguments){
        Closure c = new Closure((int) Arrays.stream(arguments).filter(e -> e == null).count(), local, arguments);
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
    protected void sendArgument(Continuation k, boolean local){
        try {
            SpaceProxy.getInstance().receiveArgument(k, local);
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
            if (isAtomic()) calculate();
            else decompose();
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

}
