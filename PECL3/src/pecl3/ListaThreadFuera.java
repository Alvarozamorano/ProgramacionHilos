package pecl3;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JTextField;

/**
 *
 * @author Álvaro Zamorano
 * @author Gabriel López
 */
public class ListaThreadFuera implements Serializable {

    //Atributos de la clase
    private final ArrayList<Coche> lista;
    private final JTextField tf;

    /**
     * Constructor de la clase
     *
     * @param p_tf
     */
    public ListaThreadFuera(JTextField p_tf) {
        lista = new ArrayList<>();
        tf = p_tf;
    }

    /**
     * Método para meter el coche en la cola de espera
     *
     * @param c
     */
    public synchronized void meter(Coche c) {
        lista.add(c);
        imprimir();
    }

    /**
     * Método para sacar el coche de la cola de espera
     *
     * @param c
     */
    public synchronized void sacar(Coche c) {
        lista.remove(c);
        imprimir();
    }

    /**
     * Método para imprimir el contenido de la cola de espera
     */
    public void imprimir() {
        String contenido = "";
        for (int i = 0; i < lista.size(); i++) {
            contenido = contenido + lista.get(i).toString() + " ";
        }
        tf.setText(contenido);
    }
}
