package api;

import java.rmi.RemoteException;

/**
 * Created by Paul on 14.04.2015.
 */
public interface Job<T>{

    public void generateTasks(Space space) throws RemoteException;
    public T collectResults(Space space) throws RemoteException;

}
