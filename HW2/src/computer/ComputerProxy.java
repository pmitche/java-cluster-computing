package computer;

import api.Result;
import api.Task;
import space.SpaceImpl;
import system.Computer;
import java.rmi.RemoteException;

/**
 * Created by Kyrre on 15.04.2015.
 * This class takes a Computer and a Space as parameters in its constructor.
 * Its function is to retrieve jobs from the Space, and execute them on the Computer.
 */
public class ComputerProxy implements Runnable {
    private final Computer computer;
    private final SpaceImpl space;

    public ComputerProxy(Computer computer, SpaceImpl space) {
        this.computer = computer;
        this.space = space;
    }

    /**
     * Runs a loop that tries to execute tasks on the computer. If the computer succeeds the result is added to Space.
     * If the computer fails, the RemoteException is caught, and the task is re-submitted to the Space task queue.
     */
    @Override
    public void run() {
       while (true){
           Task t = null;
           Result r = null;
           try {
               t = space.getTaskFromQueue();
               r = (Result) computer.execute(t);
           } catch (RemoteException e) {
               try {
                   space.put(t);
                   System.out.println("Computer failed, task reentered in queue...");
                   return;
               } catch (RemoteException e1) {
                   e1.printStackTrace();
               } catch (InterruptedException e1) {
                   e1.printStackTrace();
               }
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           try {
               space.putResult(r);
           } catch (RemoteException e) {
               e.printStackTrace();
           }
       }
    }
}
