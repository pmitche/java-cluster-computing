package computer;

import api.Result;
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
        System.out.println("Registered to Space, running...");
        computer.run();
    }

    @Override
    public <T> T execute(Task<T> task) throws RemoteException {
        return task.call();
    }

    private void run() throws InterruptedException, RemoteException {
        while (true){
            Task task = space.getTaskFromQueue();
            if (task != null){
                System.out.println("task retrieved");
                space.putResult((Result) execute(task));
                System.out.println("task completed, and returned");
            }
            //Thread.sleep(2000);
        }
    }
}
