package org.libreria.DAO;

/**
 * Representa la separacion de la logica de negocio con la logica del acceso a la base de datos
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.DAO.Dao
 * @param <T> entidad
 * @param <K> identificador unico
 */
 public interface Dao<T, K> extends Crud<T, K> {
}


