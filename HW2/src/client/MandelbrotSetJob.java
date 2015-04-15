package client;

import api.Job;
import api.Result;
import api.Space;
import api.Task;
import tasks.TaskMandelbrotSet;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ingeborgoftedal on 14.04.15.
 */
public class MandelbrotSetJob implements Job {

    private final int temp; //how many tasks will this job be divided into??

    // blæ
    double xCorner;
    double yCorner;
    double edgeLength;
    int n;
    int iterationLimit;

    public MandelbrotSetJob(int temp){
        this.temp = temp;
    }

    @Override
    public void generateTasks(Space space) throws RemoteException {
        List<Task> taskList = new ArrayList<Task>();
        for(int i=1; i< temp; i++) {
            taskList.add(i-1, new TaskMandelbrotSet(xCorner, yCorner, edgeLength, n,iterationLimit));
        }
        space.putAll(taskList);
    }

    @Override
    public Object collectResults(Space space) throws RemoteException {
        List<Result> resultList = new ArrayList<Result>();
        for (int i = 1; i < temp; i++) {
            resultList.add(i - 1, space.take());
        }
        return resultList;
    }
}

