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
    private ArrayList<Argument> arguments;

    public Closure(ArrayList<Argument> arguments, int missingArgsCount) {
        this.arguments = arguments;
        this.missingArgsCount = missingArgsCount;
        this.id = this.hashCode();
    }
}
