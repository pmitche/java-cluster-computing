package tasks;

import api.Result;

import java.util.Objects;

/**
 * Created by Kyrre on 15.04.2015.
 */
public class MandelbrotResult extends Result {
    public MandelbrotResult(Object taskReturnValue, long taskRunTime) {
        super(taskReturnValue, taskRunTime);
    }
}
