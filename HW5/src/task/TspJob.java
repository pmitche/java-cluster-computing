package task;

import api.Job;
import api.Space;
import client.ClientTsp;
import system.Closure;
import system.Continuation;
import system.Global;
import system.ResultValueWrapper;

import java.rmi.RemoteException;
import java.util.ArrayList;
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
    public List<Double> cost = new ArrayList<>();

    /**
     * Constructor for the Traveling salesman job.
     * <p>The class, splits the problem of TSP into several
     * subtasks that it collects and puts together later</p>
     * @param cities
     */
    public TspJob(double[][] cities) {
        this.cities = cities;
        generateLowerBoundList();
        System.out.println();
    }

    private void generateLowerBoundList() {
        for (int i = 0; i < cities.length; i++){
            double[] city = cities[i];
            double min = Double.MAX_VALUE;
            for (int j = 0; j < cities.length; j++) {
                double[] otherCity = cities[j];
                if (i != j){
                    double tmp = TspUtils.euclideanDistance(city, otherCity);
                    if (tmp < min){
                        min = tmp;
                    }
                }
            }
            cost.add(min*2);
        }
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


        List<Integer> unusedCities = new ArrayList<>();
        for(int i=1; i<cities.length; i++)
            unusedCities.add(i);

        List<Integer> partialTrip = new ArrayList<>();
        partialTrip.add(0);

        TaskTsp startTask = new TaskTsp(new Closure(0, new Global(new Double(Double.MAX_VALUE)), new Continuation("-1",-1,new TaskTsp.Wrapper(unusedCities, partialTrip))));

/*      //Used for Simmulated Annealing
        Integer[] startPath = new Integer[cities.length];
        for(int i=0; i<cities.length; i++)
            startPath[i] = i;

        TaskTspSa startTask = new TaskTspSa(new Closure(0, new Continuation("-1", -1, new AnnealingState(startPath, ClientTsp.START_TEMP, -1))));
*/
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
            Object rs = ((ResultValueWrapper)space.take().getTaskReturnValue()).getTaskReturnValue();
            System.out.println(rs.getClass());
            return rs;
        } catch (InterruptedException e) {  e.printStackTrace();     }
        return Optional.empty();
    }
}
