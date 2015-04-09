package tasks;

import api.Task;


/**
 * Created by Kyrre on 07.04.2015.
 */
public class TaskMandelbrotSet implements Task<Integer[][]> {

    private final double xCorner;
    private final double yCorner;
    private final double edgeLength;
    private final int n;
    private final int iterationLimit;

    public TaskMandelbrotSet(double xCorner, double yCorner, double edgeLength, int n, int iterationLimit) {
        this.xCorner = xCorner;
        this.yCorner = yCorner;
        this.edgeLength = edgeLength;
        this.n = n;
        this.iterationLimit = iterationLimit;
    }


    @Override
    public Integer[][] execute() {

        Integer[][] result = new Integer[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double c_real = (i - n/2)*4.0/n;
                double c_imaginary = (i - n/2)*4.0/n;
                double x = 0, y = 0;
                int iterations = 0;
                while (x*x+y*y <= 4 && iterations <= iterationLimit) {
                    double x_new = x*x-y*y+c_real;
                    y = 2*x*y+c_imaginary;
                    x = x_new;
                    iterations++;
                }
                result[i][j] = iterations;
            }
        }
        return result;
    }
}
