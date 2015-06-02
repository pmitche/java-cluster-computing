package task;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by hallvard on 5/26/15.
 */
public class Vertex implements Serializable{

    public final int ID;

    public final double X;
    public final double Y;

    private Color color = Color.GRAY;

    private List<Color> domain;
    private List<Vertex> neighbors = new ArrayList<>();

    public Vertex(int id, double x, double y, List<Color> domain, Color c) {
        this.ID = id;
        this.X = x;
        this.Y = y;
        this.color = (c != null) ? c : this.color;
        this.domain = domain;

    }

    public boolean isDomainSingleton() {
        boolean singleton = domain.size()==1;
        if(singleton) color = domain.get(0);
        return singleton;
    }

    public Vertex deepCopy() {
        return new Vertex(ID, X, Y, new ArrayList<>(domain), color);
    }

    public List<Vertex> getNeighbors() {
        return neighbors;
    }

    public Color getColor() {
        return color;
    }

    /**
     *
     * @param color
     * @return true, if domain was not a singleton, but was reduced to one.
     */
    public boolean reduceDomain(Color color) {
        if (isDomainSingleton()){
            return false;
        }
        domain.remove(color);
        return isDomainSingleton();
    }
    public int getDomainSize(){
        return domain.size();
    }

    public List<Color> getDomain() {
        return domain;
    }

    public void assumeColor(Color color) {
        domain.removeIf(new Predicate<Color>() {
            @Override
            public boolean test(Color color2) {
                return !color.equals(color2);
            }
        });
        this.color = color;
    }

    public void addNeighbor(Vertex vertex) {
        neighbors.add(vertex);
    }
}
