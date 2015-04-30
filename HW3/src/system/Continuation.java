package system;

import java.io.Serializable;

/**
 * Created by Kyrre on 23.04.2015.
 */
public class Continuation implements Serializable{

    public final String closureId;
    public final int offset;
    public final Object argument;
    private Object returnVal;

    /**
     * Continuation constructor
     * @param closureId: ID of the closure being passed to the constructor
     * @param offset: Integer offset
     * @param argument: Object argument being passed to constructor
     */
    public Continuation(String closureId, int offset, Object argument) {
        this.closureId = closureId;
        this.offset = offset;
        this.argument = argument;
    }

    /**
     * Sets the return value
     * @param arg: argument to be returned
     */
    public void setReturnVal(Object arg) {
        returnVal = arg;
    }

    /**
     * Getter for returnVal
     * @return Returns the value
     */
    public Object getReturnVal() {
        return returnVal;
    }
}
