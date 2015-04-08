package api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote
{
    public static String SERVICE_NAME = "HelloService";

    String hello() throws RemoteException;
}