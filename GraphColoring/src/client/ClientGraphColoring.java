package client;

import api.Result;
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

    private static ClientGraphColoring client;

    public ClientGraphColoring(String ip) throws RemoteException, NotBoundException,MalformedURLException {
        super("Graph Coloring", ip, new JobGraphColoring());
    }

    /**
     * Main method
     * <p>instansiates its parent class and starts the job</p>
     */
    public static void main( String[] args ) throws RemoteException, MalformedURLException, NotBoundException {
        System.setSecurityManager(new SecurityManager());
        client = new ClientGraphColoring("localhost");
        StateGraphColoring state;
        Result result = (Result)client.runJob();
        state = (StateGraphColoring)result.getTaskReturnValue();
        client.add(client.getLabel(state));
    }

    public static void addLabel(StateGraphColoring state) {
        client.add(client.getLabel(state));
    }

    /**
     * Creates the visual representation of the result as an ImageIcon that are theb added to the JLabel returned
     * @param state Grap coloring state that is tp be displayed
     * @return  JLabel containing the visualization of the problem.
     */
    public JLabel getLabel( final StateGraphColoring state) {
        int WIDTH = 800;
        int HEIGHT = 800;
        final Image image = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_ARGB);
        final Graphics graphics = image.getGraphics();

        graphics.setColor(Color.DARK_GRAY);

        HashMap<Integer, Vertex> lookup = new HashMap();
        double normalizeX = Integer.MIN_VALUE;
        double normalizeY = Integer.MIN_VALUE;

        for (Vertex v : state.getVertices().values()){
            normalizeX = Double.max(normalizeX, v.X);
            normalizeY = Double.max(normalizeY, v.Y);
        }
        normalizeX = (WIDTH/normalizeX)*0.9;
        normalizeY = (HEIGHT/normalizeY)*0.9;

        //Draw Vertecies
        for( Vertex v : state.getVertices().values()) {
            lookup.put(v.ID, v);
            int x = (int)(v.X*normalizeX);
            int y = (int)(v.Y*normalizeY);
            graphics.drawOval(x,y, SIZE, SIZE);
            graphics.setColor(v.getColor());
            graphics.fillOval(x, y, SIZE, SIZE);
        }
        //Draw Edges
        for( Edge e : state.EDGES) {
            int x1 = (int)(lookup.get(e.id1).X  *normalizeX)+SIZE/2;
            int y1 = (int)(lookup.get(e.id1).Y	*normalizeY)+SIZE/2;
            int x2 = (int)(lookup.get(e.id2).X	*normalizeX)+SIZE/2;
            int y2 = (int)(lookup.get(e.id2).Y	*normalizeY)+SIZE/2;
            graphics.setColor(Color.BLACK);
            graphics.drawLine(x1,y1,x2,y2);
        }
        graphics.setColor(Color.DARK_GRAY);

        return new JLabel(new ImageIcon(image));
    }

}