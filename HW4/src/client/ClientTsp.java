package client;

import task.TspJob;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peter Cappello
 * @author hallvard
 */
public class ClientTsp extends Client<List<Integer>> implements Serializable
{
    private static final int NUM_PIXALS = 600;
    public static final double[][] CITIES =
            {
                    { 1, 1 },
                    { 8, 1 },
                    { 8, 8 },
                    { 1, 8 },
                    { 2, 2 },
                    { 7, 2 },
                    { 7, 7 },
                    { 2, 7 }
//                    { 3, 3 },
  //                  { 6, 3 },
    //                { 6, 6 },
      //              { 3, 6 }
            };



    public static final double ABS_TEMP = 0.01;
    public static final double COOLING_RATE = 0.55;
    public static final double START_TEMP = 500;


    /**
     * Constructor
     * <p>Extends the Client super class</p>
     * @param ip    IP address
     * @param singleJVM if the client should run local space and computers for a single JVM
     * @throws RemoteException
     * @throws NotBoundException
     * @throws MalformedURLException
     */
    public ClientTsp(String ip, boolean singleJVM) throws RemoteException, NotBoundException, MalformedURLException
    {
        super("Euclidean TSP", ip, new TspJob(CITIES), singleJVM);
    }

    public static void main( String[] args ) throws RemoteException, MalformedURLException, NotBoundException
    {
        boolean singleJVM = false;
        if(args.length > 0)
            if(args[0].equals("singleJVM"))
                singleJVM = true;

        System.setSecurityManager(new SecurityManager());
        final ClientTsp client = new ClientTsp("localhost", singleJVM);

        client.begin();
        long elaps = System.nanoTime();
        final List<Integer> value = client.runJob();
        System.out.println((System.nanoTime()-elaps)/1000000);
        client.add( client.getLabel(value.toArray(new Integer[0])) );
        client.end();

/*        final Object value = client.runJob();

        System.out.println(value.getClass());

        //Shouldn't be neccesary but it is...
        ArrayList<Integer> res = null;
        if(value instanceof ArrayList) {
            res = (ArrayList<Integer>)value;
            System.out.println("HEI");
        }

        System.out.println((System.nanoTime()-elaps)/1000000);
        client.add( client.getLabel( value.toArray(new Integer[0])));//res.toArray( new Integer[0] ) ) );
        client.end();

  */
    }

    /**
     * Creates label to be passed tp superclass Client
     * @param tour  path to be illustrated
     * @return  generated label
     */
    public JLabel getLabel( final Integer[] tour )
    {
        Logger.getLogger( ClientTsp.class.getCanonicalName() ).log(Level.INFO, tourToString(tour));

        // display the graph graphically, as it were
        // get minX, maxX, minY, maxY, assuming they 0.0 <= mins
        double minX = CITIES[0][0], maxX = CITIES[0][0];
        double minY = CITIES[0][1], maxY = CITIES[0][1];
        for ( double[] cities : CITIES )
        {
            if ( cities[0] < minX )
                minX = cities[0];
            if ( cities[0] > maxX )
                maxX = cities[0];
            if ( cities[1] < minY )
                minY = cities[1];
            if ( cities[1] > maxY )
                maxY = cities[1];
        }

        // scale points to fit in unit square
        final double side = Math.max( maxX - minX, maxY - minY );
        double[][] scaledCities = new double[CITIES.length][2];
        for ( int i = 0; i < CITIES.length; i++ )
        {
            scaledCities[i][0] = ( CITIES[i][0] - minX ) / side;
            scaledCities[i][1] = ( CITIES[i][1] - minY ) / side;
        }

        final Image image = new BufferedImage( NUM_PIXALS, NUM_PIXALS, BufferedImage.TYPE_INT_ARGB );
        final Graphics graphics = image.getGraphics();

        final int margin = 10;
        final int field = NUM_PIXALS - 2*margin;
        // draw edges
        graphics.setColor( Color.BLUE );
        int x1, y1, x2, y2;
        int city1 = tour[0], city2;
        x1 = margin + (int) ( scaledCities[city1][0]*field );
        y1 = margin + (int) ( scaledCities[city1][1]*field );
        for ( int i = 1; i < CITIES.length; i++ )
        {
            city2 = tour[i];
            x2 = margin + (int) ( scaledCities[city2][0]*field );
            y2 = margin + (int) ( scaledCities[city2][1]*field );
            graphics.drawLine( x1, y1, x2, y2 );
            x1 = x2;
            y1 = y2;
        }
        city2 = tour[0];
        x2 = margin + (int) ( scaledCities[city2][0]*field );
        y2 = margin + (int) ( scaledCities[city2][1]*field );
        graphics.drawLine( x1, y1, x2, y2 );

        // draw vertices
        final int VERTEX_DIAMETER = 6;
        graphics.setColor( Color.RED );
        for ( int i = 0; i < CITIES.length; i++ )
        {
            int x = margin + (int) ( scaledCities[i][0]*field );
            int y = margin + (int) ( scaledCities[i][1]*field );
            graphics.fillOval( x - VERTEX_DIAMETER/2,
                    y - VERTEX_DIAMETER/2,
                    VERTEX_DIAMETER, VERTEX_DIAMETER);
        }
        final ImageIcon imageIcon = new ImageIcon( image );
        return new JLabel( imageIcon );
    }

    /**
     * Creates a more human readable string showing the path
     * @param cities
     * @return
     */
    private String tourToString( Integer[] cities )
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "Tour: " );
        for ( Integer city : cities )
        {
            stringBuilder.append( city ).append( ' ' );
        }
        return stringBuilder.toString();
    }
}
