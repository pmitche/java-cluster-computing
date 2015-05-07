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
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Kyrre on 13.04.2015.
 */
public class ComputerImpl extends UnicastRemoteObject implements Computer {

    protected final AtomicInteger threadCount;
    protected final LinkedBlockingQueue<Closure> tasks;
    public final int coreCount;

    public ComputerImpl(Space space) throws RemoteException {
        SpaceImpl.setInstance(space);
        tasks = new LinkedBlockingQueue<>();
        coreCount = SpaceImpl.MULTICORE ? Runtime.getRuntime().availableProcessors() : 1;
        threadCount = new AtomicInteger(coreCount);
        for (int i = 0; i < coreCount; i++) {
            new Thread(new CoreHandler(threadCount, tasks, i)).start();
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

    @Override
    public <T> T execute(Task<T> task) throws RemoteException {
        System.out.println("ComputerImpl; execute("+task+")");
        long elaps = System.nanoTime();
        T t= task.call();
        System.out.println((System.nanoTime()-elaps)/1000000);
        return t;
    }

    @Override
    public void execute(Closure closure) throws RemoteException {
        synchronized (threadCount){
            try {
                tasks.put(closure);

                if (tasks.size() > Space.PREFETCH_LIMIT){
                    threadCount.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
