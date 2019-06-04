package pecl3;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Álvaro Zamorano
 * @author Gabriel López
 */
public class Operario extends Thread implements Serializable {

    //Atributos de la clase
    private final int id;
    private final Gasolinera gasolinera;
    private final Paso paso;

    /**
     * Constructor de la clase
     *
     * @param p_id
     * @param p_g
     */
    public Operario(int p_id, Gasolinera p_g) {
        id = p_id;
        gasolinera = p_g;
        paso = gasolinera.getPaso();
    }

    @Override
    public String toString() {
        return "Operario " + id;
    }

    @Override
    public void run() {
        while (true) {
            try {
                paso.mirar();
                //Buscar el surtidor en el que repostar
                int pos = gasolinera.getColaEsperaDentro().dondeSacar();
                paso.mirar();
                if (pos < 8) {
                    //Repostar
                    gasolinera.getColaEsperaDentro().sacar(this, pos);
                    paso.mirar();
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(Operario.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
