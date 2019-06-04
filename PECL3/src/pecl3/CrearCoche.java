package pecl3;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Álvaro Zamorano
 * @author Gabriel López
 */
public class CrearCoche extends Thread implements Serializable {

    //Atributos de la clase
    private final Gasolinera ga;
    private final Paso paso;

    /**
     *
     * @param p_g
     */
    public CrearCoche(Gasolinera p_g) {
        ga = p_g;
        paso = ga.getPaso();
    }

    @Override
    public void run() {
        for (int k = 1; k <= ga.getN_coches(); k++) {
            paso.mirar();
            Coche c = new Coche(k, ga);
            c.start();

            try {
                sleep(1000); //Se crean coches cada segundo
            } catch (InterruptedException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
