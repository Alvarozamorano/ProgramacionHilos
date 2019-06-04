package pecl3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import static java.lang.System.exit;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/**
 *
 * @author Álvaro Zamorano
 * @author Gabriel López
 */
public class Gasolinera implements Serializable {

    //Atributos de la clase
    private final ListaThreadDentro colaEsperaDentro;
    private final ListaThreadFuera colaEsperaFuera;
    private final Semaphore semaforo;
    private int contador = 0;
    private final Principal ventana;
    private final Paso paso;
    private final int n_coches = 200;
    private final int n_operarios = 3;
    private boolean alive = true;

    /**
     * Constructor de la clase
     *
     * @param p_n_surtidores
     * @param tfEsperan
     * @param v
     * @param surtidores
     * @param p_operarios
     * @param p_v
     * @param p_paso
     */
    public Gasolinera(int p_n_surtidores, JTextField tfEsperan, ArrayList<JTextField> v,
            ArrayList<Surtidor> surtidores, ArrayList<JTextField> p_operarios, Principal p_v,
            Paso p_paso) {
        semaforo = new Semaphore(p_n_surtidores, true);
        colaEsperaFuera = new ListaThreadFuera(tfEsperan);
        colaEsperaDentro = new ListaThreadDentro(surtidores, v, p_operarios, this);
        ventana = p_v;
        paso = p_paso;
        generarDocumento(); //Llamada al método para crar el documento con la 
        //evolución de la gasolinera
    }

    /**
     *
     * @return
     */
    public ListaThreadDentro getColaEsperaDentro() {
        return colaEsperaDentro;
    }

    /**
     *
     * @return
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     *
     * @return
     */
    public Paso getPaso() {
        return paso;
    }

    /**
     *
     * @return
     */
    public int getN_coches() {
        return n_coches;
    }

    /**
     *
     * @return
     */
    public int getN_operarios() {
        return n_operarios;
    }

    /**
     *
     * @param alive
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Método para entrar a la cola de fuera de la gasolinera, y en caso de
     * haber algún surtidor libre (el semaforo tiene permisos libres), se entra
     * a la gasolinera
     *
     * @param c
     */
    public void entrar(Coche c) {
        paso.mirar();
        colaEsperaFuera.meter(c);
        Calendar calendario = new GregorianCalendar();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minutos = calendario.get(Calendar.MINUTE);
        int segundos = calendario.get(Calendar.SECOND);
        System.out.println(hora + ":" + minutos + ":" + segundos + " -- " + "Llega " + c.toString() + " a la gasolinera");
        try {
            semaforo.acquire();
        } catch (InterruptedException e) {
        }
        paso.mirar();
        colaEsperaFuera.sacar(c);
        paso.mirar();
        colaEsperaDentro.meter(c, colaEsperaDentro.dondeEntrar(c));
    }

    /**
     * Una vez repostado, se sale de la gasolinera y se libera un permiso del
     * semáforo. Se tiene en cuenta los coches que han salido para terminar con
     * la simulación
     *
     * @param c
     */
    public void salir(Coche c) {
        paso.mirar();
        semaforo.release();
        Calendar calendario = new GregorianCalendar();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minutos = calendario.get(Calendar.MINUTE);
        int segundos = calendario.get(Calendar.SECOND);
        System.out.println(hora + ":" + minutos + ":" + segundos + " -- " + "Se va el " + c.toString() + " de la gasolinera");
        contador++;
        paso.mirar();
        if (contador == n_coches) {
            alive = false;
            try {
                sleep(1500); //Fin gasolinera
            } catch (InterruptedException ex) {
                Logger.getLogger(Gasolinera.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("");
            System.out.println("-- FIN SIMULACION GASOLINERA --");
            ventana.dispose();
            exit(0);
        }

    }

    /**
     * Método para capturar la salida y escribirlo en el documento
     */
    private void generarDocumento() {
        File ev = new File("./evolucionGasolinera.txt");
        FileOutputStream salida = null;
        try {
            salida = new FileOutputStream(ev);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Gasolinera.class.getName()).log(Level.SEVERE, null, ex);
        }
        PrintStream print = new PrintStream(salida);
        System.setOut(print);
        System.out.println("-- REGISTRO ACCIONES GASOLINERA --");
        System.out.println("");
    }

}
