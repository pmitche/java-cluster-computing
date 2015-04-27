package task;

import api.Result;
import api.Task;
import system.CilkThread;
import system.Closure;
import system.Continuation;
import system.ResultValueWrapper;

import java.util.List;

/**
 * Created by hallvard on 4/25/15.
 */
class TaskFibonacci extends CilkThread implements Task {

    private int n;
    private long startTime;

    public TaskFibonacci(int n) {
        this.n = n;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    protected Object call() throws Exception {
        return 2;
    }

    @Override
    public void decompose(Continuation k, int n) {
        if(n<2) {
            sendArgument(k); //TODO send n
        } else {
            Continuation c1 = new Continuation(k.closureId, n-1, null)
                        ,c2 = new Continuation(k.closureId, n-1, null);
            spawnNext(k, c1, c2);
            spawn(c1);
            spawn(c2);
        }
    }

    @Override
    public Result compose(List<Continuation> list) {
        Integer sum = 0;
        for(Continuation c : list) {
            sum += (int)c.argument;
        }

        ResultValueWrapper<Integer, Object> rvw = new ResultValueWrapper(sum, list.get(0).closureId);
        return new Result(rvw, System.currentTimeMillis()-startTime);

    }

    @Override
    public void run() {

    }
}
