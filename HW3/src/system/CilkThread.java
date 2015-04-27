package system;

import space.SpaceImpl;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Kyrre on 25.04.2015.
 */
public abstract class CilkThread implements Runnable{

    public CilkThread(Closure cont){

    }

    protected long spawn(Object... arguments){
        int count = 0;
        count = (int)Arrays.asList(arguments).stream().filter( e -> !Optional.ofNullable(e).isPresent()).count();

        Closure c = new Closure(count,arguments);
        SpaceImpl.getInstance().putClosure(c);
        return c.getId();
    }
    protected long spawnNext(Object... arguments){
        int count = 0;
        for (Object a: arguments){
            if (a == null) {
                count++;
            }
        }
        Closure c = new Closure(count,arguments);
        SpaceImpl.getInstance().putClosure(c);
        return c.getId();
    }
    protected void sendArgument(Continuation k){
        SpaceImpl.getInstance().receiveArgument(k);
    }
}
