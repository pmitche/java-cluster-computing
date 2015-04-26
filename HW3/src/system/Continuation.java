package system;

/**
 * Created by Kyrre on 23.04.2015.
 */
public class Continuation{

    public final int closureId;
    public final int offset;
    public final Object argument;

    public Continuation(int closureId, int offset, Object argument) {
        this.closureId = closureId;
        this.offset = offset;
        this.argument = argument;
    }
}
