package tasks;

import api.Task;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Kyrre on 08.04.2015.
 * minPath holds what is currently considered the best solution,
 * e.i. the shortest path found thus far.
 * minDistance holds the distance of minPath
 */
public class TaskEuclideanTsp implements Task<List<Integer>> {

    double[][] cities;
    double minDistance = Double.MAX_VALUE;
    Integer[] minPath;
    public TaskEuclideanTsp(double[][] cities) {
        this.cities = cities;
        minPath = new Integer[cities.length];
        for (int i = 0; i < minPath.length; i++){
            minPath[i] = i;
        }
    }

    @Override
    public List<Integer> execute() {
        bruteForce(minPath, minPath.length);
        return Arrays.asList(minPath);
    }

    /**
     * Too tiered to be fancy.
     * This is just a recursive method that calls totalDistance for every permutation of a, where a is a array referring to the ordering of cities.
     */
    private void bruteForce(Integer[] a, int n) {
        if (n == 1) {
            totalDistance(a);
            return;
        }
        for (int i = 0; i < n; i++) {
            swap(a, i, n-1);
            bruteForce(a, n - 1);
            swap(a, i, n-1);
        }
    }
    private static void swap(Integer[] a, int i, int i1) {
        Integer tmp = a[i];
        a[i] = a[i1];
        a[i1] = tmp;
    }

    /**
     * Calculates the total distance of a given permutation. If the distance is shorter than the current best solution, minDistance and minPath are updated.
     * @param a
     */
    private void totalDistance(Integer[] a) {
        double distance = 0;
        for (int i = 0; i < a.length; i++){
            if (i < a.length-1){
                distance += euclideanDistance(cities[a[i]], cities[a[i+1]]);
            }
        }
        if (distance < minDistance){
            minDistance = distance;
            minPath = a.clone();
        }
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
}
