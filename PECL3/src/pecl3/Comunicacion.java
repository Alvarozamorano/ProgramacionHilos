package pecl3;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author Álvaro Zamorano
 * @author Gabriel López
 */
public class Comunicacion extends UnicastRemoteObject implements InterfaceComunica {

    //Atributos de la clase
    private final EstadoGasolinera estado;

    /**
     * Constructor de la clase
     *
     * @param p_estado
     * @throws RemoteException
     */
    public Comunicacion(EstadoGasolinera p_estado) throws RemoteException {
        estado = p_estado;
    }

    /**
     * Implementación del método remoto
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public ArrayList<String> getTextos() throws RemoteException {
        ArrayList<String> textos = new ArrayList<>();
        textos.add(estado.getEspera().getText());
        for (int i = 0; i < 8; i++) {
            textos.add(estado.getSurtidores().get(i).getText());
        }
        for (int i = 0; i < 8; i++) {
            textos.add(estado.getVehiculos().get(i).getText());
        }
        return textos;
    }

    /**
     * Implementación del método remoto
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public boolean isAlive() throws RemoteException {
        return estado.isAlive();
    }

}
