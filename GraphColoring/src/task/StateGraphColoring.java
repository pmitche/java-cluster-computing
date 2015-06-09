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
        reduce(vertices.values());
        //Contradictory
        for (Vertex v: vertices.values()){
            if (v.getDomainSize() == 0){
                return null;
            }
        }

        int smallest = Integer.MAX_VALUE;
        Vertex current = null;
        for (Vertex candidate: vertices.values()){
            if (candidate.getDomainSize() < smallest && !candidate.isDomainSingleton()){
                current = candidate;
                smallest = candidate.getDomainSize();
            }
        }
        if (current == null){
            return new ArrayList<StateGraphColoring>(Collections.singletonList(this));
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

    private void reduce(Collection<Vertex> col){
        ArrayList<Vertex> rev = new ArrayList<>();
        for (Vertex v: col){
            for (Integer id : v.getNeighbors()){
                Vertex neighbor = vertices.get(id);
                if (neighbor.isDomainSingleton()){
                    if (v.reduceDomain(neighbor.getColor())){
                        rev.add(v);
                    }
                }
            }
        }
        if (!rev.isEmpty()){
            reduce(rev);
        }
    }

    public boolean isSolution() {
        for (Vertex v : vertices.values()){
            if (!v.isDomainSingleton()){
                return false;
            }
        }
        return true;
    }

    public double getHeuristic() {
        double score = 0;
        for (Vertex v: vertices.values()){
            score += v.getDomainSize()-1;
        }
        return score;
    }
}
