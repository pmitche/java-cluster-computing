package api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import system.Computer;

public interface Space extends Remote
{
    public static int PORT = 1099;
    public static String SERVICE_NAME = "Space";

    void putAll ( List<Task> taskList ) throws RemoteException;

    Result take() throws RemoteException;

    void exit() throws RemoteException;

    void register( Computer computer ) throws RemoteException;

    Task getTaskFromQueue() throws RemoteException, InterruptedException;

    String test() throws RemoteException;

}