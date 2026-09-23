package org.libreria.model;

/**
 * Representa los datos del cliente que esta comprando
 * 
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.Cliente
 */
public class Cliente {
  
    private long cui;
    private String nombreCliente;
    private String apellidoCliente;
    private String correoElectronico;

    /**
     * Constructor vacío que permite crear una instancia de {@link AutorLibro}
     * sin establecer valores iniciales.
     */
    public Cliente() {
    }

    /**
     * construye una instancia de {@link Libro} con los datos especificados
     * @param cui identificador del cliente
     * @param nombreCliente nombre del cliente
     * @param apellidoCliente apellido del cliente
     * @param correoElectronico correo electronico del cliente
     */
    public Cliente(long cui, String nombreCliente, String apellidoCliente, String correoElectronico) {
        this.cui = cui;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.correoElectronico = correoElectronico;
    }

    /**
     * obtiene el cui personal del cliente
     * @return devuelve el cui del cliente
     */
    public long getCui() {
        return cui;
    }

    /**
     * establece el cui
     * @param cui codigo del cliente
     */
    public void setCui(long cui) {
        this.cui = cui;
    }

    /**
     * obtiene el nombre del cliente
     * @return devuelve el nombre del cliente
     */
    public String getNombreCliente() {
        return nombreCliente;
    }

    /**
     * establece el nombre del cliente
     * @param nombreCliente nombre de quien compra
     */
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    /**
     * Obtiene el apellido del cliente
     * @return devuelve el apellido del cliente
     */
    public String getApellidoCliente() {
        return apellidoCliente;
    }

    /**
     * establece el apellido del cliente
     * @param apellidoCliente apellido del cliente
     */
    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    /**
     * Obtiene el correo personal del cliente
     * @return deuvelve el correo electronico del cliente
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * establece el correo personal del cliente
     * @param correoElectronico correo personal del cliente
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * obtiene los datos del cliente
     * @return devuelve el nombre mas el apellido del cliente
     */
    @Override
    public String toString() {
        return nombreCliente + " " + apellidoCliente;
    }
}
