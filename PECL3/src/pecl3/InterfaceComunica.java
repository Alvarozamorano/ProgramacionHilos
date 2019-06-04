package pecl3;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Álvaro Zamorano
 * @author Gabriel López
 */
public interface InterfaceComunica extends Remote {

    /**
     *
     * @return @throws RemoteException
     */
    ArrayList<String> getTextos() throws RemoteException;

    /**
     *
     * @return @throws RemoteException
     */
    boolean isAlive() throws RemoteException;

}
