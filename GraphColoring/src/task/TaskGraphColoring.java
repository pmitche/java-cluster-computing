package task;

import system.CilkThread;
import system.Closure;
import system.Continuation;

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
        System.out.println("decompose()");
        Continuation cont = ((Continuation) closure.getArgument(0));
        StateGraphColoring c = (StateGraphColoring)cont.argument;
        ArrayList<StateGraphColoring> childStates = c.deduce();
        for (StateGraphColoring childState: childStates){
            if (childState.isSolution()){
                cont.setReturnVal(childState);
                return;
            }
        }
        //TODO: må brukes når vi bruker capello sin greie.
        //String parentId = getId(childStates.size(), (Continuation)getClosure().getArgument(0));
        String parentId = getId(1, (Continuation)getClosure().getArgument(0));

        int i = 1;
        for (StateGraphColoring state : childStates){
            spawn(new TaskGraphColoring(null), new Continuation(parentId, i++, state));
        }
    }

    @Override
    public void compose() {
        System.out.println("here here");
    }

    protected void generateHeuristic() {

    }

    private String getId(int size, Continuation c) {
        String id = "-1";
        switch(size)
        {
            case 1: { id = spawnNext(new TaskGraphColoring(null), c, null); break; }
            case 2: { id = spawnNext(new TaskGraphColoring(null), c, null, null); break; }
            case 3: { id = spawnNext(new TaskGraphColoring(null), c, null, null, null); break; }
            case 4: { id = spawnNext(new TaskGraphColoring(null), c, null, null, null, null); break; }
            case 5: { id = spawnNext(new TaskGraphColoring(null), c, null, null, null, null, null); break; }
            case 6: { id = spawnNext(new TaskGraphColoring(null), c, null, null, null, null, null, null); break; }
            case 7: { id = spawnNext(new TaskGraphColoring(null), c, null, null, null, null, null, null, null); break; }
            case 8: { id = spawnNext(new TaskGraphColoring(null), c, null, null, null, null, null, null, null, null); break; }
            case 9: { id = spawnNext(new TaskGraphColoring(null), c, null, null, null, null, null, null, null, null, null); break; }
            case 10: { id = spawnNext(new TaskGraphColoring(null), c, null, null, null, null, null, null, null, null, null, null); break; }
            case 11: { id = spawnNext(new TaskGraphColoring(null), c, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 12: { id = spawnNext(new TaskGraphColoring(null), c, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 13: { id = spawnNext(new TaskGraphColoring(null), c, null, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 14: { id = spawnNext(new TaskGraphColoring(null), c, null, null, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 15: { id = spawnNext(new TaskGraphColoring(null), c, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 16: { id = spawnNext(new TaskGraphColoring(null), c, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 17: { id = spawnNext(new TaskGraphColoring(null), c, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null); break; }
        }
        return id;
    }
}
