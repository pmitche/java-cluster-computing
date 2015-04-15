package client;

import api.Result;
import api.Space;
import api.Task;
import task.TaskEuclideanTsp;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hallvard on 14.04.2015.
 */
public class EuclideanTspJob implements Job {

    private final int neighbor;

    public EuclideanTspJob(int neighbor) {
        this.neighbor = neighbor;
    }

    @Override
    public void generateTasks(Space space) throws RemoteException {
        List<Task> taskList = new ArrayList<Task>();
        for(int i=1; i< neighbor; i++) {
            taskList.add(i-1, new TaskEuclideanTsp());
        }
        space.putAll(taskList);
    }

    @Override
    public Object collectResults(Space space) throws RemoteException {
        List<Result> resultList = new ArrayList<Result>();
        for(int i=1; i<neighbor; i++) {
            resultList.add(i-1, space.take());
        }
        return resultList;
    }
}
