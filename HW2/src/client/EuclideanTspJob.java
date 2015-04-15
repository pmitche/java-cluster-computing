package client;

import api.Job;
import api.Result;
import api.Space;
import api.Task;
import tasks.TaskEuclideanTsp;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hallvard on 14.04.2015.
 */
public class EuclideanTspJob implements Job {

    private double[][] cities;
    private Integer[] minPath;

    public EuclideanTspJob(double[][] cities) {
        this.cities = cities;
        minPath = new Integer[cities.length];
        for(int i=0; i<minPath.length; i++)
            minPath[i] = i;
    }

    @Override
    public void generateTasks(Space space) throws RemoteException {
        List<Task> taskList = new ArrayList<Task>();
        for(int i=1; i<cities.length; i++) {
            taskList.add(new TaskEuclideanTsp(cities, i));
        }
        space.putAll(taskList);
    }

    @Override
    public Object collectResults(Space space) throws RemoteException {
        //Collect task results
        List<Result> resultList = new ArrayList<Result>();
        for(int i=1; i<cities.length-1; i++) {
            resultList.add(i-1, space.take());
        }

        //Process task results to final result
        List<Integer> minPath = (List<Integer>)resultList.remove(0).getTaskReturnValue();
        double minDistance = totalDistance(minPath);
        for (Result r : resultList) {
            List<Integer> tmp = (List<Integer>)r.getTaskReturnValue();
            double dist = totalDistance(tmp);
            if(dist < minDistance){
                minDistance = dist;
                minPath = tmp;
            }
        }
        return minPath;
    }

    private double totalDistance(List<Integer> list) {
        double distance = 0;
        for (int i = 0; i < list.size(); i++)
            if (i < list.size()-1)
                distance += euclideanDistance(cities[list.get(i)], cities[list.get(i+1)]);
        return distance;
    }

    private double euclideanDistance(double[] d0, double[] d1) {
        double xDistance = Math.abs(d0[0]-d1[0]);
        double yDistance = Math.abs(d0[1] - d1[1]);
        return Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );
    }
}
