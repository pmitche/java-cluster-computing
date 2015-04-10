package api;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Kyrre on 07.04.2015.
 */
public interface Computer extends Remote {

    public static final String SERVICE_NAME = "ComputerService";
    public static final String PORT = "8000";
    public <T> T execute(Task<T> task) throws RemoteException;

}
