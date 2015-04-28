package task;

import api.Result;
import api.Task;
import system.ResultValueWrapper;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Hallvard on 14.04.2015.
 */
public class TaskEuclideanTsp implements Serializable, Task{

    private double[][] taskCities;
    private int lockedCity;

    private double minDistance = Double.MAX_VALUE;
    private Integer[] minPath;

    public TaskEuclideanTsp(double[][] taskCities, int lockedCity){
        this.taskCities = taskCities;
        this.lockedCity = lockedCity;
        this.minPath  = new Integer[taskCities.length];
    }

    @Override
    public Object call() {
        bruteForce();
        ResultValueWrapper wrap = new ResultValueWrapper(Arrays.asList(minPath), minDistance);
        return new Result<ResultValueWrapper>(wrap, -1);
    }

    private void bruteForce() {
        //setup the startorder. city 0 and 1 should not be moved after this.
        for(int i=0; i<minPath.length; i++)
            minPath[i] = i;
        swap(minPath, 1, lockedCity);

        tspRecusion(minPath, minPath.length);

    }

    private void tspRecusion(Integer[] a, int n) {
        if(n <= 2) {
            totalDistance(a);
            return;
        }
        for (int i=2; i<n; i++){
            swap(a, i, n-1); //swap to create permutation
            tspRecusion(a, n-1);
            swap(a, i, n-1); //swap back
        }
    }

    /**
     * Swapping two values
     * @param a     All values
     * @param i     to be swapped with i1
     * @param i1    to be swapped with i
     */
    private static void swap(Integer[] a, int i, int i1) {
        assert i>2 && i1> 2;

        Integer tmp = a[i];
        a[i] = a[i1];
        a[i1] = tmp;
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
    private void totalDistance(Integer[] a) {
        double distance = 0;
        for (int i = 0; i < a.length; i++){
            if (i < a.length-1){
                distance += euclideanDistance(taskCities[a[i]], taskCities[a[i+1]]);
            }
        }
        if (distance < minDistance){
            minDistance = distance;
            minPath = a.clone();
        }
    }
}
