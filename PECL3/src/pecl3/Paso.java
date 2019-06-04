package pecl3;

import java.io.Serializable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Álvaro Zamorano
 * @author Gabriel López
 */
public class Paso implements Serializable {

    //Atributos de la clase
    private boolean cerrado;
    private final Lock cerrojo = new ReentrantLock();
    private final Condition parar = cerrojo.newCondition();

    /**
     * Constructor de la clase
     */
    public Paso() {
        cerrado = false;
    }

    /**
     * Método para comprobar si el paso está abierto. En caso de entrar en el
     * bucle (cerrado = true) el hilo se quedará bloqueado
     */
    public void mirar() {
        cerrojo.lock();
        try {
            while (cerrado) {
                try {
                    parar.await();
                } catch (InterruptedException ie) {
                }
            }
        } finally {
            cerrojo.unlock();
        }

    }

    /**
     * Cambiar la condición del cerrojo para que así puedan pasar otros hilos
     */
    public void abrir() {
        cerrojo.lock();

        try {
            cerrado = false;
            parar.signalAll();
        } finally {
            cerrojo.unlock();
        }
    }

    /**
     * Cambiar la condición del cerrojo para que así quede cerrado el paso
     */
    public void cerrar() {
        cerrojo.lock();
        try {
            cerrado = true;
            parar.signalAll();
        } finally {
            cerrojo.unlock();
        }
    }
}
