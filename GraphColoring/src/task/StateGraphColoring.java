package task;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hallvard on 5/26/15.
 */
public class StateGraphColoring {

    private HashSet<Vertex> vertices;
    public final List<Edge> EDGES;

    public StateGraphColoring(HashSet<Vertex> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.EDGES = edges;
    }

    public HashSet<Vertex> getVertices() {
        return vertices;
    }

    public StateGraphColoring deepCopy() {
        HashSet<Vertex> verteciesCopy = new HashSet();
        for( Vertex v : vertices)
            verteciesCopy.add(v.deepCopy());
        return new StateGraphColoring(verteciesCopy, EDGES);
    }

}
