package task;

import java.io.Serializable;

/**
 * Created by hallvard on 5/5/15.
 *
 * State containting the state of a Simmulated Annealing thread
 */
final class AnnealingState implements Serializable{

    public final Double TEMP;
    public final Integer[] PATH;
    public final Integer ITERATION;

    /**
     * Simple single object wrapper class
     * @param path  Path
     * @param temp  Temperature of the simulated annealing
     * @param iteration current iteration
     */
    public AnnealingState(Integer[] path, Double temp, Integer iteration) {
        PATH = path;
        TEMP = temp;
        ITERATION = iteration;
    }
}
