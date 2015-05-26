package task;

import system.CilkThread;
import system.Continuation;

/**
 * Created by hallvard on 5/26/15.
 */
public class TaskGraphColoring extends CilkThread {


    public TaskGraphColoring() {
        super(null);

    }

    @Override
    public void decompose() {
        StateGraphColoring c = (StateGraphColoring)closure.getArgument(0);
        c.deduce();
    }

    @Override
    public void compose() {

    }

    protected void generateHeuristic() {

    }
}
