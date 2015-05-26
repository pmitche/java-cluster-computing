package task;

import system.CilkThread;
import system.Closure;

/**
 * Created by hallvard on 5/26/15.
 */
public class TaskGraphColoring extends CilkThread {


    public TaskGraphColoring(Closure closure) {
        super(closure);

    }

    @Override
    public void decompose() {

    }

    @Override
    public void compose() {

    }
}
