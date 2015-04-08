package client;

import api.Hello;
import java.rmi.Naming;

class HelloClient
{
    public static void main( String[] args ) throws Exception
    {
        System.setSecurityManager( new SecurityManager() );
        String serverDomainName = "localhost";
        String url = "//" + serverDomainName + "/" + Hello.SERVICE_NAME;

        // Can throw exceptions - see API for java.rmi.Naming.lookup
        Hello hello = (Hello) Naming.lookup( url );

        System.out.println( hello.hello() ); // can throw RemoteException
    }
}