package task;

import system.CilkThread;
import system.Closure;
import system.Continuation;

import java.util.List;

/**
 * Created by hallvard on 5/5/15.
 */
class TaskTspSa extends CilkThread {

    public TaskTspSa(Closure closure) {
        super(closure);
    }

    @Override
    public Object call() {
        return null;
    }

    @Override
    public void decompose(Continuation c) {

    }

    @Override
    public void compose() {

    }

    @Override
    public void run() {

    }
}
