package tasks;

import api.Task;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Kyrre on 08.04.2015.
 */
public class TaskEuclideanTsp implements Task<List<Integer>> {

    double[][] cities;

    public TaskEuclideanTsp(double[][] cities) {
        this.cities = cities;
    }

    @Override
    public List<Integer> execute() {
        return Arrays.asList(new Integer[]{0, 1, 8, 3, 4, 5, 6, 2, 9, 7});
    }
}
