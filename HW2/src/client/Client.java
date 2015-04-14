package client;

import api.Task;
import computer.ComputerImpl;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peter Cappello
 * @param <T> return type the Task that this Client executes.
 */
public class Client<T> extends JFrame
{
    final protected Task<T> task;
    protected T taskReturnValue;
    private long clientStartTime;

    public Client(final String title, final Task<T> task)
            throws RemoteException, NotBoundException, MalformedURLException
    {
        this.task = task;
        setTitle( title );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }

    public void begin() { clientStartTime = System.nanoTime(); }

    public void end()
    {
        Logger.getLogger( Client.class.getCanonicalName() )
                .log(Level.INFO, "Client time: {0} ms.", ( System.nanoTime() - clientStartTime) / 1000000 );
    }

    public void add( final JLabel jLabel )
    {
        final Container container = getContentPane();
        container.setLayout( new BorderLayout() );
        container.add( new JScrollPane( jLabel ), BorderLayout.CENTER );
        pack();
        setVisible( true );
    }
}
