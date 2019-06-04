package pecl3;

import java.io.Serializable;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/**
 *
 * @author Álvaro Zamorano
 * @author Gabriel López
 */
public class ListaThreadDentro implements Serializable {

    //Atributos de la clase
    private final ArrayList<Surtidor> lista;
    private final ArrayList<Integer> nSurtidor;
    private final ArrayList<JTextField> vehiculos, operarios;
    private final Gasolinera gasolinera;
    private final Lock cerrojo = new ReentrantLock();
    private final Condition vacia = cerrojo.newCondition();
    private int numOperariosAten;

    /**
     * Constructor de la clase
     *
     * @param surtidores
     * @param JTextV
     * @param p_otext
     * @param p_g
     */
    public ListaThreadDentro(ArrayList<Surtidor> surtidores, ArrayList<JTextField> JTextV,
            ArrayList<JTextField> p_otext, Gasolinera p_g) {
        numOperariosAten = 0;
        lista = surtidores;
        vehiculos = JTextV;
        operarios = p_otext;
        gasolinera = p_g;
        nSurtidor = new ArrayList<>();
    }

    /**
     * Método synchronized para buscar un surtidor libre
     *
     * @param c
     * @return
     */
    public synchronized int dondeEntrar(Coche c) {
        int i = 0;
        boolean sitioEncontrado = false;
        while (!sitioEncontrado && i < lista.size()) {
            if (!lista.get(i).isOcupado()) {
                sitioEncontrado = true;
            } else {
                i++;
            }
        }

        nSurtidor.add(i);
        return i;
    }

    /**
     * Método para introducir el coche en el surtidor pasado como parámetro
     *
     * @param vehiculo
     * @param i
     */
    public void meter(Coche vehiculo, int i) {
        cerrojo.lock();
        Surtidor s = lista.get(i);
        s.setVehiculo(vehiculo);
        s.setOcupado(true);
        vehiculo.setSurtidor(s);
        imprimir("Entra ", vehiculo);
        vehiculos.get(i).setText(s.getVehiculo().toString());
        try {
            vacia.signal();
        } finally {
            cerrojo.unlock();
        }
    }

    /**
     * Método con un cerrojo con condición para saber a que surtidor debe ir el
     * operario y así repostar al coche que está esperando
     *
     * @return
     */
    public int dondeSacar() {
        cerrojo.lock();
        int i = 0;
        try {
            while (nSurtidor.isEmpty() || numOperariosAten == gasolinera.getN_operarios()) {
                vacia.await();
            }

            i = nSurtidor.get(0);
            nSurtidor.remove(0);

        } catch (InterruptedException ex) {
            Logger.getLogger(ListaThreadDentro.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrojo.unlock();
        }
        return i;
    }

    /**
     * Método para simular el repostaje y sacar el coche del surtidor
     *
     * @param op
     * @param i
     * @throws InterruptedException
     */
    public void sacar(Operario op, int i) throws InterruptedException {
        numOperariosAten++;
        Coche c = lista.get(i).getVehiculo();
        c.setAtendido(true);
        operarios.get(i).setText(op.toString());
        Calendar calendario = new GregorianCalendar();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minutos = calendario.get(Calendar.MINUTE);
        int segundos = calendario.get(Calendar.SECOND);
        int rnd = (int) (4000 + Math.random() * 4000);
        System.out.println(hora + ":" + minutos + ":" + segundos + " -- " + "El " + op.toString() + " reposta al " + c.toString()
                + " en el surtidor " + c.getSurtidor().getNumero() + ", tarda en repostar " + rnd / 1000 + " segundos");

        //Tiempo de repostaje
        try {
            sleep(rnd);
        } catch (InterruptedException e) {
        }

        lista.get(i).setOcupado(false);
        lista.get(i).setVehiculo(null);
        imprimir("Sale ", c);
        gasolinera.salir(c);
        vehiculos.get(i).setText("");
        operarios.get(i).setText("");
        numOperariosAten--;
    }

    /**
     * Método para imprimir cuando un coche entra o sale de un surtidor
     *
     * @param c
     * @param accion
     */
    public void imprimir(String accion, Coche c) {
        Calendar calendario = new GregorianCalendar();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minutos = calendario.get(Calendar.MINUTE);
        int segundos = calendario.get(Calendar.SECOND);
        System.out.println(hora + ":" + minutos + ":" + segundos + " -- " + accion + c.toString() + " surtidor " + c.getSurtidor().getNumero());
    }
}
