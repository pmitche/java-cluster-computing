package client;

import api.Job;
import api.Space;
import api.Task;
import computer.ComputerImpl;
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

/*
 *
 * @author Peter Cappello
 * @param <T> return type the Task that this Client executes.
 */
public class Client<T> extends JFrame
{
    final protected Job job;
    private Space space;
    protected T taskReturnValue;
    private long clientStartTime;

    public Client( final String title, final String domainName, final Job job , boolean singleJVM)
            throws RemoteException, NotBoundException, MalformedURLException
    {
        this.job = job;
        setTitle( title );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String url = "rmi://" + domainName + ":" + Space.PORT + "/" + Space.SERVICE_NAME;
        if(singleJVM)
            runSingleJVM();
        else
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

    /**
     * sets up the loacl space
     */
    protected void runSingleJVM() {
        SpaceImpl localSpace;
        ComputerImpl localComputer;

        try {
            localSpace = new SpaceImpl();
            localSpace.initLocal(localSpace);
            localComputer = new ComputerImpl(localSpace);
            localComputer.initLocal(localComputer);

            localSpace.register(localComputer);
            space = localSpace;
        } catch (RemoteException re) {
            System.out.println("FAILLL!!!");
            re.printStackTrace();
        }
    }

    public T runJob() throws RemoteException
    {
        long elaps = System.nanoTime();
        job.generateTasks(space);
        T t = (T) job.collectResults(space);
        System.out.println((System.nanoTime()-elaps)/1000000);
        return t;
    }
}
