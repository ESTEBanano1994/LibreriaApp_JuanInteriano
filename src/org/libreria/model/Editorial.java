package org.libreria.model;

/**
 * Representa los datos de la editorial
 * 
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.DetalleVenta
 */
public class Editorial {
    private String nit;
    private String nombreEditorial;
    private String telefonoEditorial;
    private String direccionEditoria;

    /**
     *Constructor vacío que permite crear una instancia de {@link AutorLibro}
     * sin establecer valores iniciales.
     */
    public Editorial() {
    }

    /**
     *construye una instancia de {@link Libro} con los datos especificados
     * @param nit numero identificador de la editorial
     * @param nombreEditorial nombre de la editorial
     * @param telefonoEditorial telefono de la editorial
     * @param direccionEditoria direccion de la editorial
     */
    public Editorial(String nit, String nombreEditorial, String telefonoEditorial, String direccionEditoria) {
        this.nit = nit;
        this.nombreEditorial = nombreEditorial;
        this.telefonoEditorial = telefonoEditorial;
        this.direccionEditoria = direccionEditoria;
    }

    /**
     *  obtiene el nit de la editorial
     * @return devuelve el nit de la editorial
     */
    public String getNit() {
        return nit;
    }

    /**
     * establece el numero identificador de la editorial
     * @param nit nit de la editorial
     */
    public void setNit(String nit) {
        this.nit = nit;
    }

    /**
     *  Obtiene el nombre de la editorial
     * @return devuelve el nombre de la editorial
     */
    public String getNombreEditorial() {
        return nombreEditorial;
    }

    /**
     * establece el nombre de al editorial
     * @param nombreEditorial nombre de la editorial
     */
    public void setNombreEditorial(String nombreEditorial) {
        this.nombreEditorial = nombreEditorial;
    }

    /**
     * obtiene el telefono de la editorial
     * @return devuelve el numero de telefono de la editorial
     */
    public String getTelefonoEditorial() {
        return telefonoEditorial;
    }

    /**
     * establece el telefono de la editorial
     * @param telefonoEditorial numero de la editorial
     */
    public void setTelefonoEditorial(String telefonoEditorial) {
        this.telefonoEditorial = telefonoEditorial;
    }

    /**
     * obtiene la direccion de la editorial
     * @return devuelve la direccion de la editorial
     */
    public String getDireccionEditoria() {
        return direccionEditoria;
    }

    /**
     * estabñece la direccion de la editorial 
     * @param direccionEditorial direccion de la editorial
     */
    public void setDireccionEditoria(String direccionEditorial) {
        this.direccionEditoria = direccionEditorial;
    }

    /**
     * obtiene los datos de la editorial
     * @return devuelve los datos de la editorial
     */
    @Override
    public String toString() {
        return nombreEditorial;
    }
}