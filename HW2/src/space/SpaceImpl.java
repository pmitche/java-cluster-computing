package space;

import api.Result;
import api.Space;
import api.Task;
import system.Computer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Kyrre on 13.04.2015.
 */
public class SpaceImpl extends UnicastRemoteObject implements Space {

    private LinkedBlockingQueue<Task> taskQueue;
    private LinkedBlockingQueue<Result> resultQueue;

    protected SpaceImpl() throws RemoteException {
        this.taskQueue = new LinkedBlockingQueue<Task>();
        this.resultQueue = new LinkedBlockingQueue<Result>();
    }

    @Override
    public void putAll(List<Task> taskList) throws RemoteException {

    }

    @Override
    public Result take() throws RemoteException {
        return null;
    }

    @Override
    public void exit() throws RemoteException {

    }

    @Override
    public void register(Computer computer) throws RemoteException {
        System.out.println("Computer registered");
    }

    public static void main(String[] args) throws RemoteException {
        System.setSecurityManager( new SecurityManager() );
        LocateRegistry.createRegistry(Space.PORT).rebind(Space.SERVICE_NAME, new SpaceImpl());
        System.out.println("Space running...");
    }
}
