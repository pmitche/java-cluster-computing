package task;

import system.CilkThread;
import system.Closure;

import java.util.ArrayList;

/**
 * Created by hallvard on 5/26/15.
 */
public class TaskGraphColoring extends CilkThread {


    public TaskGraphColoring(Closure closure) {
        super(closure);

    }

    @Override
    public void decompose() {
        StateGraphColoring c = (StateGraphColoring)closure.getArgument(0);
        ArrayList<StateGraphColoring> childStates = c.deduce();

        //TODO: SpawnNext
        //spawnNext(null,);

        for (StateGraphColoring state : childStates){
            spawn(new TaskGraphColoring(null), state);
        }
    }

    @Override
    public void compose() {

    }

    protected void generateHeuristic() {

    }
}
