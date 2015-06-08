package task;

import api.Job;
import api.Space;
import system.Closure;
import system.Continuation;
import system.Global;
import util.ProblemGenerator;

import java.rmi.RemoteException;
import java.util.Optional;

/**
 * Created by hallvard on 5/26/15.
 */
public class JobGraphColoring implements Job {

    private final int DOMAIN_SIZE = 6;

    @Override
    public void generateTasks(Space space) throws RemoteException {

        StateGraphColoring state0 = new ProblemGenerator(DOMAIN_SIZE).getProblem();
        assert state0!=null;

        TaskGraphColoring startTask = new TaskGraphColoring(new Closure(0, new Global(new Double(Double.MAX_VALUE)), new Continuation("-1",-1,state0)));

        try {
            space.put(startTask);
        } catch( InterruptedException e ){ e.printStackTrace();}
    }

    @Override
    public Object collectResults(Space space) throws RemoteException {
        try {
            StateGraphColoring rs = ((StateGraphColoring)space.take().getTaskReturnValue());
            System.out.println(rs.getClass());
            return rs;
        } catch (InterruptedException e) {  e.printStackTrace();     }
        return Optional.empty();
    }
}
