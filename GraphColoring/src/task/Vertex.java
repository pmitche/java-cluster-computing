package task;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hallvard on 5/26/15.
 */
public class Vertex implements Serializable{

    public final int ID;

    public final double X;
    public final double Y;

    private Color color;

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

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
