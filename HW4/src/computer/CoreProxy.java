package computer;

import system.Closure;

import java.rmi.Remote;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Kyrre on 04.05.2015.
 */
public class CoreProxy implements Runnable {

    private final AtomicInteger threadCount;
    private final LinkedBlockingQueue<Closure> tasks;
    private final int threadId;

    public CoreProxy(AtomicInteger threadCount, LinkedBlockingQueue<Closure> tasks, int threadId) {
        this.threadId = threadId;
        this.threadCount = threadCount;
        this.tasks = tasks;
    }

    @Override
    public void run() {
        while (true){
            try {
                //TODO: Handle exception
                long waitTime = System.nanoTime();
                Closure c = tasks.take();
                waitTime = (System.nanoTime()-waitTime)/1000000;
                threadCount.decrementAndGet();
                System.out.println("CoreProxy; run(); Thread: "+threadId);
                c.call();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (threadCount){
                threadCount.incrementAndGet();
                threadCount.notify();
            }
        }
    }
}
