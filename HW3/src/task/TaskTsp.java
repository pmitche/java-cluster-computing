package task;

import api.Task;
import system.CilkThread;
import system.Closure;
import system.Continuation;
import system.ResultValueWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hallvard on 4/26/15.
 */
public class TaskTsp extends CilkThread implements Task {

    private final long START_TIME = System.nanoTime(); //TODO

    private final double[][] CITIES;

    public TaskTsp(Closure closure, double[][] cities) {
        super(closure);
        this.CITIES = cities;
    }

    @Override
    public Object call() {
        Thread t = new Thread(this);
        t.start();
        return null;
    }

    /**
     * Decomposes the problem to subtasks that are spawned in space
     * @param c The Continuation of this task
     */
    @Override
    public void decompose(Continuation c) {

        int n = (Integer)((ResultValueWrapper)c.argument).getN();
        if(n < 2) {
            sendArgument(c);
            return;
        }
        Integer[] a = (Integer[])((ResultValueWrapper) c.argument).getTaskReturnValue();

        //Create all successor lists
        List<Integer[]> succLists = new ArrayList<>();
        for(int i=1; i<n; i++)
            succLists.add(swap(a.clone(), i, n-1));

        //Spawn next to get ID
        long id = spawnNext(new TaskTsp(null, CITIES),c, new Object[succLists.size()]);

        //Spawn a new Continuation for each successor entry
        for(int i=0; i<succLists.size(); i++) {
            ResultValueWrapper rvw = new ResultValueWrapper(succLists.get(i), n-1);
            spawn(new TaskTsp(null, CITIES), new Continuation(id, i + 1, rvw));
        }
    }

    /**
     * Composes the subsolutions into its own solution when the subtask values are ready
     * @param list  List containing the Continuation objects of the subtasks
     */
    @Override
    public void compose(List list) {
        List<Continuation> temp = (List<Continuation>) list;
        Continuation currCont = temp.remove(0);

        Continuation best = null;
        double shortest = Double.MAX_VALUE;
        for(Continuation cont : temp) {
            double path = totalDistance((Integer[])((ResultValueWrapper)cont.argument).getTaskReturnValue());
            if(best==null || path < shortest) {
                best = cont;
                shortest = path;
            }
        }

        currCont.setReturnVal(best.argument);
        sendArgument(currCont);
    }


    /**
     * Runs the CilkThread task instance and decides weather it should decompose or compose.
     */
    @Override
    public void run() {
        List<Continuation> conts = (List<Continuation>)closure.getArguments();
        if(closure.isAncestor()) compose(conts);
        else decompose(conts.get(0));
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
     */
    public double totalDistance(Integer[] a) {
        double distance = 0;
        for (int i = 0; i < a.length; i++){
            if (i < a.length-1){
                distance += euclideanDistance(CITIES[a[i]], CITIES[a[i+1]]);
            }
        }
        return distance;
    }
}
