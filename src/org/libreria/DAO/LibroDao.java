package org.libreria.DAO;

/**
 * Interfaz DAO encargada de definir las operaciones de acceso a datos :)
 * para la entidad {@link Libro}.
 * <p>
 * Hereda las operaciones CRUD definidas en {@link Crud}, utilizando
 * {@code String} como tipo de identificador de la entidad.
 *
 * @author Juan Esteban Interiano Riera
 * @version 1.0.0
 */
import org.libreria.model.Libro;

public interface LibroDAO extends Crud<Libro, String>{
    
}