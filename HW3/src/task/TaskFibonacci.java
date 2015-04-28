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

    private final long START_TIME = System.currentTimeMillis();

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

        int n = (int) k.argument;
        if(n<2) {
            k.setReturnVal(n);
            sendArgument(k); //TODO send n
        } else {
            //TODO: fix pointer hax
            long id = spawnNext(new TaskFibonacci(null), k, null, null);
            Continuation c1 = new Continuation(id, 1, n-1)
                        ,c2 = new Continuation(id, 2, n-2);
            spawn(new TaskFibonacci(null), c1);
            spawn(new TaskFibonacci(null), c2);
        }
    }

    @Override
    public void compose(List list) {
        //Redundant: replaced by sum
    }

    private void sum(Continuation cont, int arg0, int arg1) {
        cont.setReturnVal(arg0 + arg1);
        sendArgument(cont);
    }

    @Override
    public void run() {
        Continuation c = (Continuation) closure.getArgument(0);
        System.out.println("Running: continuation ID: " + c.closureId);
        if (closure.isAncestor()){
            sum((Continuation) closure.getArgument(0), (int) closure.getArgument(1),(int) closure.getArgument(2));
        } else {
            decompose((Continuation) closure.getArgument(0));
        }
    }
}
