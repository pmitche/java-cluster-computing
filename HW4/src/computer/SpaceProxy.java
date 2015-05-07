package computer;


import api.Result;
import api.Space;
import api.Task;
import space.SpaceImpl;
import system.Closure;
import system.Computer;
import system.Continuation;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Kyrre on 06.05.2015.
 */
public class SpaceProxy implements Space {

    public static SpaceProxy instance;

    private SpaceProxy(){
    }

    public static SpaceProxy getInstance(){
        if (instance == null ) {
            synchronized (SpaceProxy.class) {
                if (instance == null) {
                    instance = new SpaceProxy();
                }
            }
        }
        return instance;
    }

    @Override
    public void put(Task task) throws RemoteException, InterruptedException {
        SpaceImpl.getInstance().put(task);
    }

    @Override
    public void put(Closure closure) throws RemoteException {
        SpaceImpl.getInstance().put(closure);
    }

    @Override
    public void putClosureInReady(Closure closure) throws RemoteException {
        SpaceImpl.getInstance().putClosureInReady(closure);
    }

    @Override
    public Result take() throws RemoteException, InterruptedException {
        return SpaceImpl.getInstance().take();
    }

    @Override
    public void exit() throws RemoteException {
        SpaceImpl.getInstance().exit();
    }

    @Override
    public void register(Computer computer) throws RemoteException {
        SpaceImpl.getInstance().register(computer);
    }

    @Override
    public void putResult(Result r) throws RemoteException {
        SpaceImpl.getInstance().putResult(r);
    }

    @Override
    public Closure takeReadyClosure() throws RemoteException {
        return SpaceImpl.getInstance().takeReadyClosure();
    }

    @Override
    public void receiveArgument(Continuation k) throws RemoteException {
        SpaceImpl.getInstance().receiveArgument(k);
    }

    @Override
    public void closureDone(String id) throws RemoteException {
        SpaceImpl.getInstance().closureDone(id);
    }

    @Override
    public void putAll(Collection<Closure> closures) throws RemoteException {
        SpaceImpl.getInstance().putAll(closures);
    }
}
