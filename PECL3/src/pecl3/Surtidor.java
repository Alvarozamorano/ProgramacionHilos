package pecl3;

import java.io.Serializable;

/**
 *
 * @author Álvaro Zamorano
 * @author Gabriel López
 */
public class Surtidor implements Serializable {

    //Atributos de la clase
    private final int numero;
    private boolean ocupado;
    private Coche vehiculo;

    /**
     * Constructor de la clase
     *
     * @param numero
     * @param ocupado
     * @param vehiculo
     */
    public Surtidor(int numero, boolean ocupado, Coche vehiculo) {
        this.numero = numero;
        this.ocupado = ocupado;
        this.vehiculo = vehiculo;
    }

    /**
     *
     * @return
     */
    public int getNumero() {
        return numero;
    }

    /**
     *
     * @return
     */
    public boolean isOcupado() {
        return ocupado;
    }

    /**
     *
     * @param ocupado
     */
    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    /**
     *
     * @return
     */
    public Coche getVehiculo() {
        return vehiculo;
    }

    /**
     *
     * @param vehiculo
     */
    public void setVehiculo(Coche vehiculo) {
        this.vehiculo = vehiculo;
    }

}
