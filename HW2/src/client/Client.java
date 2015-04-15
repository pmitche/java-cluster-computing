package client;

import api.Space;
import api.Task;
import space.SpaceImpl;

import java.awt.BorderLayout;
import java.awt.Container;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 *
 * @author Peter Cappello
 * @param <T> return type the Task that this Client executes.
 */
public class Client<T> extends JFrame
{
    final protected Task<T> task;
    final private Space space;
    protected T taskReturnValue;
    private long clientStartTime;

    public Client( final String title, final String domainName, final Task<T> task )
            throws RemoteException, NotBoundException, MalformedURLException
    {
        this.task = task;
        setTitle( title );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String url = "rmi://" + domainName + ":" + Space.PORT + "/" + Space.SERVICE_NAME;
        space = ( domainName == null ) ? new SpaceImpl() : (Space) Naming.lookup( url );
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

    public T runTask() throws RemoteException
    {
        ArrayList<Task> list = new ArrayList<Task>();
        list.add(new DummyTask());
        list.add(new DummyTask());
        list.add(new DummyTask());
        list.add(new DummyTask());
        list.add(new DummyTask());
        space.putAll(list);
        return null;
    }
}
