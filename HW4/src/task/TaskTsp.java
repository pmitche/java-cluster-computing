package task;

import client.ClientTsp;
import system.CilkThread;
import system.Closure;
import system.Continuation;
import system.ResultValueWrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hallvard on 4/26/15.
 */
public class TaskTsp extends CilkThread {

    private final long START_TIME = System.nanoTime();

    public TaskTsp(Closure closure) {
        super(closure);
    }

    /**
     * Decomposes the problem to subtasks that are spawned in space
     * @param c The Continuation of this task
     */
    @Override
    public void decompose(Continuation c) {

        Wrapper w = (Wrapper)c.argument;
        if(w.UNUSED.size()==0) {
            c.setReturnVal(new ResultValueWrapper<>(w.PATH, TspUtils.totalDistance(w.PATH.toArray(new Integer[0]))));
            sendArgument(c);
            return;
        }

        List<Wrapper> succList = new ArrayList<>();
        //Create all successor lists
        for(Integer next : w.UNUSED) {
            ArrayList<Integer> succUnvisited = new ArrayList<>(w.UNUSED);
            ArrayList<Integer> succVisited = new ArrayList<>(w.PATH);
            succUnvisited.remove(next);
            succVisited.add(next);
            succList.add(new Wrapper(succUnvisited, succVisited));
        }



        String id = "-1";
        //Spawn next to get ID
        switch(succList.size())
        {
            case 0: {break;}
            case 1: { id = spawnNext(new TaskTsp(null),c, null); break; }
            case 2: { id = spawnNext(new TaskTsp(null),c, null, null); break; }
            case 3: { id = spawnNext(new TaskTsp(null),c, null, null, null); break; }
            case 4: { id = spawnNext(new TaskTsp(null),c, null, null, null, null); break; }
            case 5: { id = spawnNext(new TaskTsp(null),c, null, null, null, null, null); break; }
            case 6: { id = spawnNext(new TaskTsp(null),c, null, null, null, null, null, null); break; }
            case 7: { id = spawnNext(new TaskTsp(null),c, null, null, null, null, null, null, null); break; }
            case 8: { id = spawnNext(new TaskTsp(null),c, null, null, null, null, null, null, null, null); break; }
            case 9: { id = spawnNext(new TaskTsp(null),c, null, null, null, null, null, null, null, null, null); break; }
            case 10: { id = spawnNext(new TaskTsp(null),c, null, null, null, null, null, null, null, null, null, null); break; }
            case 11: { id = spawnNext(new TaskTsp(null),c, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 12: { id = spawnNext(new TaskTsp(null),c, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 13: { id = spawnNext(new TaskTsp(null),c, null, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 14: { id = spawnNext(new TaskTsp(null),c, null, null, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 15: { id = spawnNext(new TaskTsp(null),c, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 16: { id = spawnNext(new TaskTsp(null),c, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 17: { id = spawnNext(new TaskTsp(null),c, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            default: {}
        }
//        id = spawnNext(new TaskTsp(null),c, temp.toArray(new Integer[0]));

        //Spawn a new Continuation for each successor entry
        for(int i=0; i<w.UNUSED.size(); i++) {
            spawn(new TaskTsp(null), new Continuation(id, i + 1,succList.get(i)));
        }
    }

    /**
     * Composes the subsolutions into its own solution when the subtask values are ready
     */
    @Override
    public void compose() {
        Continuation currCon = (Continuation)closure.getArgument(0);
        List<ResultValueWrapper> list = new ArrayList<>();

        int i=1;
        while(true) {
            try {
                list.add((ResultValueWrapper) closure.getArgument(i++));
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }

        ResultValueWrapper best = null;
        double shortest = Double.MAX_VALUE;
        for(ResultValueWrapper rvw : list) {
            if((Double)rvw.getN() < shortest) {
                best = rvw;
                shortest = (Double)rvw.getN();
            }
        }

        currCon.setReturnVal(best);
        sendArgument(currCon);
    }


    public static final class Wrapper implements Serializable {
        public final List<Integer> UNUSED;
        public final List<Integer> PATH;
        public Wrapper(List<Integer> used, List<Integer> path){
            this.UNUSED = used;
            this.PATH = path;
        }
    }
}
