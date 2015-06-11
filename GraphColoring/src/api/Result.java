package api;
import java.io.Serializable;

/**
 * When Space is finished with computing the resulting computation is wrapped in an instance of this class.
 * @param <T>
 */
public class Result<T> implements Serializable
{
    private final T taskReturnValue;
    private final long taskRunTime;


    /**
     * constructor for results of the returnvalue and run time for each task
     * @param taskReturnValue
     * @param taskRunTime
     */
    public Result( T taskReturnValue, long taskRunTime )
    {
        assert taskReturnValue != null;
        assert taskRunTime >= 0;
        this.taskReturnValue = taskReturnValue;
        this.taskRunTime = taskRunTime;
    }


    /**
     *
     * @return return value
     */
    public T getTaskReturnValue() { return taskReturnValue; }

    public long getTaskRunTime() { return taskRunTime; }
    /**
     * Self explanatory...
     * @return
     */
    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( getClass() );
        stringBuilder.append( "\n\tExecution time:\n\t" ).append( taskRunTime );
        stringBuilder.append( "\n\tReturn value:\n\t" ).append( taskReturnValue.toString() );
        return stringBuilder.toString();
    }
}
