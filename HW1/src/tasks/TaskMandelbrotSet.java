package tasks;

import api.Task;

/**
 * Created by Kyrre on 07.04.2015.
 */
public class TaskMandelbrotSet implements Task {

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
        int width = 1920, height = 1080, max = 1000;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                double c_re = (col - width/2)*4.0/width;
                double c_im = (row - height/2)*4.0/width;
                double x = 0, y = 0;
                int iterations = 0;
                while (x*x+y*y < 4 && iterations < max) {
                    double x_new = x*x-y*y+c_re;
                    y = 2*x*y+c_im;
                    x = x_new;
                    iterations++;
                }
                //if (iterations < max) image.setRGB(col, row, white);
                //else image.setRGB(col, row, black);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double c_real = 
            }
        }
        return null;
    }
}
