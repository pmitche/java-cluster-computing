package client;

import api.Task;

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

    // Midlertidig
    public ClientMandelbrotSet(String title, Task<Integer[][]> task) throws RemoteException, NotBoundException, MalformedURLException {
        super(title,"localhost", new MandelbrotSetJob(LOWER_LEFT_X,LOWER_LEFT_Y,EDGE_LENGTH,N_PIXELS,ITERATION_LIMIT));
    }

    /*public ClientMandelbrotSet(String ip) throws RemoteException, NotBoundException, MalformedURLException
    {
        super( "Mandelbrot Set Visualizer", ip,
                new TaskMandelbrotSet( LOWER_LEFT_X, LOWER_LEFT_Y, EDGE_LENGTH, N_PIXELS,
                        ITERATION_LIMIT) );
    }*/

    /** TODO endre
     * Run the MandelbrotSet visualizer client.
     * @param args unused
     * @throws java.rmi.RemoteException
     *
    */public static void main( String[] args ) throws Exception {
        System.setSecurityManager(new SecurityManager());
        //final ClientMandelbrotSet client = new ClientMandelbrotSet(args[0], null);
        final ClientMandelbrotSet client = new ClientMandelbrotSet("Derp", null);
        client.begin();
        Integer[][] value = client.runJob();
        client.add(client.getLabel(value));
        client.end();
    }


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

    private Color getColor( int iterationCount )
    {
        return iterationCount == ITERATION_LIMIT ? Color.BLACK : Color.WHITE;
    }
}
