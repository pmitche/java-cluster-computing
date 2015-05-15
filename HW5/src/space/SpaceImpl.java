package space;

import api.Result;
import api.Space;
import api.Task;
import computer.ComputerImpl;
import system.Closure;
import computer.Computer;
import system.Continuation;
import system.Global;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Kyrre on 13.04.2015.
 */
public class SpaceImpl extends UnicastRemoteObject implements Space {

    public static final boolean LOCAL_COMPUTER = true;

    private static Space instance;
    private LinkedBlockingDeque<Task> taskQueue;
    private LinkedBlockingQueue<Result> resultQueue;
    private LinkedBlockingQueue<Closure> readyClosureQueue;
    private HashMap<String,Closure> closures;
    private HashSet<String> closuresDone;
    private Global global;

    private SpaceImpl() throws RemoteException {
        this.taskQueue = new LinkedBlockingDeque<Task>();
        this.resultQueue = new LinkedBlockingQueue<Result>();
        this.readyClosureQueue = new LinkedBlockingQueue<Closure>();
        this.closures = new HashMap<>();
        this.closuresDone = new HashSet<>();
    }

    public static void main(String[] args) throws RemoteException {
        System.setSecurityManager(new SecurityManager());
        LocateRegistry.createRegistry(Space.PORT).rebind(Space.SERVICE_NAME, SpaceImpl.getInstance());
        String ip;
        ip = args.length > 0 ? args[0] : inputIp();
        System.setProperty("java.rmi.server.hostname", ip);
        System.out.println("Space running...");

        //Start a thread with a local computer.
        if(LOCAL_COMPUTER) {
            Runnable r = () -> {
                try {
                    getInstance().register(new ComputerImpl(getInstance()));
                } catch (RemoteException re) {
                }
            };

            Thread localComputer = new Thread(r);
            localComputer.setPriority(Thread.MIN_PRIORITY);
            localComputer.start();
        }
    }

    /**
     * Takes one task and adds it to the task queue. This is used by the ComputerProxy if its corresponding Computer fails.
     * @param task
     * @throws RemoteException
     * @throws InterruptedException
     */
    @Override
    public void put(Task task) throws RemoteException, InterruptedException {
        global = task.getClosure().getGlobal();
        taskQueue.put(task);
    }

    @Override
    public synchronized void put(Closure closure) throws RemoteException{
        closures.put(closure.getId(), closure);
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

    /**Should only be used, to active a JVM global reference to the remote space. As such it should be
     * run before getInstance, except if you intend to create a local Space.
     * The cleanest workaround we have found to an "issue" with RMI and the singleton pattern.
     * This is not an ideal solution, but it is the cleanest we can think of.
     * @param space
     */
    public static void setInstance(Space space){
        if (instance == null ) {
            synchronized (SpaceImpl.class) {
                if (instance == null) {
                    instance = space;
                }
            }
        }
    }

    /**
     * Sets an argument for the Closure corresponding to k.closureId.
     * @param k: Continuation coresponding to a Closure, and it's missing argument.
     * @throws RemoteException
     */
    @Override
    public synchronized void receiveArgument(Continuation k) throws RemoteException{
        if (k.closureId.equals("-1")){
            putResult(new Result(k.getReturnVal(),-1));
        }else {
            Closure c = closures.get(k.closureId);
            c.setArgument(k);
        }
    }

    @Override
    public synchronized void closureDone(String id) throws RemoteException {
        closuresDone.add(id);
    }

    @Override
    public synchronized void putAll(Collection<Closure> closures) throws RemoteException {
        closures.forEach(closure -> {
            if (!closuresDone.contains(closure.getId())) {
                try {
                    readyClosureQueue.put(closure);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public synchronized void updateGlobal(Global global) throws RemoteException {
       this.global = global.findBest(this.global);
    }

    @Override
    public synchronized void putClosureInReady(Closure closure) throws RemoteException {
        try {
            readyClosureQueue.put(closure);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Closure takeReadyClosure() throws RemoteException {
        Closure c = null;
        try {
            c = readyClosureQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        c.setGlobal(global);
        return c;
    }
}
