package pecl3;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JTextField;

/**
 *
 * @author Álvaro Zamorano
 * @author Gabriel López
 */
public class EstadoGasolinera implements Serializable {

    //Atributos de la clase
    private final ArrayList<JTextField> vehiculos;
    private final ArrayList<JTextField> surtidores;
    private final JTextField espera;
    private final Gasolinera gasolinera;

    /**
     * Constructor de la clase
     *
     * @param p_v
     * @param p_s
     * @param p_e
     * @param ga
     */
    public EstadoGasolinera(ArrayList<JTextField> p_v, ArrayList<JTextField> p_s,
            JTextField p_e, Gasolinera ga) {
        vehiculos = p_v;
        surtidores = p_s;
        espera = p_e;
        gasolinera = ga;
    }

    /**
     *
     * @return
     */
    public boolean isAlive() {
        return gasolinera.isAlive();
    }

    /**
     *
     * @return
     */
    public ArrayList<JTextField> getVehiculos() {
        return vehiculos;
    }

    /**
     *
     * @return
     */
    public ArrayList<JTextField> getSurtidores() {
        return surtidores;
    }

    /**
     *
     * @return
     */
    public JTextField getEspera() {
        return espera;
    }

}
