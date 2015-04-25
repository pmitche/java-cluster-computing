package task;

import api.Result;
import api.Task;
import system.CilkThread;

import java.util.List;

/**
 * Created by hallvard on 4/25/15.
 */
class TaskFibonacci extends CilkThread implements Task
{

    private int n;
    private long startTime;

    public TaskFibonacci(int n) {
        this.n = n;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    protected Object call() throws Exception {
        return null;
    }

    @Override
    public Result decompose() {
        if(n<2) {
            return new Result(n, System.currentTimeMillis()-startTime);
        }

        return null;
    }

    @Override
    public Result compose(List list) {
        return null;
    }

    @Override
    public void run() {

    }
}
