package space;

import api.Result;
import api.Space;
import api.Task;
import system.Computer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
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
        taskQueue.add(new Task() {
            @Override
            public Object call() {
                return null;
            }
        });
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

    @Override
    public Task getTaskFromQueue() throws RemoteException, InterruptedException {
        return new Task() {
            @Override
            public Object call() {
                return null;
            }
        };
    }

    @Override
    public String test() throws RemoteException {
        return "derp";
    }

    public static void main(String[] args) throws RemoteException {
        System.setSecurityManager(new SecurityManager());
        LocateRegistry.createRegistry(Space.PORT).rebind(Space.SERVICE_NAME, new SpaceImpl());

        System.out.println("Space running...");

    }
}
