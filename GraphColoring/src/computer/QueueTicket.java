package computer;

/**
 * Created by Kyrre on 06.05.2015.
 * Is used to order requests to Space in Async mode.
 * Used by SpaceProxyAsyncHandler
 */
public class QueueTicket {

    public enum Type {
        PUT_CLOSURE,
        PUT_CLOSURE_IN_READY,
        RECEIVE_ARGUMENT,
        CLOSURE_DONE,
        UPDATE_GLOBAL;
    }
    private final Type type;
    private final Object arg;

    public QueueTicket(Type type, Object arg) {
        this.type = type;
        this.arg = arg;
    }

    public Type getType() {
        return type;
    }

    public Object getArg() {
        return arg;
    }
}
