package server;

import api.Hello;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

class HelloServer extends UnicastRemoteObject implements Hello
{
    HelloServer() throws RemoteException {} // "export" HelloImpl

    @Override
    public String hello() { return "Hello world!"; }

    public static void main(String[] args) throws Exception
    {
        // construct & set a security manager (unnecessary in this case)
        System.setSecurityManager( new SecurityManager() );

        // instantiate a server object
        Hello hello = new HelloServer(); // can throw RemoteException

        // construct an rmiregistry within this JVM using the default port
        Registry registry = LocateRegistry.createRegistry( 1099 );

        // bind server in rmiregistry. Can throw exceptions. See api.
        registry.rebind( Hello.SERVICE_NAME, hello );

        System.out.println("HelloImpl.main: Ready.");
    }
}