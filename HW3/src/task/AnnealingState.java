package task;

import java.io.Serializable;

/**
 * Created by hallvard on 5/5/15.
 */
final class AnnealingState implements Serializable{

    public final Double TEMP;
    public final Integer[] PATH;
    public final Integer ITERATION;

    public AnnealingState(Integer[] path, Double temp, Integer iteration) {
        PATH = path;
        TEMP = temp;
        ITERATION = iteration;
    }
}
