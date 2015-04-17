package job;

import api.Job;
import api.Result;
import api.Space;
import api.Task;
import system.ResultValueWrapper;
import task.TaskMandelbrotSet;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ingeborgoftedal on 14.04.15.
 */
public class MandelbrotSetJob implements Job {

    private int numberOfTasks; //TODO how many tasks will this job be divided into??

    private final double xCorner, yCorner, edgeLength;
    private final int n, iterationLimit;

    /**
     *
     * @param xCorner Lower left X-coordinate in a square in the plane of complex numbers
     * @param yCorner Lower left Y-coordinate in a square in the plane of complex numbers
     * @param edgeLength Length of the edges of a square in the complex plane
     * @param n The number of subregions of the square in the complex plane, such that it is subdivided into n X n squares
     * @param iterationLimit Defines when the representative point of a region is considered to be in the Mandelbrot set.
     */

    public MandelbrotSetJob(double xCorner, double yCorner, double edgeLength, int n, int iterationLimit){
        this.xCorner = xCorner;
        this.yCorner = yCorner;
        this.edgeLength = edgeLength;
        this.n = n;
        this.iterationLimit = iterationLimit;
    }

    @Override
    public void generateTasks(Space space) throws RemoteException {
        List<Task> taskList = new ArrayList<Task>();
        /**
         * adding each task to a list, then putting the list in space
         */
        for(int i=0; i< n; i++) {
            taskList.add(new TaskMandelbrotSet(xCorner, yCorner, edgeLength, n,iterationLimit, i));
        }
        space.putAll(taskList);
    }

    @Override
    public Integer [][] collectResults(Space space) throws RemoteException {
        /**
         * collecting result from space and putting it togheter
         */
        Integer[][] resultArray = new Integer[n][n];
        for (int i = 0; i < n; i++)
            try {
                Result result = space.take();
                ResultValueWrapper wrapper = (ResultValueWrapper) result.getTaskReturnValue();
                resultArray[((Integer) wrapper.getN())] = (Integer[]) wrapper.getTaskReturnValue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }        return resultArray;
    }
}

