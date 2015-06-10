package task;

import api.Job;
import api.Result;
import api.Space;
import client.ClientGraphColoring;
import system.Closure;
import system.Continuation;
import system.Global;
import util.ProblemGenerator;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.Optional;
import java.util.Scanner;

/**
 * Created by hallvard on 5/26/15.
 */
public class JobGraphColoring implements Job {

    private final int DOMAIN_SIZE = promptDomainSize();

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

        Runnable r = () -> {
            Result result;
            StateGraphColoring rs = null;
            do{
                try {
                    result = space.take();
                    rs = (StateGraphColoring)result.getTaskReturnValue();
                    System.out.println(rs.getClass());
                    //todo return
                    ClientGraphColoring.addLabel(rs);

                } catch (InterruptedException e) {  e.printStackTrace(); continue;}
                catch (RemoteException re) {}
            } while (!rs.isSolution());
            ClientGraphColoring.addLabel(rs);
        };
        r.run();

        //TODO Hvis return value er -1 så vul det ho ikke vætr nor verdi å displaye...

        return Optional.empty();
    }

    /**
     * Prompt for domainSize for the problem.
     * @return  DOMAIN_SIZE
     */
    private int promptDomainSize() {
        System.out.println("Input domain size");
        int domainSize = new Scanner(System.in).nextInt();
        return domainSize;
    }
}
