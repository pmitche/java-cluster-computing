package task;

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

        final Wrapper w = (Wrapper)c.argument;
        if(w.UNUSED.size()<=0) {
            c.setReturnVal(new ResultValueWrapper(w.PATH, TspUtils.totalDistance(w.PATH)));
            sendArgument(c);
            return;
        }

        //Return if the whole line has been explored
        List<Wrapper> permutations = new ArrayList<>();
        for(Integer unvisited : w.UNUSED) {
            List<Integer> succUnvisited = new ArrayList<>(w.UNUSED);
            List<Integer> succVisited = new ArrayList<>(w.PATH);
            succUnvisited.remove(unvisited);
            succVisited.add(unvisited);
            if(succUnvisited.size() > 1)
                if(succVisited.indexOf(0) > succVisited.indexOf(1)) continue;
                    permutations.add(new Wrapper(succUnvisited, succVisited));
        }

        //Dead end...
        if(permutations.isEmpty()) {
            c.setReturnVal(new ResultValueWrapper(new ArrayList() {{
                addAll(w.PATH);
                addAll(w.UNUSED);
            }}, new Double(Double.MAX_VALUE)));
            sendArgument(c);
        }

        final String id = getId(permutations.size(), c);

        int index = 1;
        for(Wrapper permutation : permutations) {
            spawn(new TaskTsp(null), new Continuation(id, index++, permutation));
        }

        /*
        int index = 1;
        for(Integer unusedCity : w.UNUSED) {
            List<Integer> succUnvisited = new ArrayList<>(w.UNUSED);
            List<Integer> succVisited = new ArrayList<>(w.PATH);
            succUnvisited.remove(unusedCity);
            succVisited.add(unusedCity);
            spawn(new TaskTsp(null), new Continuation(id, index++, new Wrapper(succUnvisited, succVisited)));
        }
        */

    }

    /**
     * Composes the subsolutions into its own solution when the subtask values are ready
     */
    @Override
    public void compose() {
        final Continuation currCon = (Continuation)closure.getArgument(0);

        ResultValueWrapper best = null;
        double shortest = Double.MAX_VALUE;

        int i=1;
        while(true) {
            try {
                final ResultValueWrapper rvw = ((ResultValueWrapper) closure.getArgument(i++));
                if((Double)rvw.getN() < shortest) {
                    best = rvw;
                    shortest = (Double)rvw.getN();
                }
            } catch (IndexOutOfBoundsException e) { break;}
        }

        currCon.setReturnVal(best);
        sendArgument(currCon);
    }

    /**
     * Simple inner value-wrapper class
     */
    public static final class Wrapper implements Serializable {
        public final List<Integer> UNUSED;
        public final List<Integer> PATH;
        public Wrapper(List<Integer> unused, List<Integer> path){
            this.UNUSED = unused;
            this.PATH = path;
        }
    }




    /**
     * TODO Ugliest code ever written...
     * @param size
     * @param c
     * @return
     */
    private String getId(int size, Continuation c) {

        String id = "-1";
        //Spawn next to get ID
        switch(size)
        {
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
        return id;
    }
}
