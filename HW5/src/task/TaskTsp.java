package task;

import system.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hallvard on 4/26/15.
 */
public class TaskTsp extends CilkThread {

    private static final int DECOMPOSE_LIMIT = 8;
    private final long START_TIME = System.nanoTime();

    public TaskTsp(Closure closure) {
        super(closure);
    }

    /**
     * Decomposes the problem to subtasks that are spawned in space
     */
    @Override
    public void decompose() {

        if (isAtomic()){
            calculate();
            return;
        }

        Continuation c =(Continuation)closure.getArgument(0);
        final Wrapper w = (Wrapper)c.argument;

        //TODO: Can probably remove this now
        if(w.UNUSED.size()<=1) {
            w.PATH.addAll(w.UNUSED);
            c.setReturnVal(new ResultValueWrapper(w.PATH, TspUtils.totalDistance(w.PATH)));
            sendArgument(c);
            return;
        }

        //Return if the whole line has been explored
        List<Wrapper> permutations = new ArrayList();
        for(Integer unvisited : w.UNUSED) {
            List<Integer> succUnvisited = new ArrayList(w.UNUSED);
            List<Integer> succVisited = new ArrayList(w.PATH);
            succUnvisited.remove(unvisited);
            succVisited.add(unvisited);
            if(succVisited.size() > 1) {
                if(succVisited.indexOf(1) > succVisited.indexOf(2)) continue; // Removes the reverse of another solution
            }
            permutations.add(new Wrapper(succUnvisited, succVisited));
        }

        //Check if this thread is a dead end.
        if(permutations.isEmpty() || isRedundant()) {
            c.setReturnVal(new ResultValueWrapper(new ArrayList() {{
                addAll(w.PATH);
                addAll(w.UNUSED);
            }}, new Double(Double.MAX_VALUE)));
            sendArgument(c);
            return;
        }

        final String id = getId(permutations.size(), c);

        int index = 1;
        for(Wrapper permutation : permutations) {
            spawn(new TaskTsp(null), new Continuation(id, index++, permutation));
        }
    }

    public void calculate()  {
        Continuation c = (Continuation)closure.getArgument(0);
        Wrapper w = (Wrapper)c.argument;

        bruteForce(new ArrayList() {{
            addAll(w.PATH);
            addAll(w.UNUSED);
        }}, w.PATH.size()-1);
        closure.updateGlobal(new Global(shortest));

        c.setReturnVal(new ResultValueWrapper(best, shortest));
        sendArgument(c);
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
     * Decides weather the task is atomic and cannot be devided anymore
     * @return weather or not to decompose or calculate.
     */
    private boolean isAtomic() {
        Continuation c = getContinuation();
        Wrapper w = (Wrapper)c.argument;
        return w.UNUSED.size() <= DECOMPOSE_LIMIT;
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

    private boolean isRedundant() {
        Continuation c = getContinuation();
        Wrapper w = (Wrapper)c.argument;

        Global currCost = new Global(TspUtils.totalDistance(w.PATH));
        Global g = closure.getGlobal();

        if(g == g.findBest(currCost)) return true;

        Global g2 = addTwoClosestCities(w.PATH, w.UNUSED);

//        if(g2 != g2.findBest(currCost)) return true;
        return false;
    }

    private Global addTwoClosestCities(List<Integer> path, List<Integer> unused) {
        return null;
    }


    private Continuation getContinuation() {
        return (Continuation)closure.getArgument(0);
    }


    //------------------------------

    private void bruteForce(List<Integer> a, int n) {
        if (n == a.size()-1)
            register(a);

        for (int i = a.size()-1; i > n; i--) {
            Collections.swap(a, i, n + 1);
            bruteForce(a, n + 1);
            Collections.swap(a, i, n + 1);
        }
    }

    private double shortest = Double.MAX_VALUE;
    private List<Integer> best = null;

    private void register(List<Integer> path) {
        double dist = TspUtils.totalDistance(path);
        if(dist < shortest){
            shortest = dist;
            best = new ArrayList<>(path);
        }
    }


    /**
     * @param size
     * @param c
     * @return
     */
    private String getId(int size, Continuation c) {

        String id = "-1";
        //Spawn next to get ID

        switch(size)
        {
            case 1: { id = spawnNext(new TaskTsp(null), c, null); break; }
            case 2: { id = spawnNext(new TaskTsp(null), c, null, null); break; }
            case 3: { id = spawnNext(new TaskTsp(null), c, null, null, null); break; }
            case 4: { id = spawnNext(new TaskTsp(null), c, null, null, null, null); break; }
            case 5: { id = spawnNext(new TaskTsp(null), c, null, null, null, null, null); break; }
            case 6: { id = spawnNext(new TaskTsp(null), c, null, null, null, null, null, null); break; }
            case 7: { id = spawnNext(new TaskTsp(null), c, null, null, null, null, null, null, null); break; }
            case 8: { id = spawnNext(new TaskTsp(null), c, null, null, null, null, null, null, null, null); break; }
            case 9: { id = spawnNext(new TaskTsp(null), c, null, null, null, null, null, null, null, null, null); break; }
            case 10: { id = spawnNext(new TaskTsp(null), c, null, null, null, null, null, null, null, null, null, null); break; }
            case 11: { id = spawnNext(new TaskTsp(null), c, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 12: { id = spawnNext(new TaskTsp(null), c, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 13: { id = spawnNext(new TaskTsp(null), c, null, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 14: { id = spawnNext(new TaskTsp(null), c, null, null, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 15: { id = spawnNext(new TaskTsp(null), c, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 16: { id = spawnNext(new TaskTsp(null), c, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 17: { id = spawnNext(new TaskTsp(null), c, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            default: {}
        }
        return id;
    }
}
