package space;

import api.Result;
import api.Space;
import api.Task;
import computer.ComputerProxy;
import system.Computer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Kyrre on 13.04.2015.
 */
public class SpaceImpl extends UnicastRemoteObject implements Space {

    private LinkedBlockingQueue<Task> taskQueue;
    private LinkedBlockingQueue<Result> resultQueue;
    private HashMap<Integer,Computer> computerSet;

    public SpaceImpl() throws RemoteException {
        this.taskQueue = new LinkedBlockingQueue<Task>();
        this.resultQueue = new LinkedBlockingQueue<Result>();
    }

    @Override
    public void putAll(List<Task> taskList) throws RemoteException {
        taskQueue.addAll(taskList);
    }

    @Override
    public void put(Task task) throws RemoteException, InterruptedException {
        taskQueue.put(task);
    }

    @Override
    public Result take() throws RemoteException, InterruptedException {
        return resultQueue.take();
    }

    @Override
    public void exit() throws RemoteException {
        System.exit(0);
    }

    @Override
    public void register(Computer computer) throws RemoteException {
        ComputerProxy cp = new ComputerProxy(computer, this);
        Thread t = new Thread(cp);
        t.start();
        System.out.println("Computer registered and working...");
    }

    @Override
    public Task getTaskFromQueue() throws RemoteException, InterruptedException {
        return taskQueue.take();
    }

    @Override
    public void putResult(Result r) throws RemoteException {
        resultQueue.add(r);
    }

    public static void main(String[] args) throws RemoteException {
        System.setSecurityManager(new SecurityManager());
        LocateRegistry.createRegistry(Space.PORT).rebind(Space.SERVICE_NAME, new SpaceImpl());
        System.out.println("Space running...");
    }
}
