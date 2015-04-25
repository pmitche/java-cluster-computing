package system;

import api.Task;

import java.util.ArrayList;

/**
 * Created by Kyrre on 23.04.2015.
 */
public class Closure {

    private long id;
    private CilkThread cilkThread;
    private int missingArgsCount;
    private Argument[] arguments;

    //TODO: null tilsvarer manglene argumenter.
    public Closure(int missingArgsCount, Argument... arguments) {
        this.arguments = arguments;
        this.missingArgsCount = missingArgsCount;
        this.id = this.hashCode();
    }

    public long getId() {
        return id;
    }

    public void setArgument(Continuation k) {
        arguments[k.offset] = k.argument;
    }
}
