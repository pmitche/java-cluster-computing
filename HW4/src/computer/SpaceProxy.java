package computer;


import api.Result;
import api.Space;
import api.Task;
import space.SpaceImpl;
import system.Closure;
import system.Continuation;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Kyrre on 06.05.2015.
 */
public class SpaceProxy implements Space {

    public static SpaceProxy instance;
    private LinkedBlockingQueue<QueueTicket> waitingQueue;

    private SpaceProxy(){
        this.waitingQueue = new LinkedBlockingQueue<>();
        if (ASYNC){
            new Thread(new SpaceProxyAsyncHandler(waitingQueue) {
            }).start();
        }
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
    public void put(Closure closure) throws RemoteException {
        if (!ASYNC){
            SpaceImpl.getInstance().put(closure);
            return;
        }
        try {
            waitingQueue.put(new QueueTicket(QueueTicket.Type.PUT_CLOSURE, closure));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void putClosureInReady(Closure closure) throws RemoteException {
        if (!ASYNC){
            SpaceImpl.getInstance().putClosureInReady(closure);
            return;
        }
        try {
            waitingQueue.put(new QueueTicket(QueueTicket.Type.PUT_CLOSURE_IN_READY, closure));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveArgument(Continuation k) throws RemoteException {
        if (!ASYNC){
            SpaceImpl.getInstance().receiveArgument(k);
            return;
        }
        try {
            waitingQueue.put(new QueueTicket(QueueTicket.Type.RECEIVE_ARGUMENT, k));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closureDone(String id) throws RemoteException {
        if (!ASYNC){
            SpaceImpl.getInstance().closureDone(id);
            return;
        }
        try {
            waitingQueue.put(new QueueTicket(QueueTicket.Type.CLOSURE_DONE, id));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Does the same as SpaceImpl, meaning it is not available in ASYNC mode.
     * @param r
     * @throws RemoteException
     */
    @Override
    public void putResult(Result r) throws RemoteException {
        SpaceImpl.getInstance().putResult(r);
    }

    /**
     * Does the same as SpaceImpl, meaning it is not available in ASYNC mode.
     * @return
     * @throws RemoteException
     * @throws InterruptedException
     */
    @Override
    public Result take() throws RemoteException, InterruptedException {
        return SpaceImpl.getInstance().take();
    }

    /**
     * Does the same as SpaceImpl, meaning it is not available in ASYNC mode.
     * @throws RemoteException
     */
    @Override
    public void exit() throws RemoteException {
        SpaceImpl.getInstance().exit();
    }

    /**
     * Does the same as SpaceImpl, meaning it is not available in ASYNC mode.
     * @param computer
     * @throws RemoteException
     */
    @Override
    public void register(Computer computer) throws RemoteException {
        SpaceImpl.getInstance().register(computer);
    }

    /**
     * Does the same as SpaceImpl, meaning it is not available in ASYNC mode.
     * @return
     * @throws RemoteException
     */
    @Override
    public Closure takeReadyClosure() throws RemoteException {
        return SpaceImpl.getInstance().takeReadyClosure();
    }

    /**
     * Does the same as SpaceImpl, meaning it is not available in ASYNC mode.
     * @param closures
     * @throws RemoteException
     */
    @Override
    public void putAll(Collection<Closure> closures) throws RemoteException {
        SpaceImpl.getInstance().putAll(closures);
    }

    /**
     * Does the same as SpaceImpl, meaning it is not available in ASYNC mode.
     * @param task
     * @throws RemoteException
     * @throws InterruptedException
     */
    @Override
    public void put(Task task) throws RemoteException, InterruptedException {
        SpaceImpl.getInstance().put(task);
    }
}
