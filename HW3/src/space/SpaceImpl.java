package space;

import api.Result;
import api.Space;
import api.Task;
import computer.ComputerProxy;
import system.Closure;
import system.Computer;
import system.Continuation;
import task.TaskFibonacci;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Kyrre on 13.04.2015.
 */
public class SpaceImpl extends UnicastRemoteObject implements Space {

    private static Space instance;
    private LinkedBlockingQueue<Task> taskQueue;
    private LinkedBlockingQueue<Result> resultQueue;
    private LinkedBlockingQueue<Closure> readyClosureQueue;
    private HashMap<Long,Closure> closures;

    private SpaceImpl() throws RemoteException {
        this.taskQueue = new LinkedBlockingQueue<Task>();
        this.resultQueue = new LinkedBlockingQueue<Result>();
        this.readyClosureQueue = new LinkedBlockingQueue<Closure>();
        this.closures = new HashMap<>();

        Thread t = new Thread(() ->{
            while (true){
                Closure c = null;
                try {
                    c = SpaceImpl.getInstance().takeReadyClosure();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                c.call();
            }
        });
        //t.start();
    }

    public static void main(String[] args) throws RemoteException {
        System.setSecurityManager(new SecurityManager());
        LocateRegistry.createRegistry(Space.PORT).rebind(Space.SERVICE_NAME, SpaceImpl.getInstance());
        String ip;
        ip = args.length > 0 ? args[0] : inputIp();
        System.setProperty("java.rmi.server.hostname", ip);

        //Continuation cont = new Continuation(-1,-1, new Integer(8));
        //new TaskFibonacci(new Closure(0, cont));
        System.out.println("Space running...");
    }


    @Override
    public void putAll(List<Task> taskList) throws RemoteException {
        taskQueue.addAll(taskList);
    }

    /**
     * Takes one task and adds it to the task queue. This is used by the ComputerProxy if its corresponding Computer fails.
     * @param task
     * @throws RemoteException
     * @throws InterruptedException
     */
    @Override
    public void put(Task task) throws RemoteException, InterruptedException {
        taskQueue.put(task);
    }

    @Override
    public synchronized void put(Closure closure) throws RemoteException{
        closures.put(closure.getId(), closure);
  //      System.out.println("SpaceImpl; Putting closure " + closure + " size: " + closures.size());
    }

    @Override
    public Result take() throws RemoteException, InterruptedException {
        return resultQueue.take();
    }

    @Override
    public void exit() throws RemoteException {
        System.exit(0);
    }

    /**
     * Creates a ComputerProxy for the computer, and runs the ComputerProxy in its own thread.
     * @param computer
     * @throws RemoteException
     */
    @Override
    public void register(Computer computer) throws RemoteException {
        ComputerProxy cp = new ComputerProxy(computer);
        Thread t = new Thread(cp);
        t.start();
        System.out.println("SpaceImpl; Computer registered and working...");
    }

    @Override
    public Task takeTaskFromQueue() throws RemoteException, InterruptedException {
        return taskQueue.take();
    }

    @Override
    public void putResult(Result r) throws RemoteException {
        resultQueue.add(r);
    }

    /**
     * Prompts the user for the ip of space. This is done because java rmi is not always able to retrieve the system ip correctly.
     * This is a know problem in java rmi, and this is one of the suggested workarounds.
     * @return
     */
    private static String inputIp(){
        System.out.println("SpaceImpl; input space ip:");
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

    /**
     * Initializes the space locally
     * @param localSpace    Space instance
     */
    public void initLocal(Space localSpace){
        if(System.getSecurityManager()== null)
            System.setSecurityManager(new SecurityManager());

        try {
            LocateRegistry.createRegistry(Space.PORT).rebind(Space.SERVICE_NAME, localSpace);
            System.out.println("SpaceImpl; Local Space up and running...");
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }

    public static Space getInstance(){
        if (instance == null ) {
            synchronized (SpaceImpl.class) {
                if (instance == null) {
                    try {
                        instance = new SpaceImpl();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    public static void setInstance(Space space){
        if (instance == null ) {
            synchronized (SpaceImpl.class) {
                if (instance == null) {
                    instance = space;
                }
            }
        }
    }

    @Override
    public void receiveArgument(Continuation k) throws RemoteException{
        //TODO: for testing
        if (k.closureId == -1){
            System.out.println("SpaceImpl; Back to root");
            System.out.println("\"Result\": "+k.getReturnVal());

        }else {
            closures.get(k.closureId).setArgument(k);
        }
    }

    @Override
    public void putClosureInReady(Closure closure) throws RemoteException {
        try {
            readyClosureQueue.put(closure);
            System.out.println("SpaceImpl; Putting closure in ready " + closure + " size: " + readyClosureQueue.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Closure takeReadyClosure() throws RemoteException {
//        System.out.println("SpaceImpl;  takeReadyClosure(), size before get: " + readyClosureQueue.size());
        Closure c = null;
        try {
            c = readyClosureQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
  //      System.out.println("SpaceImpl; takeReadyClosure() ######, size after" +
    //            " get: " + readyClosureQueue.size());
        return c;
    }
}
