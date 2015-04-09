package tasks;

import api.Task;


/**
 * Created by Kyrre on 07.04.2015.
 */
public class TaskMandelbrotSet implements Task<Integer[][]> {

    private final double xCorner, yCorner, edgeLength;
    private final int n, iterationLimit;


    /**
     * Constructs a new Task that is able to compute a Mandelbrot set as specified in the HW1 description
     *
     * @param xCorner Lower left X-coordinate in a square in the plane of complex numbers
     * @param yCorner Lower left Y-coordinate in a square in the plane of complex numbers
     * @param edgeLength Length of the edges of a square in the complex plane
     * @param n The number of subregions of the square in the complex plane, such that it is subdivided into n X n squares
     * @param iterationLimit Defines when the representative point of a region is considered to be in the Mandelbrot set.
     */
    public TaskMandelbrotSet(double xCorner, double yCorner, double edgeLength, int n, int iterationLimit) {
        this.xCorner = xCorner;
        this.yCorner = yCorner;
        this.edgeLength = edgeLength;
        this.n = n;
        this.iterationLimit = iterationLimit;
    }


    @Override
    public Integer[][] execute() {

        // Initialize 2D Integer result-array of length n, n.
        Integer[][] result = new Integer[n][n];

        // Iterate over 2D array
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                // Compute the complex number c. We can represent a complex number z = x + iy as the pair of real numbers (x, y)
                // The complex number c belongs to the Mandelbrot set if the sequence stays within  a radius of 2 from the origin.
                double cReal = (j - n/2)*4.0/n;
                double cImaginary = (i - n/2)*4.0/n;

                // Accumulate iterations for current coordinates and save it in the result-array.
                double x = 0, y = 0;
                int iterations = 0;
                while (x*x + y*y <= 4 && iterations < iterationLimit) {
                    double x_new = x*x - y*y + cReal;
                    y = 2*x*y + cImaginary;
                    x = x_new;
                    iterations++;
                }
                result[i][j] = iterations;
            }
        }
        return result;
    }
}
