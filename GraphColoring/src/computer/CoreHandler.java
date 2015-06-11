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

    long localTime = 0;
    /**
     * Retrives Closure's from a queue of waiting closures, and executes them.
     */
    @Override
    public void run() {
        while (true){
            try {
                long waitTime = System.currentTimeMillis();
                Closure c = tasks.take();
                localTime += System.currentTimeMillis() - waitTime;
                double waitPercent = ((double)localTime)/((double)(ComputerImpl.computerStarTime - System.currentTimeMillis()));
                System.out.println(waitPercent);
                //System.out.println("CoreHandler; run(); Thread: "+threadId);
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
