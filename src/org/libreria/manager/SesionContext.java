package org.libreria.manager;

import org.libreria.model.Usuario;

/**
 * Gestiona la sesión del usuario actual dentro de la aplicación.
 * Utiliza el patrón Singleton para mantener una única instancia
 * de la sesión durante la ejecución del programa.
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.Usuario
 */
public class SesionContext {

    private static SesionContext instancia;
    private Usuario usuarioActual;

    /**
     * Constructor privado que evita la creación directa de instancias.
     */
    private SesionContext() {
    }

    /**
     * Obtiene la única instancia de la sesión.
     *
     * @return instancia actual de SesionContext.
     */
    public static synchronized SesionContext getInstancia() {
        if (instancia == null) {
            instancia = new SesionContext();
        }
        return instancia;
    }

    /**
     * Obtiene el usuario que se encuentra actualmente autenticado.
     *
     * @return usuario actualmente autenticado.
     */
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Establece el usuario que se encuentra actualmente autenticado.
     *
     * @param usuario usuario que se establecerá como usuario actual.
     */
    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    /**
     * Cierra la sesión del usuario actual.
     * Establece el usuario actual como nulo.
     */
    public void cerrarSesion() {
        this.usuarioActual = null;
    }
}
