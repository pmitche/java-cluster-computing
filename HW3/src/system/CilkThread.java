package system;

import space.SpaceImpl;

/**
 * Created by Kyrre on 25.04.2015.
 */
public abstract class CilkThread implements Runnable{

    public CilkThread(Closure cont){

    }

    protected void spawn(Argument... arguments){
        int count = 0;
        for (Argument a: arguments){
            if (a == null) {
                count++;
            }
        }
        SpaceImpl.getInstance().putClosure(new Closure(count,arguments));
    }
    protected void spawnNext(Argument... arguments){
        int count = 0;
        for (Argument a: arguments){
            if (a == null) {
                count++;
            }
        }
        SpaceImpl.getInstance().putClosure(new Closure(count,arguments));
    }
    protected void sendArgument(Continuation k){
        SpaceImpl.getInstance().receiveArgument(k);
    }
}
