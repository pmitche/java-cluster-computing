package client;

import api.Job;
import api.Result;
import api.Space;
import api.Task;
import tasks.ResultWrapper;
import tasks.TaskMandelbrotSet;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ingeborgoftedal on 14.04.15.
 */
public class MandelbrotSetJob implements Job {

    private int numberOfTasks; //TODO how many tasks will this job be divided into??

    // blæ
    private final double xCorner, yCorner, edgeLength;
    private final int n, iterationLimit;

    public MandelbrotSetJob(double xCorner, double yCorner, double edgeLength, int n, int iterationLimit){
        this.xCorner = xCorner;
        this.yCorner = yCorner;
        this.edgeLength = edgeLength;
        this.n = n;
        this.iterationLimit = iterationLimit;
    }

    @Override
    public void generateTasks(Space space) throws RemoteException {
        // Define what chunks
        List<Task> taskList = new ArrayList<Task>();
        for(int i=0; i< n; i++) {
            taskList.add(new TaskMandelbrotSet(xCorner, yCorner, edgeLength, n,iterationLimit, i));
        }
        space.putAll(taskList);
    }

    @Override
    public Integer [][] collectResults(Space space) throws RemoteException {
        Integer[][] resultArray = new Integer[n][n];
        for (int i = 0; i < n; i++) {
            try {
                Result result = space.take();
                ResultWrapper wrapper = (ResultWrapper) result.getTaskReturnValue();
                resultArray [wrapper.getN()] = (Integer[]) wrapper.getTaskReturnValue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultArray;
    }
}

