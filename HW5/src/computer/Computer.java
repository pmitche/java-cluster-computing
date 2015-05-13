package computer;

import system.Closure;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Describes obligatory methods for all implementations of Computer.
 * Method description falls to implementing classes.
 */
public interface Computer extends Remote{
    void execute(Closure closure) throws RemoteException;
}
