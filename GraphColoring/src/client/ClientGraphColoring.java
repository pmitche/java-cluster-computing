package client;

import sun.security.provider.certpath.Vertex;
import task.Edge;
import task.StateGraphColoring;
import task.TspJob;
import task.TspUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by hallvard on 5/26/15.
 */
public class ClientGraphColoring extends Client<List<Integer>> implements Serializable {

    private final int SIZE = 13;
    private final double DIM = 0;

    private HashMap<Integer, Vertex> lookup = new HashMap<>();

    public ClientGraphColoring(String ip) throws RemoteException, NotBoundException,MalformedURLException {
        super("Graph Coloring", ip, null); //TODO - add Job
    }

    public static void main( String[] args ) throws RemoteException, MalformedURLException, NotBoundException {
        System.setSecurityManager(new SecurityManager());
        final ClientGraphColoring client = new ClientGraphColoring("localhost"):
        //TODO - Add label.
    }

    public JLabel getLabel( final StateGraphColoring state) {
        final Image image = new BufferedImage(800,800, BufferedImage.TYPE_INT_ARGB);
        final Graphics graphics = image.getGraphics();

        graphics.setColor(Color.DARK_GRAY);

        //Draw Vertecies
        for( task.Vertex v : state.getVertices()) {
            int x = (int)(v.X);
            int y = (int)(v.Y);
            graphics.drawOval(x,y, SIZE, SIZE);
            graphics.setColor(v.getColor());
            graphics.fillOval(x, y, SIZE, SIZE);
        }
        //Draw Edges
        for( Edge e : state.EDGES) {
            int x1 = (int)(lookup.get(e.id1).x	*DIM)+SIZE/2;
            int y1 = (int)(lookup.get(e.id1).y	*DIM)+SIZE/2;
            int x2 = (int)(lookup.get(e.id2).x	*DIM)+SIZE/2;
            int y2 = (int)(lookup.get(e.id2).y	*DIM)+SIZE/2;
            graphics.setColor(Color.BLACK);
            graphics.drawLine(x1,y1,x2,y2);
        }
        graphics.setColor(Color.DARK_GRAY);

        return new JLabel(new ImageIcon(image));
    }

}