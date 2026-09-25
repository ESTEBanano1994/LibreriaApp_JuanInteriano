
package org.libreria.DAO;

/**
 * Representa la separacion del codigo de Usuario con su parte en la base de datos SQL
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.DAO.Usuario
 */
import java.util.ArrayList;
import org.libreria.model.Usuario;

/**
 *
 * @author Esteban Interiano
 */
public interface UsuarioDAO {

    /**
     * obtiene los datos necesarios para el inicio de sesion
     * @param usernarme nombre de usuario 
     * @param passwordHash contraseña de la cuenta
     * @return devuelve los datos necesarios para poder iniciar la cuenta
     */
    public Usuario iniciarSesion(String usernarme, String passwordHash);

    /**
     * obtiene el nombre del usuario a ingresar
     * @param usuario identificador por nombre del usuario
     * @return devuelve el nombre de la cuenta
     */
    public boolean crearUsuario(Usuario usuario);

    /**
     * obtiene un valor verdadero o falso para poder verificar el cambio de un usuario
     * @param usuario nombre de la cuenta
     * @return devuelve la actualizacion del usuario
     */
    public boolean actualizarUsuario(Usuario usuario);

    /**
     * obtiene el identificador unico de la cuenta junto con la contraseña para hacer un cambio
     * @param idUsuario identificador unico del user
     * @param passwordHash contraseña de la cuenta
     * @return devuelve los datos necesarios para validar el cambio de contraseña
     */
    public boolean cambiarPassword(int idUsuario, String passwordHash);

    /**
     *
     * obtiene la validacion para poder desactivar un usuario
     * @param idUsuario identificador unico
     * @return devuelve el valor verdadero o falso para poder desactivar el usuario
     */
    public boolean desactivarUsuario(int idUsuario);

    /**
     * obtiene el identificador del usuario para eliminarlo
     * @param idUsuario identificador unico
     * @return devuelve el valor verdadero o falso para poder eliminar la cuenta
     */
    public boolean eliminarUsuario(int idUsuario);

    /**
     * obtiene la lista de los usuarios
     * @return devuelve un listado de usuario
     */
    public ArrayList<Usuario> listarTodosUsuarios();

    /**
     * obtiene la cuenta de un usuario por su id
     * @param idUsuario identificador unico del user
     * @return devuelve el usuario en base a su id
     */
    public Usuario obtenerUsuarioPorId(int idUsuario);
}