package system;

import api.Task;

import java.rmi.RemoteException;

/**
 * Created by Kyrre on 13.04.2015.
 */
public interface Computer {
    public <T> T execute(Task<T> task) throws RemoteException;
}
