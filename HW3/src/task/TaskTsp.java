package task;

import api.Result;
import api.Task;
import system.CilkThread;
import system.Closure;
import system.Continuation;

import java.util.List;

/**
 * Created by hallvard on 4/26/15.
 */
public class TaskTsp extends CilkThread implements Task {

    private final long START_TIME = System.currentTimeMillis();

    public TaskTsp(Closure closure) {
        super(closure);
    }

    @Override
    public Object call() {
        Thread t = new Thread(this);
        t.start();
        return null;
    }

    @Override
    public void decompose(Continuation c) {


    }

    @Override
    public Result compose(List list) {
        return null;
    }

    @Override
    public void run() {

    }
}
