/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ganaderiaprincipal;

import ganaderia.cliente.logica.Operar;
import ganaderia.cliente.vista.Finca;
import ganaderia.cliente.vista.InicioSesion;
import ganaderia.cliente.vista.PaginaPrincipal;
import ganaderia.cliente.vista.Ventana;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.JFrame;

/**
 *
 * @author JJ
 */
public class GanaderiaPrincipal extends JFrame {

    private Operar operar;
    private Ventana ventana;
    private InicioSesion IS;
    private PaginaPrincipal PP;
    private Finca finca;

    public GanaderiaPrincipal() {
        operar = new Operar();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Ganaderia");
        this.setResizable(false);
        this.setVisible(false);
        //inicia caracteristicas del Frame
        this.irAInicioDeSesion();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        GanaderiaPrincipal principal = new GanaderiaPrincipal();
    }

    public boolean validar(String usuario, String pass) {
         System.out.println("funciono");
        return operar.iniciarSesion(usuario, pass);
        
    }

    protected void irAInicioDeSesion() {
        operar.cerrarSesion();
        iniciarVentana();
        IS = new InicioSesion(this);
        ventana = new Ventana(this, IS, "Ventana", false, false, DO_NOTHING_ON_CLOSE);
    }

    private void iniciarVentana() {
        if (ventana != null) {
            cerrarVentana();
        }
        setVisible(false);
    }

    protected void cerrarVentana() {
        ventana.setVisible(false);
        ventana = null;
    }

    public void irAPaginaPrincipal() {
        cerrarVentana();
        iniciarMarco();
        //agrega el panelinicial
        PaginaPrincipal pp = new PaginaPrincipal(this);
        pp.setVisible(true);
        add(pp);
        //se ajusta el frame
        pack();
        //centra la aplicacion 
        setLocationRelativeTo(null);
    }

    protected void iniciarMarco() {
        //inicia los componentes del frame
        this.quitarPanelesDelMarco();        
        this.setVisible(true);
    }

    public void irAFinca() {
       
        iniciarMarco();
        //agrega el panelinicial
        finca = new Finca(this);
        finca.setVisible(true);
        add(finca);
        //se ajusta el frame
        pack();
        //centra la aplicacion 
        setLocationRelativeTo(null);
    
    }
    /**
     * Metodo para quitar el panel actual del marco
     */
        private void quitarPanelesDelMarco() {
        setVisible(false);
        if (PP!=null) {
            remove(PP);
            PP=null;
        }
       
        //hacer lo anterior parra cada panel que este en el marco
        
    
    }
}
