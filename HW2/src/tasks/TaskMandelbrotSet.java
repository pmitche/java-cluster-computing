package tasks;

import api.Task;


/**
 * @author Paul Mitchell
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
    public Integer[][] call() {

        // Initialize 2D Integer result-array of length n, n.
        Integer[][] result = new Integer[n][n];
        double delta = edgeLength / n;

        // Iterate over 2D array
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                // Compute the complex number c. We can represent a complex number z = x + iy as the pair of real numbers (x, y)
                // The complex number c belongs to the Mandelbrot set if the sequence stays within  a radius of 2 from the origin.

                // Accumulate iterations for current coordinates and save it in the result-array.

                result[row][col] = getIterationCount(row, col, delta);
            }
        }
        return result;
    }

    // Modifisert kode fra forrige oppgave + Capello sin implementasjon
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
