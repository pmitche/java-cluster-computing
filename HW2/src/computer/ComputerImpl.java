package computer;

import api.Space;
import api.Task;
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

    protected ComputerImpl(Space space) throws RemoteException {
        this.space = space;
    }

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, InterruptedException {
        System.setSecurityManager(new SecurityManager());
        String url = "//" + inputIp() + "/" + Space.SERVICE_NAME;
        Space space = (Space) Naming.lookup(url);
        ComputerImpl computer = new ComputerImpl(space);
        space.register(computer);
        System.out.println("Registered to Space, running...");
    }

    @Override
    public <T> T execute(Task<T> task) throws RemoteException {
        return task.call();
    }

    private static String inputIp(){
        System.out.println("input space ip:");
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }
}
