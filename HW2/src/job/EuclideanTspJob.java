package job;

import api.Job;
import api.Result;
import api.Space;
import api.Task;
import system.ResultValueWrapper;
import task.TaskEuclideanTsp;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hallvard on 14.04.2015.
 */
public class EuclideanTspJob implements Job {
    //problem
    private double[][] cities;

    /**
     * Constructor for the Traveling salesman job.
     * <p>The class, splits the problem of TSP into several
     * subtasks that it collects and puts together later</p>
     * @param cities
     */
    public EuclideanTspJob(double[][] cities) {
        this.cities = cities;
    }

    /**
     * Generate rsults
     *
     * <p>The task is divided into smaller more handable subtasks
     * @param space Space where the tasks should be sent</p>
     * @throws RemoteException
     */
    @Override
    public void generateTasks(Space space) throws RemoteException {
        List<Task> taskList = new ArrayList<Task>();
        for(int i=1; i<cities.length; i++) {
            taskList.add(new TaskEuclideanTsp(cities, i));
        }
        space.putAll(taskList);
    }

    /**
     * Collect Results
     *
     * <p>This method collects the completed tasks from Space
     * then puts the partial solutions together to the final solution</p>
     * @param space
     * @return
     * @throws RemoteException
     */
    @Override
    public Object collectResults(Space space) throws RemoteException {
        //Collect task results

        List<Result> resultList = new ArrayList<Result>();
        for(int i=1; i<cities.length-1; i++) {
            try {
                resultList.add(i - 1, space.take());
            } catch (InterruptedException e) {e.printStackTrace();}
        }

        //Process task results to final result
        ResultValueWrapper rw0 = (ResultValueWrapper)resultList.remove(0).getTaskReturnValue();
        List<Integer> minPath = (List<Integer>)rw0.getTaskReturnValue();
        double minDistance = (Double)rw0.getN();
        for (Result r : resultList) {
            ResultValueWrapper rw = (ResultValueWrapper)r.getTaskReturnValue();
            List<Integer> tmp = (List<Integer>)rw.getTaskReturnValue();
            double dist = (Double)rw.getN();
            if(dist < minDistance){
                minDistance = dist;
                minPath = tmp;
            }
        }
        return minPath;
    }
}
