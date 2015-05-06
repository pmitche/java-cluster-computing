package task;

import client.ClientTsp;

import java.util.Random;

/**
 * Created by hallvard on 5/5/15.
 */
public class TspUtils {

    /**
     * Swapping two values
     * @param a     All values
     * @param i     to be swapped with i1
     * @param i1    to be swapped with i
     */
    public static Integer[] swap(Integer[] a, int i, int i1) {
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
    private static double euclideanDistance(double[] d0, double[] d1){
        double xDistance = Math.abs(d0[0]-d1[0]);
        double yDistance = Math.abs(d0[1] - d1[1]);
        return Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );
    }

    /**
     * Calculates the total distance of a given permutation. If the distance is shorter than the current best solution, minDistance and minPath are updated.
     * @param a
     */
    public static double totalDistance(Integer[] a) {
        double distance = 0;
        for (int i = 0; i < a.length; i++){
            if (i < a.length-1){
                distance += euclideanDistance(ClientTsp.CITIES[a[i]], ClientTsp.CITIES[a[i+1]]);
            }
        }
        distance += euclideanDistance(ClientTsp.CITIES[a[0]], ClientTsp.CITIES[a[a.length-1]]);
        return distance;
    }

    /**
     * Fisher–Yates shuffle.
     */
    public static void shuffle(Integer[] ar) {
        for (int i = ar.length - 1; i > 0; i--) {
            int index = new Random().nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
