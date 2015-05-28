package task;

import java.io.Serializable;

/**
 * Created by hallvard on 5/26/15.
 */
public class Edge implements Serializable{

    public final int id1;
    public final int id2;

    public Edge(int id1, int id2) {
        this.id1 = id1;
        this.id2 = id2;
    }
}