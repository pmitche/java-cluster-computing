package task;

import api.Task;
import client.ClientTsp;
import system.CilkThread;
import system.Closure;
import system.Continuation;
import system.ResultValueWrapper;

import java.io.Serializable;
import java.util.*;

/**
 * Created by hallvard on 4/26/15.
 */
public class TaskTsp extends CilkThread {

    private final long START_TIME = System.nanoTime(); //TODO

    public TaskTsp(Closure closure) {
        super(closure);
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

        Wrapper w = (Wrapper)c.argument;
/*
        int n = ((Wrapper)(c.argument)).N;
        Integer[] a = ((Wrapper)c.argument).PATH;

        if(n < 2) {
            c.setReturnVal(a);
            sendArgument(c);
            return;
        }
*/
        if(w.UNUSED.size()==0) {
            c.setReturnVal(new ResultValueWrapper<>(w.PATH, totalDistance(w.PATH.toArray(new Integer[0]))));
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

        long id = -1;
        //Spawn next to get ID
/*
        List<Integer[]> temp = new ArrayList() {{
           for(Integer[] il : succLists) {
               add(null);
           }
        }};
*/
        switch(succList.size())
        {
            case 0: {System.out.println("PIKK"); break;}
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
            default: {System.out.println("AIDS");}
        }
//        id = spawnNext(new TaskTsp(null),c, temp.toArray(new Integer[0]));

        //Spawn a new Continuation for each successor entry
        for(int i=0; i<w.UNUSED.size(); i++) {
            spawn(new TaskTsp(null), new Continuation(id, i + 1,succList.get(i)));
        }
    }

    /**
     * Composes the subsolutions into its own solution when the subtask values are ready
     * @param list  List containing the Continuation objects of the subtasks
     */
    @Override
    public void compose(List list) {

        ArrayList<Integer[]> temp = (ArrayList<Integer[]>)list.subList(1, list.size());
        Continuation currCont = (Continuation)list.get(0);

        Integer[] best = null;
        double shortest = Double.MAX_VALUE;
        for(Integer[] path : temp) {
            double way = totalDistance(path);//(Integer[])((ResultValueWrapper)cont.argument).getTaskReturnValue());
            if(best==null || way < shortest) {
                best = path;
                shortest = way;
            }
        }
        currCont.setReturnVal(best);
        sendArgument(currCont);
    }

    private void testMethod() {
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
        /*
        for(Integer[] path : list) {
            double dist = totalDistance(path);
            if(dist < shortest) {
                best = path;
                shortest = dist;
            }
        }
        */

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


    /**
     * Runs the CilkThread task instance and decides weather it should decompose or compose.
     */
    @Override
    public void run() {

        if(closure.isAncestor()) {
            testMethod();
        }
        else decompose((Continuation)closure.getArgument(0));
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
                System.out.println("Cities: "+(ClientTsp.CITIES==null));
                System.out.println("List: "+(a==null));
                distance += euclideanDistance(ClientTsp.CITIES[a[i]], ClientTsp.CITIES[a[i+1]]);
            }
        }
        return distance;
    }
}
