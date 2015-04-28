package task;

import api.Job;
import api.Space;
import system.Closure;
import system.Continuation;

import java.rmi.RemoteException;
import java.util.Optional;

/**
 * Created by hallvard on 4/28/15.
 */
public class FibonacciJob implements Job {

    private final int DEPTH;

    /**
     * Fibonacci Job
     * @param depth the N-th numer to be found.
     */
    public FibonacciJob(int depth) {
        DEPTH = depth;
    }


    /**
     * Generate Tasks
     *
     * <p>The task is divided into smaller more handable subtasks
     * @param space Space where the tasks should be sent</p>
     * @throws RemoteException
     */
    @Override
    public void generateTasks(Space space) throws RemoteException {
        try {
            space.put(new TaskFibonacci(new Closure(0, new Continuation(-1, -1, new Integer(DEPTH)))));
        } catch (InterruptedException e) {   e.printStackTrace();   }
    }

    /**
     * Collect Results
     *
     * <p>This method collects the completed tasks from Space
     * then puts the partial solutions together to the final solution</p>
     * @param space
     * @return
     * @throws RemoteException
     */
    @Override
    public Object collectResults(Space space) throws RemoteException {
        try {
            return space.take().getTaskReturnValue();
        } catch (InterruptedException e) {  e.printStackTrace();     }
        return Optional.empty();
    }
}
