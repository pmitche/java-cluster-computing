package system;

/**
 * Created by Kyrre on 23.04.2015.
 */
public class Continuation implements Argument{

    public final int closureId;
    public final int offset;
    public final Argument argument;

    public Continuation(int closureId, int offset, Argument argument) {
        this.closureId = closureId;
        this.offset = offset;
        this.argument = argument;
    }
}
