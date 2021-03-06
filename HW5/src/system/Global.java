package system;

import java.io.Serializable;

/**
 * Created by Kyrre on 14.05.2015.
 */
public class Global implements Serializable{

    private final Comparable value;

    public Global(Comparable value) {
        this.value = value;
    }

    public Comparable getValue() {
        return value;
    }

    public Global findBest(Global that){
        if (this.getValue().compareTo(that.getValue()) > 0){
            return that;
        }
        return this;
    }

}