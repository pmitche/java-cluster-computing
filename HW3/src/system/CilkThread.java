package system;

import space.SpaceImpl;

/**
 * Created by Kyrre on 25.04.2015.
 */
public class CilkThread implements Runnable{

    @Override
    public void run() {

    }

    public CilkThread(Continuation cont){

    }

    private void spawn(Argument... arguments){
        int count = 0;
        for (Argument a: arguments){
            if (a == null) {
                count++;
            }
        }
        Closure child = new Closure(count,arguments);
    }
    private void spawnNext(Argument... arguments){
        int count = 0;
        for (Argument a: arguments){
            if (a == null) {
                count++;
            }
        }
        SpaceImpl.getInstance().putClosure(new Closure(count,arguments));
    }
    private void sendArgument(Continuation k){
        SpaceImpl.getInstance().receiveArgument(k);
    }
}
