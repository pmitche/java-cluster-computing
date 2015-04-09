package api;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Kyrre on 07.04.2015.
 */
public interface Computer extends Remote {

    public static String SERVICE_NAME = "ComputerService";
    public static String PORT = "8000";
    public <T> T execute(Task<T> task) throws RemoteException;

}
