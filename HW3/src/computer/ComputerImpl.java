package computer;

import api.Space;
import api.Task;
import system.Closure;
import system.Computer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * Created by Kyrre on 13.04.2015.
 */
public class ComputerImpl extends UnicastRemoteObject implements Computer {
    private final Space space;
    private int id = -1;

    public ComputerImpl(Space space) throws RemoteException {
        this.space = space;
    }

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, InterruptedException {
        System.setSecurityManager(new SecurityManager());
        String ip;
        ip = args.length > 0 ? args[0] : inputIp();
        String url = "//" + ip + "/" + Space.SERVICE_NAME;
        Space space = (Space) Naming.lookup(url);
        ComputerImpl computer = new ComputerImpl(space);
        space.register(computer);
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
        closure.call();
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
