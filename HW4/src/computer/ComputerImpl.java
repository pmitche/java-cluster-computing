package computer;

import api.Space;
import space.SpaceImpl;
import system.Closure;

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


    /**
     * Sets space to ensure the singleton points to the correct remote reference. Also checks if MULTICORE mode is enabled.
     * If enabled one CoreHandler is initiated for each core. If not, only one CoreHandler is initiated.
     * @param space
     * @throws RemoteException
     */
    public ComputerImpl(Space space) throws RemoteException {
        SpaceImpl.setInstance(space);
        tasks = new LinkedBlockingQueue<>();
        coreCount = SpaceImpl.MULTICORE ? Runtime.getRuntime().availableProcessors() : 1;
        threadCount = new AtomicInteger(coreCount);
        for (int i = 0; i < coreCount; i++) {
            new Thread(new CoreHandler(threadCount, tasks, i)).start();
        }
    }

    /**
     * Preforms necessary initialization to start an instance of ComputerImpl
     * @param args
     * @throws RemoteException
     * @throws NotBoundException
     * @throws MalformedURLException
     * @throws InterruptedException
     */
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
     * Does not execute the Closure as the name implies. Rather it adds the closure to a queue. This is to allow for asynchronous communication.
     * @param closure
     * @throws RemoteException
     */
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
