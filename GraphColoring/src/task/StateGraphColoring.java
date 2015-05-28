package task;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * Created by hallvard on 5/26/15.
 */
public class StateGraphColoring implements Serializable {

    private HashMap<Integer, Vertex> vertices;
    public final List<Edge> EDGES;
    public Vertex lastAssumed;

    public StateGraphColoring(HashMap<Integer, Vertex> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.EDGES = edges;
    }

    public HashMap<Integer, Vertex> getVertices() {
        return vertices;
    }

    public StateGraphColoring deepCopy() {
        HashMap<Integer, Vertex> verteciesCopy = new HashMap<>();
        for( Vertex v : vertices.values())
            verteciesCopy.put(v.ID, v.deepCopy());
        return new StateGraphColoring(verteciesCopy, EDGES);
    }

    public Vertex getLastAssumed() {
        return lastAssumed;
    }

    /**
     * Deduces colors from the previous assumtion, and returns a list of new assumptions.
     * @return
     */
    public ArrayList<StateGraphColoring> deduce() {
        if (lastAssumed == null){
            Integer key = (Integer) vertices.keySet().toArray()[0];
            makeAssumption(key, vertices.get(key).getDomain().get(0));
        }
        HashSet<Vertex> candidates = reduce(lastAssumed);

        int smallest = Integer.MAX_VALUE;
        Vertex current = null;
        for (Vertex candidat: candidates){
            if (candidat.getDomainSize() < smallest){
                current = candidat;
                smallest = candidat.getDomainSize();
            }
        }
        return generateChildState(current);
    }

    private ArrayList<StateGraphColoring> generateChildState(Vertex current) {
        ArrayList<StateGraphColoring> childStates = new ArrayList<>();
        for (Color color: current.getDomain()){
            StateGraphColoring child = deepCopy();
            child.makeAssumption(current.ID, color);
            childStates.add(child);
        }
        return childStates;
    }

    private void makeAssumption(Integer id, Color color) {
        vertices.get(id).assumeColor(color);
        lastAssumed = vertices.get(id);
    }

    private HashSet<Vertex> reduce(Vertex focal){
        ArrayList<Vertex> singletons = new ArrayList<>();
        HashSet<Vertex> notSingletons = new HashSet<>();
        for (Vertex neighbour: focal.getNeighbors()){
            if (neighbour.reduceDomain(focal.getColor())){
                singletons.add(neighbour);
            }else {
                notSingletons.add(neighbour);
            }
        }
        for (Vertex v: singletons){
            notSingletons.addAll(reduce(v));
        }
        return notSingletons;
    }
}
