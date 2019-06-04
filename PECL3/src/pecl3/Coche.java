package pecl3;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Álvaro Zamorano
 * @author Gabriel López
 */
public class Coche extends Thread implements Serializable {

    //Atributos de la clase
    private final int numero;
    private final Gasolinera gasolinera;
    private boolean atendido;
    private Surtidor surtidor;
    private final Paso paso;

    /**
     *
     * @param p_n
     * @param p_g
     */
    public Coche(int p_n, Gasolinera p_g) {
        numero = p_n;
        gasolinera = p_g;
        surtidor = null;
        atendido = false;
        paso = gasolinera.getPaso();
    }

    @Override
    public String toString() {
        return "Coche " + numero + " ";
    }

    /**
     *
     * @return
     */
    public Surtidor getSurtidor() {
        return surtidor;
    }

    /**
     *
     * @return
     */
    public boolean isAtendido() {
        return atendido;
    }

    /**
     *
     * @param surtidor
     */
    public void setSurtidor(Surtidor surtidor) {
        this.surtidor = surtidor;
    }

    /**
     *
     * @param atendido
     */
    public void setAtendido(boolean atendido) {
        this.atendido = atendido;
    }

    @Override
    public void run() {
        paso.mirar();
        try {
            int rnd = (int) (5500 * Math.random() + 500);
            Calendar calendario = new GregorianCalendar();
            int hora = calendario.get(Calendar.HOUR_OF_DAY);
            int minutos = calendario.get(Calendar.MINUTE);
            int segundos = calendario.get(Calendar.SECOND);
            System.out.println(hora + ":" + minutos + ":" + segundos + " -- " + "El " + this.toString() + " tarda en llegar a la gasolinera " + rnd / 1000 + " segundos");
            sleep(rnd); //Tiempo de llegada a la gasolinera
        } catch (InterruptedException e) {
        }
        paso.mirar();

        gasolinera.entrar(this); //Entra si hay hueco; y sino espera en la cola
    }
}
