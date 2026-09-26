package org.libreria.DAO;

/**
 * Representa las operaciones realizadas por la entidad y su identificador unico
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.DAO.Crud
 */
import java.util.ArrayList;

/**
 * Representa la relación entre un autor y un libro dentro del sistema.
 * @author Esteban Interiano
 * @param <T> entidad
 * @param <K> id
 */
public interface Crud<T, K> {

    /**
     * Obtiene los datos de creacion de entidad
     * @param entidad objeto excistente
     * @return devuelve entidad
     */
    boolean crear(T entidad);

    /**
     * obtiene la actualizacion de la entidad
     * @param entidad objeto excistente
     * @return devuelve la actualizacion de la entidad
     */
    boolean actualizar(T entidad);

    /**
     * elimina el identificador 
     * @param id identificador unico
     * @return devuelve la eliminacion del identificador unico
     */
    boolean eliminar(K id);

    /**
     * realiza una busqueda del identificar unico
     * @param id identificador unico
     * @return devuelve el identificador unico buscado
     */
    T buscarPorId(K id);

    /**
     * obtiene una lista de las entidades
     * @return devuelve la lista de todas las entidades excistentes
     */
    ArrayList<T> listarTodos();
}
    

