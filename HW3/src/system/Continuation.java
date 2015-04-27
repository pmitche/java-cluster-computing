package system;

/**
 * Created by Kyrre on 23.04.2015.
 */
public class Continuation{

    public final long closureId;
    public final int offset;
    public final Object argument;

    public Continuation(long closureId, int offset, Object argument) {
        this.closureId = closureId;
        this.offset = offset;
        this.argument = argument;
    }
}
