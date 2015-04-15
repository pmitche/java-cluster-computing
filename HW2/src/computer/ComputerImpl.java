package computer;

import api.Space;
import api.Task;
import space.SpaceImpl;
import system.Computer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Kyrre on 13.04.2015.
 */
public class ComputerImpl extends UnicastRemoteObject implements Computer {
    private final Space space;

    protected ComputerImpl(Space space) throws RemoteException {
        this.space = space;
    }

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, InterruptedException {
        //TODO: get domain name from command line
        System.setSecurityManager(new SecurityManager());
        String serverDomainName = "localhost";
        String url = "//" + serverDomainName + "/" + Space.SERVICE_NAME;
        Space space = (Space) Naming.lookup(url);
        ComputerImpl computer = new ComputerImpl(space);
        space.register(computer);
        space.getTaskFromQueue();
        System.out.println(space.test());
        System.out.println("Registered to Space");
        //computer.run();
    }

    @Override
    public <T> T execute(Task<T> task) throws RemoteException {
        return null;
    }

    private void run() throws InterruptedException, RemoteException {
        while (true){
            //space.getTaskFromQueue();
            System.out.println("task retrieved");
        }
    }
}
