package client;

import task.Edge;
import task.JobGraphColoring;
import task.StateGraphColoring;
import task.Vertex;
import util.ProblemGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hallvard on 5/26/15.
 */
public class ClientGraphColoring extends Client<List<Integer>> implements Serializable {

    private final int SIZE = 13;    //Size of the vertecies
    private final double DIM = 22;  //Dimension of the graph

    public ClientGraphColoring(String ip) throws RemoteException, NotBoundException,MalformedURLException {
        super("Graph Coloring", ip, new JobGraphColoring());
    }

    public static void main( String[] args ) throws RemoteException, MalformedURLException, NotBoundException {
        System.setSecurityManager(new SecurityManager());
        final ClientGraphColoring client = new ClientGraphColoring("localhost");
        client.add(client.getLabel((StateGraphColoring)client.runJob()));
    }

    public JLabel getLabel( final StateGraphColoring state) {
        final Image image = new BufferedImage(800,800, BufferedImage.TYPE_INT_ARGB);
        final Graphics graphics = image.getGraphics();

        graphics.setColor(Color.DARK_GRAY);

        HashMap<Integer, Vertex> lookup = new HashMap();

        //Draw Vertecies
        for( Vertex v : state.getVertices().values()) {
            lookup.put(v.ID, v);
            int x = (int)(v.X*DIM);
            int y = (int)(v.Y*DIM);
            graphics.drawOval(x,y, SIZE, SIZE);
            graphics.setColor(v.getColor());
            graphics.fillOval(x, y, SIZE, SIZE);
        }
        //Draw Edges
        for( Edge e : state.EDGES) {
            int x1 = (int)(lookup.get(e.id1).X  *DIM)+SIZE/2;
            int y1 = (int)(lookup.get(e.id1).Y	*DIM)+SIZE/2;
            int x2 = (int)(lookup.get(e.id2).X	*DIM)+SIZE/2;
            int y2 = (int)(lookup.get(e.id2).Y	*DIM)+SIZE/2;
            graphics.setColor(Color.BLACK);
            graphics.drawLine(x1,y1,x2,y2);
        }
        graphics.setColor(Color.DARK_GRAY);

        return new JLabel(new ImageIcon(image));
    }

}