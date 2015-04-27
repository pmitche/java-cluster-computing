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

    public TaskTsp(Closure cont) {
        super(cont);
    }

    @Override
    public Object call() {
        return null;
    }

    @Override
    public void decompose(Continuation c, int n, Integer[]) {

    }

    @Override
    public Result compose(List list) {
        return null;
    }

    @Override
    public void run() {

    }
}
