package client;

import api.Task;

/**
 * Created by Kyrre on 14.04.2015.
 */
public class DummyTask implements Task {
    @Override
    public Object call() {
        return null;
    }
}
