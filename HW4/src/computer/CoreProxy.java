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

    public CoreProxy(AtomicInteger threadCount, LinkedBlockingQueue<Closure> tasks) {
        this.threadCount = threadCount;
        this.tasks = tasks;
    }

    @Override
    public void run() {
        threadCount.decrementAndGet();
        try {
            //TODO: Handle exception
            tasks.take().call();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (threadCount){
            threadCount.incrementAndGet();
            threadCount.notify();
        }
    }
}
