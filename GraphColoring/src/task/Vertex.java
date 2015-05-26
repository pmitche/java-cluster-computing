package task;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hallvard on 5/26/15.
 */
public class Vertex implements Serializable{

    public final String ID;

    public final double X;
    public final double Y;

    private Color color = Color.GRAY;

    private List<Color> domain;
    private List<Vertex> neighbors = new ArrayList<>();

    public Vertex(String id, double x, double y, List<Color> domain, Color c) {
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
        List<Color> copyColor = new ArrayList();
        for(Color c : domain)
            copyColor.add(c);
        return new Vertex(ID, X, Y, copyColor, color);
    }

    public List<Vertex> getNeighbors() {
        return neighbors;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    /**
     *
     * @param color
     * @return true, if domain was reduced to a singleton
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
}
