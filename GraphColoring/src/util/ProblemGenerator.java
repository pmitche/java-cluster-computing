package util;

import task.Edge;
import task.StateGraphColoring;
import task.Vertex;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import java.io.*;
import java.util.*;

/**
 * Created by hallvard on 5/26/15.
 */
public class ProblemGenerator implements Serializable {

    private final int DOMAIN_SIZE;

    public ProblemGenerator(int domainSize) {
        this.DOMAIN_SIZE = domainSize;
    }

    public StateGraphColoring getProblem() {


        //Select File
        File file = selectFile("./GraphColoring/src/");

        //Read File
        ArrayList<String> input;
        try {
            input = readFile(file);
        } catch ( Exception e ) { e.printStackTrace(); return null; }

        //Generate Problem
        return generateProblem(input);
    }

    private boolean isInteger(String s) {
        try { Integer.parseInt(s); }
        catch( NumberFormatException e) { return false; }
        return true;
    }

    protected File selectFile(String path) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Textfiles", "txt");
        chooser.setCurrentDirectory(new File(path));
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        } else {
            return selectFile(path);
        }
    }

    protected ArrayList<String> readFile(File file) throws Exception {
        Reader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        ArrayList<String> input = new ArrayList<String>();
        String s;
        while((s = bufferedReader.readLine()) != null) {
            input.add(s);
        }
        bufferedReader.close();
        reader.close();
        return input;
    }

    private StateGraphColoring generateProblem(ArrayList<String> input) {
        Object[] first = getValues(input.get(0), false);
        int nv = (Integer)first[0];			//Number of vertices
        int ne = (Integer)first[1];			//Number of edges

        //Normalize Vertices
        double negativeX = 99999999;
        double negativeY = 99999999;
        for(int i=1; i<nv+1; i++) {
            Object[] values = getValues(input.get(i), true);
            if ((Double)values[1] < negativeX){
                negativeX = (Double)values[1];
            }
            if ((Double)values[2] < negativeY){
                negativeY = (Double)values[2];
            }
        }

        //Vertices
        HashMap<Integer, Vertex> vertices = new HashMap<>();
        for(int i=1; i<nv+1; i++) {
            Object[] values = getValues(input.get(i), true);
            vertices.put((Integer)values[0], new Vertex((Integer)values[0], (Double)values[1]+(negativeX*-1), (Double)values[2]+(negativeY*-1), getDomain(DOMAIN_SIZE), Color.GRAY));
        }

        //Edges
        ArrayList<Edge> edges = new ArrayList();
        for(int j=nv+1; j<input.size(); j++) {
            Object[] values = getValues(input.get(j), false);
            edges.add(new Edge((Integer) values[0], (Integer) values[1]));
        }

        System.out.println(vertices);
        return new StateGraphColoring(vertices, edges);
    }

    protected Object[] getValues(String s, boolean vertex) {
        String[] array = s.split(" ");
        Object[] values = new Object[array.length];
        for(int i=0; i<array.length; i++) {
            if(vertex && i!=0)
                values[i] = Double.parseDouble(array[i]);
            else
                values[i] = Integer.parseInt(array[i]);
        }
        return values;
    }
    public List<Color> getDomain(int domainSize) {
        ArrayList<Color> domain = new ArrayList();
        domain.add(Color.RED);
        domain.add(Color.BLUE);
        domain.add(Color.GREEN);
        domain.add(Color.YELLOW);
        domain.add(Color.MAGENTA);
        domain.add(Color.ORANGE);
        domain.add(Color.CYAN);
        domain.add(Color.WHITE);
        domain.add(Color.BLACK);
        return domain.subList(0, domainSize);
    }
}
