package computer;

import api.Space;
import system.Closure;
import system.Continuation;

import java.rmi.RemoteException;

/**
 * Created by Kyrre on 27.04.2015.
 */
public class Scheduler {

    private static volatile Scheduler instance;
    private Space space;

    /**
     * This class serves as a singleton. As such this is just here so that Scheduler compiles, and it is never used.
     */
    private Scheduler(){
        space = null;
    }

    public static Scheduler getInstance(){
        if (instance == null ) {
            synchronized (Scheduler.class) {
                if (instance == null) {
                    instance = new Scheduler();
                }
            }
        }
        return instance;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public void putClosure(Closure closure) {

    }

    public void receiveArgument(Continuation k) {

    }

    public void putClosureInReady(Closure closure) {
    }
}
