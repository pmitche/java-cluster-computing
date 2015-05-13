package api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

import system.Closure;
import computer.Computer;
import system.Continuation;

public interface Space extends Remote
{

    public static final int PORT = 1099;
    public static final String SERVICE_NAME = "Space";
    public static final boolean MULTICORE = false;
    public static final int PREFETCH_LIMIT = 0;
    public static final boolean ASYNC = false;


    void put(Task task) throws RemoteException, InterruptedException;

    void put(Closure closure)throws RemoteException;

    void putClosureInReady(Closure closure) throws RemoteException;

    Result take() throws RemoteException, InterruptedException;

    void exit() throws RemoteException;

    void register(Computer computer) throws RemoteException;


    void putResult(Result r) throws RemoteException;

    Closure takeReadyClosure() throws  RemoteException;

    void receiveArgument(Continuation k) throws RemoteException;

    void closureDone(String id) throws RemoteException;

    void putAll(Collection<Closure> closures) throws RemoteException;
}