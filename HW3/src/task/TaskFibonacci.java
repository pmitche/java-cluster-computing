package task;

import api.Result;
import system.CilkThread;
import system.Closure;
import system.Continuation;
import system.ResultValueWrapper;

import java.util.List;

/**
 * Created by hallvard on 4/25/15.
 */
public class TaskFibonacci extends CilkThread {

    private final long startTime = System.currentTimeMillis();

    public TaskFibonacci(Closure closure) {
        super(closure);
    }

    @Override
    public Object call() {
        Thread t = new Thread(this);
        t.start();
        return null;
    }

    @Override
    public void decompose(Continuation k) {
        int n = (int)k.argument;
        if(n<2) {
            sendArgument(k); //TODO send n
        } else {
            long id = spawnNext(k, null, null);
            Continuation c1 = new Continuation(id, n-1, null)
                        ,c2 = new Continuation(id, n-2, null);
            spawn(c1);
            spawn(c2);
        }
    }

    @Override
    public Result compose(List list) {
        Integer sum = 0;
        for(Object c : list) {
            ResultValueWrapper rvw = (ResultValueWrapper)((Continuation)c).argument; //Castception
            sum += (int)rvw.getTaskReturnValue();
        }
        ResultValueWrapper<Integer, Object> rvw = new ResultValueWrapper(sum, ((Continuation)list.get(0)).closureId);

        return new Result(rvw, System.currentTimeMillis()-startTime);
    }

    @Override
    public void run() {
       decompose(new Continuation(closure.getId(), 0, closure.getArgument(0)));
    }
}
