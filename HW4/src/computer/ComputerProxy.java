package computer;

import space.SpaceImpl;
import system.Closure;

import java.rmi.RemoteException;
import java.util.ArrayList;

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
     * Runs a loop that tries to execute tasks on the computer.
     * If the computer fails, an Exception is caught, and the tasks are re-submitted to the Space's task queue.
     */
    @Override
    public void run() {
        ArrayList<Closure> closures = new ArrayList<>();
        while (true) {
            Closure closure = null;
            try {
                closure = SpaceImpl.getInstance().takeReadyClosure();
                closures.add(closure);
                computer.execute(closure);
            } catch (Exception e) {
                try {
                    SpaceImpl.getInstance().putAll(closures);
                } catch (RemoteException e1) {
                    System.out.println("ComputerProxy; run(): Something really strange has failed...");
                }
                System.out.println("ComputerProxy; Computer failed, closures re-entered in queue...");
                return;
            }
        }
    }
}
