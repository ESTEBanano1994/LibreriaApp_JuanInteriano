package org.libreria.exception;

/**
 * Excepción personalizada utilizada para representar errores ocurridos
 * en la capa de acceso a datos de la aplicación.
 *
 * Se utiliza cuando una operación contra la base de datos, generalmente
 * mediante procedimientos almacenados, falla debido a problemas como
 * errores de conexión, sentencias SQL inválidas u otros errores relacionados
 * con el acceso a los datos.
 *
 * @author Esteban Interiano
 * @version 1.0.0
 */
public class DaoException extends RuntimeException {

    /**
     * Crea una nueva excepción de acceso a datos con el mensaje especificado.
     *
     * @param mensaje mensaje que describe el error ocurrido.
     */
    public DaoException(String mensaje) {
        super(mensaje);
    }

    /**
     * Crea una nueva excepción de acceso a datos con un mensaje y la causa
     * original del error.
     *
     * @param mensaje mensaje que describe el error ocurrido.
     * @param causa excepción original que provocó el error.
     */
    public DaoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}