package system;

import java.io.Serializable;

/**
 * Created by Kyrre on 14.05.2015.
 */
public class Global implements Serializable{

    private final Comparable value;
    private boolean done = false;

    public Global(Comparable value) {
        this.value = value;
    }

    public Comparable getValue() {
        return value;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

    public Global findBest(Global that){
        if (done){
            return this;
        }
        if (this.getValue().compareTo(that.getValue()) > 0){
            return that;
        }
        return this;
    }

}