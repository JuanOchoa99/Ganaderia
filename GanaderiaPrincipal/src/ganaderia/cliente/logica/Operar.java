/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ganaderia.cliente.logica;

import ganaderia.controladores.UsuarioJpaController;
import ganaderia.entidades.Agregar;
import ganaderia.entidades.Egresos;
import ganaderia.entidades.Finca;
import ganaderia.entidades.Ingresos;
import ganaderia.entidades.Liquidar;
import ganaderia.entidades.Usuario;
import javax.persistence.Persistence;

/**
 *
 * @author JJ
 */

public class Operar {

private Agregar a;
private Egresos e;
private Finca f;
private Ingresos i;
private Liquidar l;
private Usuario currentUser;

    public boolean iniciarSesion(String user, String pass) {
        UsuarioJpaController ujc = new UsuarioJpaController(Persistence.createEntityManagerFactory("GanaderiaPrincipalPU"));
        Usuario u = ujc.findUsuarioByLogin(user);
        if (u != null) {
            if (u.getLogin().equals(user) && u.getPassword().equals(pass)) {
                currentUser = u;
                return true;
            } else {
                currentUser = null;
                return false;
            }
        } else {
            currentUser = null;
            return false;
        }
    }
              public void cerrarSesion() {
        currentUser = null;
    }
}
