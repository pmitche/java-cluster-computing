package task;

import api.Space;
import computer.SpaceProxy;
import system.CilkThread;
import system.Closure;
import system.Continuation;
import system.Global;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by hallvard on 5/26/15.
 */
public class TaskGraphColoring extends CilkThread {


    public TaskGraphColoring(Closure closure) {
        super(closure);

    }

    @Override
    protected double genHeuristic() {
        Continuation cont = ((Continuation) closure.getArgument(0));
        StateGraphColoring c = (StateGraphColoring)cont.argument;
        return c.getHeuristic();
    }

    @Override
    public void decompose() {
        System.out.println("decompose()");
        Continuation cont = ((Continuation) closure.getArgument(0));
        StateGraphColoring c = (StateGraphColoring)cont.argument;
        ArrayList<StateGraphColoring> childStates = c.deduce();
        //Contradictory
        if (childStates == null){
            return;
        }
        for (StateGraphColoring childState: childStates){
            if (childState.isSolution()){
                cont.setReturnVal(childState);
                try {
                    SpaceProxy.getInstance().receiveArgument(cont);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        //TODO: m� brukes n�r vi bruker capello sin greie.
        //String parentId = getId(childStates.size(), (Continuation)getClosure().getArgument(0));
        String parentId = getId(1, (Continuation)getClosure().getArgument(0));

        int i = 1;
        for (StateGraphColoring state : childStates){
            if (state.getHeuristic() < closure.getHeuristic()){
                try {
                    SpaceProxy.getInstance().updateGlobal(new Global(state.getHeuristic()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            spawn(new TaskGraphColoring(null), new Continuation(parentId, i, state));
        }
    }

    @Override
    public void compose() {
        try {
            Continuation cont = (Continuation) closure.getArgument(0);
            cont.setReturnVal(closure.getArgument(1));
            SpaceProxy.getInstance().receiveArgument(cont);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
