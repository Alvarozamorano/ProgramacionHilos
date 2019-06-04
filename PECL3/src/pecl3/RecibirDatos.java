package pecl3;

import static java.lang.System.exit;
import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author Álvaro Zamorano
 * @author Gabriel López
 */
public class RecibirDatos extends Thread {

    //Atributos de la clase
    private ArrayList<String> estado;
    private boolean alive;
    private final JTextField jTextField1;
    private final ArrayList<JTextField> surtidoresjt;
    private final ArrayList<JTextField> operariosjt;
    private final JFrame frame;

    /**
     * Constructor de la clase
     *
     * @param jt
     * @param s
     * @param o
     * @param jf
     */
    public RecibirDatos(JTextField jt, ArrayList<JTextField> s,
            ArrayList<JTextField> o, JFrame jf) {
        jTextField1 = jt;
        surtidoresjt = s;
        operariosjt = o;
        frame = jf;
    }

    @Override
    public void run() {
        do {
            try {
                InterfaceComunica obj;
                obj = (InterfaceComunica) Naming.lookup("//127.0.0.1/ObGasolinera");
                estado = obj.getTextos();
                alive = obj.isAlive();

                jTextField1.setText(estado.get(0));

                for (int i = 0; i < 8; i++) {
                    operariosjt.get(i).setText(estado.get(i + 1));
                }
                for (int i = 0; i < 8; i++) {
                    surtidoresjt.get(i).setText(estado.get(i + 9));
                }

                sleep(1000); //Se actualiza cada segundo
            } catch (NotBoundException | MalformedURLException | RemoteException | InterruptedException ex) {
                Logger.getLogger(RecibirDatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (alive);
        frame.dispose();
        exit(0);
    }
}
