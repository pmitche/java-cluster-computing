package computer;

import system.Closure;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is responsible for actual execution of Cilk Threads, through closures.
 */
public class CoreHandler implements Runnable {

    private final AtomicInteger threadCount;
    private final LinkedBlockingQueue<Closure> tasks;
    private final int threadId;

    public CoreHandler(AtomicInteger threadCount, LinkedBlockingQueue<Closure> tasks, int threadId) {
        this.threadId = threadId;
        this.threadCount = threadCount;
        this.tasks = tasks;
    }
    long starTime = -1;
    long localTime = 0;
    /**
     * Retrives Closure's from a queue of waiting closures, and executes them.
     */
    @Override
    public void run() {
        while (true){
            try {
                //idle logging
                if (starTime == -1){
                    starTime = System.currentTimeMillis();
                }
                boolean waiting = false;
                if (tasks.isEmpty()){
                    waiting = true;
                }
                long waitTime = System.currentTimeMillis();
                Closure c = tasks.take();
                if (waiting){
                    localTime += System.currentTimeMillis() - waitTime;
                }
                double waitPercent = ((double)localTime)/((double)(System.currentTimeMillis() - starTime));
                System.out.println("Idle time in percent: "+(100-(waitPercent*100)));
                //------------------------------
                c.call();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (threadCount){
                threadCount.notify();
            }
        }
    }
}
