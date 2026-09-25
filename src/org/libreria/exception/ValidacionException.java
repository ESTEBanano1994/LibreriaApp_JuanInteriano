package org.libreria.exception;

/**
 * Excepción personalizada utilizada para manejar errores de validación
 * de datos ingresados en la aplicación.
 *
 * @author Esteban Interiano
 * @version 1.0.0
 */
public class ValidacionException extends Exception {

    /**
     * Crea una nueva excepción de validación con el mensaje especificado.
     *
     * @param mensaje mensaje que describe el error de validación.
     */
    public ValidacionException(String mensaje) {
        super(mensaje);
    }

    /**
     * Valida que un campo de texto no sea nulo ni esté vacío.
     *
     * @param valor valor que se desea validar.
     * @param nombreCampo nombre del campo que se está validando.
     * @throws ValidacionException si el valor es nulo o está vacío.
     */
    public static void validarNoVacio(String valor, String nombreCampo)
            throws ValidacionException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionException(
                    "El campo " + nombreCampo + " no puede estar vacío.");
        }
    }

    /**
     * Valida que dos valores sean iguales.
     *
     * @param a primer valor que se desea comparar.
     * @param b segundo valor que se desea comparar.
     * @param mensaje mensaje que se mostrará si los valores no coinciden.
     * @throws ValidacionException si los valores son diferentes.
     */
    public static void validarCoinciden(String a, String b, String mensaje)
            throws ValidacionException {
        if (!a.equals(b)) {
            throw new ValidacionException(mensaje);
        }
    }

    /**
     * Valida que un texto tenga como mínimo una cantidad determinada de caracteres.
     *
     * @param valor texto que se desea validar.
     * @param min cantidad mínima de caracteres permitidos.
     * @param mensaje mensaje que se mostrará si no se cumple la longitud mínima.
     * @throws ValidacionException si el texto tiene menos caracteres de los requeridos.
     */
    public static void validarLongitudMinima(String valor, int min, String mensaje)
            throws ValidacionException {
        if (valor.length() < min) {
            throw new ValidacionException(mensaje);
        }
    }

    /**
     * Valida que un objeto no sea nulo.
     *
     * @param obj objeto que se desea validar.
     * @param mensaje mensaje que se mostrará si el objeto es nulo.
     * @throws ValidacionException si el objeto es nulo.
     */
    public static void validarNoNulo(Object obj, String mensaje)
            throws ValidacionException {
        if (obj == null) {
            throw new ValidacionException(mensaje);
        }
    }

    /**
     * Valida que un texto represente un número entero válido.
     *
     * @param valor valor que se desea validar.
     * @param nombreCampo nombre del campo que se está validando.
     * @throws ValidacionException si el valor no representa un número válido.
     */
    public static void validarNumero(String valor, String nombreCampo)
            throws ValidacionException {
        try {
            Long.parseLong(valor.trim());
        } catch (NumberFormatException e) {
            throw new ValidacionException(
                    "El campo " + nombreCampo + " debe ser un número válido.");
        }
    }

    /**
     * Valida que un texto tenga exactamente una cantidad determinada de caracteres.
     *
     * @param valor texto que se desea validar.
     * @param longitud cantidad exacta de caracteres permitidos.
     * @param mensaje mensaje que se mostrará si no se cumple la longitud.
     * @throws ValidacionException si el texto no tiene la longitud especificada.
     */
    public static void validarLongitudExacta(String valor, int longitud, String mensaje)
            throws ValidacionException {
        if (valor.length() != longitud) {
            throw new ValidacionException(mensaje);
        }
    }

    /**
     * Valida que un texto tenga un formato de correo electrónico válido.
     *
     * @param valor correo electrónico que se desea validar.
     * @param mensaje mensaje que se mostrará si el formato no es válido.
     * @throws ValidacionException si el correo electrónico no tiene un formato válido.
     */
    public static void validarFormatoEmail(String valor, String mensaje)
            throws ValidacionException {
        if (!valor.matches("[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+")) {
            throw new ValidacionException(mensaje);
        }
    }

    /**
     * Valida que un texto represente un número decimal válido.
     *
     * @param valor valor que se desea validar.
     * @param nombreCampo nombre del campo que se está validando.
     * @throws ValidacionException si el valor no representa un número válido.
     */
    public static void validarDecimal(String valor, String nombreCampo)
            throws ValidacionException {
        try {
            Double.parseDouble(valor.trim());
        } catch (NumberFormatException e) {
            throw new ValidacionException(
                    "El campo " + nombreCampo + " debe ser un número válido.");
        }
    }

    /**
     * Valida que una fecha tenga el formato año-mes-día.
     *
     * @param valor fecha que se desea validar.
     * @param mensaje mensaje que se mostrará si el formato no es válido.
     * @throws ValidacionException si la fecha no cumple con el formato esperado.
     */
    public static void validarFormatoFecha(String valor, String mensaje)
            throws ValidacionException {
        if (!valor.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new ValidacionException(mensaje);
        }
    }

    /**
     * Valida que un texto represente un número entero positivo.
     *
     * @param valor valor que se desea validar.
     * @param nombreCampo nombre del campo que se está validando.
     * @throws ValidacionException si el valor no es un número válido o no es mayor que cero.
     */
    public static void validarPositivo(String valor, String nombreCampo)
            throws ValidacionException {
        validarNumero(valor, nombreCampo);
        if (Long.parseLong(valor.trim()) <= 0) {
            throw new ValidacionException(
                    "El campo " + nombreCampo + " debe ser un número mayor que cero.");
        }
    }
}