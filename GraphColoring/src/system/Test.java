package system;

import task.TaskTsp;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Kyrre on 26.05.2015.
 */
public class Test {
    public static void main(String[] args) {
        PriorityBlockingQueue<Derp> queue = new PriorityBlockingQueue<>();
        queue.put(new Derp(3));
        queue.put(new Derp(5));
        while (!queue.isEmpty()){
            System.out.println(queue.poll().s);
        }
    }
}

class Derp implements Comparable<Derp>{

    public int s;

    public Derp(int s) {
        this.s = s;
    }

    @Override
    public int compareTo(Derp o) {
        return s - o.s;
    }
}
