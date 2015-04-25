package system;

import api.Task;

import java.util.ArrayList;

/**
 * Created by Kyrre on 23.04.2015.
 */
public class Closure {

    public long id;
    private Task task;
    private int missingArgsCount;
    private Argument[] arguments;

    //TODO: null tilsvarer manglene argumenter.
    public Closure(int missingArgsCount, Argument... arguments) {
        this.arguments = arguments;
        this.missingArgsCount = missingArgsCount;
        this.id = this.hashCode();
    }
}
