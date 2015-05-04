package computer;

import api.Result;
import space.SpaceImpl;
import system.Closure;
import system.Computer;

import java.rmi.RemoteException;

/**
 * Created by Kyrre on 15.04.2015.
 * This class takes a Computer and a Space as parameters in its constructor.
 * Its function is to retrieve jobs from the Space, and execute them on the Computer.
 */
public class ComputerProxy implements Runnable {
    private final Computer computer;

    public ComputerProxy(Computer computer) {
        this.computer = computer;
    }

    /**
     * Runs a loop that tries to execute tasks on the computer. If the computer succeeds the result is added to Space.
     * If the computer fails, the RemoteException is caught, and the task is re-submitted to the Space task queue.
     */
    @Override
    public void run() {
        while (true) {
            Closure closure = null;
            Result r = null;
            try {
                System.out.println("ComputerProxy; Waiting for ready Closure");
                closure = SpaceImpl.getInstance().takeReadyClosure();
                System.out.println("ComputerProxy; took and executing closure: " + closure);
                computer.execute(closure);
                System.out.println("ComputerProxy; done executing closure: " + closure);
            } catch (RemoteException e) {
                //TODO: re-enter closure
                // space.put(closure);
                System.out.println("ComputerProxy; Computer failed, task re-entered in queue...");
                e.printStackTrace();
                return;
            }
        }
    }
}
