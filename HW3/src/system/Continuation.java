package system;

/**
 * Created by Kyrre on 23.04.2015.
 */
public class Continuation{

    public final long closureId;
    public final int offset;
    public final Object argument;
    public Object returnVal;

    public Continuation(long closureId, int offset, Object argument) {
        this.closureId = closureId;
        this.offset = offset;
        this.argument = argument;
    }

    public void setReturnVal(Object arg) {
        returnVal = arg;
    }

    public Object getReturnVal() {
        return returnVal;
    }
}
