package computer;

import api.Computer;
import api.Task;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Kyrre on 07.04.2015.
 * This serves as the server for the HW1
 */
public class ComputerImpl extends UnicastRemoteObject implements Computer {

    public ComputerImpl() throws RemoteException {}

    /**
     * Simply executes the task and returns the result.
     * @param task
     * @param <T>
     * @return
     * @throws RemoteException
     */
    @Override
    public <T> T execute(Task<T> task) throws RemoteException {
        return task.execute();
    }

    public static void main(String[] args) throws Exception
    {
        // construct & set a security manager (unnecessary in this case)
        System.setSecurityManager( new SecurityManager() );

        // instantiate a server object
        ComputerImpl server = new ComputerImpl(); // can throw RemoteException

        // construct an rmiregistry within this JVM using the default port
        Registry registry = LocateRegistry.createRegistry(Integer.parseInt(Computer.PORT));

        // bind server in rmiregistry. Can throw exceptions. See api.
        registry.rebind( Computer.SERVICE_NAME, server );

        System.out.println("ComputerImpl.main: Ready.");
    }
}
