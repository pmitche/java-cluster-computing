package client;

import api.Result;
import api.Task;

/**
 * Created by Kyrre on 14.04.2015.
 */
public class DummyTask implements Task {
    @Override
    public Object call() {
        return new Result<String>("Derp, im a result :P", 0);
    }
}
