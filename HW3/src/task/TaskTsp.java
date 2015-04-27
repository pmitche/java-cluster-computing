package task;

import api.Result;
import api.Task;
import system.CilkThread;
import system.Closure;
import system.Continuation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hallvard on 4/26/15.
 */
public class TaskTsp extends CilkThread implements Task {

    private final long START_TIME = System.currentTimeMillis();

    public TaskTsp(Closure closure) {
        super(closure);
    }

    @Override
    public Object call() {
        Thread t = new Thread(this);
        t.start();
        return null;
    }

    @Override
    public void decompose(Continuation c) {

        int n = ((Wrapper)c.argument).N;
        if(n < 2) {
            sendArgument(c);
            return;
        }
        Integer[] a = ((Wrapper)c.argument).PATH;

        //Create all successor lists
        List<Integer[]> succLists = new ArrayList<>();
        for(int i=1; i<n; i++)
            succLists.add(swap(a.clone(), i, n-1));

        //Spawn next to get ID
        long id = spawnNext(c, new Object[succLists.size()]);

        //Spawn a new Continuation for each successor entry
        for(int i=0; i<succLists.size(); i++)
            spawn(new Continuation(id, i+1, new Wrapper(n-1, succLists.get(i))));
    }

    /**
     * Private inner class to wrap the two needed values as an single argument
     */
    private final static class Wrapper {
        private final int N;
        private final Integer[] PATH;
        private Wrapper(int n, Integer[] path) {
            this.N = n;
            this.PATH = path;
        }
    }

    @Override
    public Result compose(List list) {
        return null;
    }

    @Override
    public void run() {

    }

    /**
     * Swapping two values
     * @param a     All values
     * @param i     to be swapped with i1
     * @param i1    to be swapped with i
     */
    private static Integer[] swap(Integer[] a, int i, int i1) {
        assert i>2 && i1> 2;

        Integer tmp = a[i];
        a[i] = a[i1];
        a[i1] = tmp;
        return  a;
    }

    /**
     *
     * @param d0 is an double[] of length 2, where d0[0] is the x coordinate, and d0[1] is the y coordinates.
     * @param d1 is an double[] of length 2, where d1[0] is the x coordinate, and d2[1] is the y coordinates.
     * @return the euclidean distance between do and d1
     */
    private double euclideanDistance(double[] d0, double[] d1){
        double xDistance = Math.abs(d0[0]-d1[0]);
        double yDistance = Math.abs(d0[1] - d1[1]);
        return Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );
    }

    /**
     * Calculates the total distance of a given permutation. If the distance is shorter than the current best solution, minDistance and minPath are updated.
     * @param a
     *//*
    private void totalDistance(Integer[] a) {
        double distance = 0;
        for (int i = 0; i < a.length; i++){
            if (i < a.length-1){
                distance += euclideanDistance(CITIES[a[i]], CITIES[a[i+1]]);
            }
        }
        if (distance < minDistance){
            minDistance = distance;
            minPath = a.clone();
        }
    }*/
}
