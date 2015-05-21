package computer;

import space.SpaceImpl;
import system.Closure;
import system.Continuation;
import system.Global;

import java.rmi.RemoteException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Kyrre on 06.05.2015.
 */
public class SpaceProxyAsyncHandler implements Runnable {

    private final LinkedBlockingQueue<QueueTicket> waitingQueue;

    public SpaceProxyAsyncHandler(LinkedBlockingQueue<QueueTicket> waitingQueue){
        this.waitingQueue = waitingQueue;
    }
    @Override
    public void run() {
        QueueTicket ticket = null;
        while (true){
            try {
                ticket = waitingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (ticket.getType()){
                case PUT_CLOSURE:{
                    try {
                        SpaceImpl.getInstance().put((Closure) ticket.getArg());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case PUT_CLOSURE_IN_READY:{
                    try {
                        SpaceImpl.getInstance().putClosureInReady((Closure) ticket.getArg());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case RECEIVE_ARGUMENT:{
                    try {
                        SpaceImpl.getInstance().receiveArgument((Continuation) ticket.getArg());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case CLOSURE_DONE:{
                    try {
                        SpaceImpl.getInstance().closureDone((String) ticket.getArg());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case UPDATE_GLOBAL:{
                    try {
                        SpaceImpl.getInstance().updateGlobal((Global) ticket.getArg());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
}
