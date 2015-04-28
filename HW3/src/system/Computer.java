package system;

import api.Task;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Kyrre on 13.04.2015.
 */
public interface Computer extends Remote{
    public <T> T execute(Task<T> task) throws RemoteException;

    public <T> T execute(Closure closure) throws RemoteException;
}
