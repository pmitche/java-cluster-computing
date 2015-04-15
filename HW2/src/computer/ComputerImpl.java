package computer;

import api.Space;
import api.Task;
import system.Computer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Kyrre on 13.04.2015.
 */
public class ComputerImpl implements Computer {
    protected ComputerImpl() throws RemoteException {
    }

    @Override
    public <T> T execute(Task<T> task) throws RemoteException {
        return null;
    }

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        //TODO: get domain name from command line
        System.setSecurityManager(new SecurityManager());
        String serverDomainName = "localhost";
        String url = "//" + serverDomainName + "/" + Space.SERVICE_NAME;
        ComputerImpl computer = new ComputerImpl();
        Space space = (Space) Naming.lookup(url);
        //space.register(computer);
    }
}
