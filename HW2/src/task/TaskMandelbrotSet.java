package task;

import api.Result;
import api.Task;
import system.ResultValueWrapper;


/**
 * @author Paul Mitchell
 */
public class TaskMandelbrotSet implements Task<Result<ResultValueWrapper>> {

    private final double xCorner, yCorner, edgeLength;
    private final int n, iterationLimit;
    private final int row;


    /**
     * Constructs a new Task that is able to compute a Mandelbrot set as specified in the HW1 description
     *
     * @param xCorner Lower left X-coordinate in a square in the plane of complex numbers
     * @param yCorner Lower left Y-coordinate in a square in the plane of complex numbers
     * @param edgeLength Length of the edges of a square in the complex plane
     * @param n The number of subregions of the square in the complex plane, such that it is subdivided into n X n squares
     * @param iterationLimit Defines when the representative point of a region is considered to be in the Mandelbrot set.
     */
    public TaskMandelbrotSet(double xCorner, double yCorner, double edgeLength, int n, int iterationLimit, int row) {
        this.row = row;
        this.xCorner = xCorner;
        this.yCorner = yCorner;
        this.edgeLength = edgeLength;
        this.n = n;
        this.iterationLimit = iterationLimit;
    }


    @Override
    public Result<ResultValueWrapper> call() {

        // Initialize 2D Integer result-array of length n, n.
        Integer[] result = new Integer[n];
        double delta = edgeLength / n;

        // Iterate over 2D array
        for (int col = 0; col < n; col++) {
            result[col] = getIterationCount(row, col, delta);
        }
        // creating a wrapper for composing the results
        ResultValueWrapper wrap = new ResultValueWrapper(result,row);
        return new Result<ResultValueWrapper>(wrap,-1);
    }

    /**
     *
     * @param row row in image array
     * @param col column in image array
     * @param delta
     * @return iterations
     */
    private int getIterationCount(int row, int col, double delta) {
        double cReal = xCorner + row * delta;
        double cImaginary = yCorner + col * delta;
        double x = cReal, y = cImaginary;
        int iterations = 0;

        while (x*x + y*y <= 4 && iterations < iterationLimit) {
            double x_new = x*x - y*y + cReal;
            y = 2*x*y + cImaginary;
            x = x_new;
            iterations++;
        }

        return iterations;
    }

}
