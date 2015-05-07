package api;

import java.rmi.RemoteException;

/**
 * All jobs should implement this interface
 * Documenting usages falls to the implementation.
 */
public interface Job<T>{

    public void generateTasks(Space space) throws RemoteException;
    public T collectResults(Space space) throws RemoteException;

}
