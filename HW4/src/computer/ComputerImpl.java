package computer;

import api.Space;
import api.Task;
import space.SpaceImpl;
import system.Closure;
import system.Computer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Kyrre on 13.04.2015.
 */
public class ComputerImpl extends UnicastRemoteObject implements Computer {

    protected final AtomicInteger threadCount;
    protected final LinkedBlockingQueue<Closure> tasks;
    //TODO: move to better place
    public final boolean MULTICORE = false;
    public final int coreCount;

    public ComputerImpl(Space space) throws RemoteException {
        SpaceImpl.setInstance(space);
        //TODO: Change to system number of cores
        tasks = new LinkedBlockingQueue<>();
        coreCount = MULTICORE ? Runtime.getRuntime().availableProcessors() : 1;
        threadCount = new AtomicInteger(coreCount);
        for (int i = 0; i < coreCount; i++) {
            new Thread(new CoreProxy(threadCount, tasks, i)).start();
        }
    }

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, InterruptedException {
        System.setSecurityManager(new SecurityManager());
        String ip;
        ip = args.length > 0 ? args[0] : inputIp();
        String url = "//" + ip + "/" + Space.SERVICE_NAME;
        Space space = (Space) Naming.lookup(url);
        ComputerImpl computer = new ComputerImpl(space);
        SpaceImpl.getInstance().register(computer);
        System.out.println("Registered to Space, running...");
    }

    /**
     * Sets the SecurityManager.
     * @param localComputer Computer instance
     */
    public void initLocal(Computer localComputer) {
        if(System.getSecurityManager()==null)
            System.setSecurityManager(new SecurityManager());
    }

    @Override
    public <T> T execute(Task<T> task) throws RemoteException {
        System.out.println("ComputerImpl; execute("+task+")");
        long elaps = System.nanoTime();
        T t= task.call();
        System.out.println((System.nanoTime()-elaps)/1000000);
        return t;
    }

    @Override
    public <T> T execute(Closure closure) throws RemoteException {
        synchronized (threadCount){
            try {
                if (threadCount.get() == 0){
                    threadCount.wait();
                }
                tasks.put(closure);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Takes the ip of the Space that the Computer connects to.
     * @return
     */
    private static String inputIp(){
        System.out.println("input space ip:");
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }
}
