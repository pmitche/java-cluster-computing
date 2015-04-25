package system;

import api.Task;

/**
 * Created by Kyrre on 25.04.2015.
 */
public class CilkThread implements Runnable{

    @Override
    public void run() {

    }
    private void spawn(Argument... arguments){
        //TODO: null tilsvarer missing argument; ?k i Cilk
        Closure childe = new Closure(0,arguments);
    }
    private void spawnNext(Argument... arguments){
        //TODO: Lager en closure
    }
    private void sendArgument(Continuation k){
        //TODO: Sendes til closure spesifisert i k.
    }
}
