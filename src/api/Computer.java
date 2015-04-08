package api;

import java.rmi.Remote;

/**
 * Created by Kyrre on 07.04.2015.
 */
public interface Computer extends Remote {

    public T execute(Task task);

}
