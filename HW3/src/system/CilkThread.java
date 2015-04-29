package system;

import api.Task;
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
    protected long spawn(CilkThread t, Object... arguments){
        Closure c = new Closure((int) Arrays.stream(arguments).filter(e -> !Optional.ofNullable(e).isPresent()).count(),arguments);
        c.setCilkThread(t);
        t.setClosure(c);
        try {
    //        System.out.println("CilkThread; spawn() Putting closure in map");
            SpaceImpl.getInstance().put(c);
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
    protected long spawnNext(CilkThread t, Object... arguments){
        Closure c = new Closure((int) Arrays.stream(arguments).filter(e -> !Optional.ofNullable(e).isPresent()).count(),arguments);
        c.setIsAncestor(true);
        t.setClosure(c);
        c.setCilkThread(t);
        try {
  //          System.out.println("CilkThread; spawnNext() Putting closure in map");
            SpaceImpl.getInstance().put(c);
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
//        System.out.println("CilkThread; Sending Continuation to Space");
        try {
            SpaceImpl.getInstance().receiveArgument(k);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setClosure(Closure closure) {
        this.closure = closure;
    }
}
