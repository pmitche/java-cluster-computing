package task;

import api.Job;
import api.Space;
import system.Closure;
import system.Continuation;
import system.Global;

import java.rmi.RemoteException;

/**
 * Created by hallvard on 5/26/15.
 */
public class JobGraphColoring implements Job {
    @Override
    public void generateTasks(Space space) throws RemoteException {

        //TODO - get problem description

        //TODO - init Task
        TaskGraphColoring task0 = null; //new TaskGraphColoring(new Closure(0,new Global(Double.MAX_VALUE)), new Continuation("-1",-1, null));

        try {
            space.put(task0);
        } catch( InterruptedException e ){ e.printStackTrace();}

    }

    @Override
    public Object collectResults(Space space) throws RemoteException {
        return null;
    }
}
