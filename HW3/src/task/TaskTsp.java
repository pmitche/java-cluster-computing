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
        int n = c.offset;
        if(n < 2) {
            sendArgument(c);
            return;
        }
        Integer[] a = (Integer[])c.argument;
        for(int i=1; i<n; i++) {
            long id = spawnNext()
            swap(a.clone(), i, n-1);

        }


    }

    @Override
    public Result compose(List list) {
        return null;
    }

    @Override
    public void run() {

    }

    private static Integer[] swap(Integer[] a, int i, int i1) {
        assert i>2 && i1> 2;

        Integer tmp = a[i];
        a[i] = a[i1];
        a[i1] = tmp;
        return  a;
    }
}
