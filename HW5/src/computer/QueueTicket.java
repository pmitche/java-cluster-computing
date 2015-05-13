package computer;

/**
 * Created by Kyrre on 06.05.2015.
 */
public class QueueTicket {

    public enum Type {
        PUT_CLOSURE,
        PUT_CLOSURE_IN_READY,
        RECEIVE_ARGUMENT,
        CLOSURE_DONE;
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
