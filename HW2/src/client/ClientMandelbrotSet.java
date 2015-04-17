package client;

import api.Task;
import job.MandelbrotSetJob;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author Peter Cappello
 * @author Paul Mitchell
 */
public class ClientMandelbrotSet extends Client<Integer[][]>
{
    private static final double LOWER_LEFT_X = -0.7510975859375;
    private static final double LOWER_LEFT_Y = 0.1315680625;
    private static final double EDGE_LENGTH = 0.01611;
    private static final int N_PIXELS = 1024;
    private static final int ITERATION_LIMIT = 512;

    /**
     * Constructor
     * @param title the title
     * @param task  task definition
     * @param singleJVM if the client should run local space and computers for a single JVM
     * @throws RemoteException
     * @throws NotBoundException
     * @throws MalformedURLException
     */
    public ClientMandelbrotSet(String title, Task<Integer[][]> task, boolean singleJVM) throws RemoteException, NotBoundException, MalformedURLException {
        super(title,"localhost", new MandelbrotSetJob(LOWER_LEFT_X,LOWER_LEFT_Y,EDGE_LENGTH,N_PIXELS,ITERATION_LIMIT), singleJVM);
    }

    /*public ClientMandelbrotSet(String ip) throws RemoteException, NotBoundException, MalformedURLException
    {
        super( "Mandelbrot Set Visualizer", ip,
                new TaskMandelbrotSet( LOWER_LEFT_X, LOWER_LEFT_Y, EDGE_LENGTH, N_PIXELS,
                        ITERATION_LIMIT) );
    }*/

    /**
     * Run the MandelbrotSet visualizer client.
     * @param args unused
     * @throws java.rmi.RemoteException
     *
    */public static void main( String[] args ) throws Exception {

        boolean singleJVM = false;
        if(args.length > 0)
            if(args[0].equals("singleJVM"))
                singleJVM = true;

        System.setSecurityManager(new SecurityManager());
        //final ClientMandelbrotSet client = new ClientMandelbrotSet(args[0], null);
        final ClientMandelbrotSet client = new ClientMandelbrotSet("Derp", null, singleJVM);
        client.begin();
        Integer[][] value = client.runJob();
        client.add(client.getLabel(value));
        client.end();
    }


    /**
     * Generates the illustration of the solution
     * @param counts
     * @return  generated JLabel
     */
    public JLabel getLabel( Integer[][] counts )
    {
        final Image image = new BufferedImage( N_PIXELS, N_PIXELS, BufferedImage.TYPE_INT_ARGB );
        final Graphics graphics = image.getGraphics();
        for ( int i = 0; i < counts.length; i++ )
            for ( int j = 0; j < counts.length; j++ )
            {
                graphics.setColor( getColor( counts[i][j] ) );
                graphics.fillRect( i, N_PIXELS - j, 1, 1 );
            }
        final ImageIcon imageIcon = new ImageIcon( image );
        return new JLabel( imageIcon );
    }

    /**
     * Defines the color for the specific pixels
     * @param iterationCount    iteration count
     * @return  color of the pixel
     */
    private Color getColor( int iterationCount )
    {
        return iterationCount == ITERATION_LIMIT ? Color.BLACK : Color.WHITE;
    }
}
