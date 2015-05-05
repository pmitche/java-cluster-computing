package client;

import task.FibonacciJob;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by hallvard on 4/28/15.
 */
public class ClientFibonacci extends Client {

    private static final int DEPTH = 16;

    /**
     * Client constructor
     *
     * @param domainName Name of the domain
     * @param singleJVM  boolean value determining if the system should be ran on a single JVM
     * @throws RemoteException
     * @throws NotBoundException
     * @throws MalformedURLException
     */
    public ClientFibonacci(String domainName, boolean singleJVM) throws RemoteException, NotBoundException, MalformedURLException {
        super("Fibonacci", domainName, new FibonacciJob(DEPTH), singleJVM);
    }

    /**
     * Main method
     *
     * @param args
     * @throws RemoteException
     * @throws MalformedURLException
     * @throws NotBoundException
     */
    public static void main( String[] args ) throws RemoteException, MalformedURLException, NotBoundException {
        boolean singleJVM = false;
        if(args.length > 0)
            if(args[0].equals("singleJVM"))
                singleJVM = true;

        System.setSecurityManager(new SecurityManager());
        final ClientFibonacci client= new ClientFibonacci("localhost", singleJVM);

        client.begin();
        long elaps = System.nanoTime();
        final Integer value = (Integer)client.runJob();
        System.out.println("Job completed, solution: "+value);
        System.out.println((System.nanoTime()-elaps)/1000000);
        System.out.println();
        client.end();
    }

}