package task;

import api.Job;
import api.Result;
import api.Space;
import api.Task;
import system.Closure;
import system.Continuation;
import system.ResultValueWrapper;
import task.TaskTsp;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by hallvard on 4/28/15.
 */

/**
 * Created by Hallvard on 14.04.2015.
 */
public class TspJob implements Job {
    //problem
    private double[][] cities;

    /**
     * Constructor for the Traveling salesman job.
     * <p>The class, splits the problem of TSP into several
     * subtasks that it collects and puts together later</p>
     * @param cities
     */
    public TspJob(double[][] cities) {
        this.cities = cities;
    }

    /**
     * Generate Tasks
     *
     * <p>The task is divided into smaller more handable subtasks
     * @param space Space where the tasks should be sent</p>
     * @throws RemoteException
     */
    @Override
    public void generateTasks(Space space) throws RemoteException {

        ArrayList<Integer> unusedCities = new ArrayList<>();
        for(int i=1; i<cities.length; i++)
            unusedCities.add(i);

        ArrayList<Integer> partialTrip = new ArrayList<>();
        partialTrip.add(0);

        System.out.println((unusedCities==null)+" : "+(partialTrip==null));
        TaskTsp startTask = new TaskTsp(new Closure(0, new Continuation("-1",-1,new TaskTsp.Wrapper(unusedCities, partialTrip))));

        try {
            space.put(startTask);
        } catch (InterruptedException e) {e.printStackTrace();}
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

        try {
            return ((ResultValueWrapper)space.take().getTaskReturnValue()).getTaskReturnValue();
        } catch (InterruptedException e) {  e.printStackTrace();     }
        return Optional.empty();
    }
}

