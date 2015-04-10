package api;

import java.io.Serializable;

/**
 * Created by Kyrre on 07.04.2015.
 */
public interface Task<T> extends Serializable{
    public <T> T execute();
}
