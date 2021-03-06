package api;

import computer.Computer;
import system.Closure;
import system.Continuation;
import system.Global;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface Space extends Remote
{

    public static final int PORT = 1099;
    public static final String SERVICE_NAME = "Space";
    public static final boolean MULTICORE = false;
    public static final int PREFETCH_LIMIT = 5;
    public static final boolean ASYNC = true;


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

    void updateGlobal(Global global) throws RemoteException;
}