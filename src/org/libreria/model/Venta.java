package org.libreria.model;

/**
 * Representa la venta y todos los datos que almacena esta misma
 * 
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.Venta
 */
public class Venta {
    private int noVenta;
    private String fechaVenta;
    private double totalVenta;
    private long cuiCliente;
    private int idUsuario;

    /**
     *Constructor vacío que permite crear una instancia de {@link AutorLibro}
     * sin establecer valores iniciales.
     */
    public Venta() {
    }

    /**
     *construye una instancia de {@link Venta} con los datos especificados
     * 
     * @param noVenta numero de la venta
     * @param fechaVenta fecha de la venta
     * @param totalVenta total de la venta
     * @param cuiCliente cui del cliente que compró
     * @param idUsuario id del usuario
     */
    public Venta(int noVenta, String fechaVenta, double totalVenta, long cuiCliente, int idUsuario) {
        this.noVenta = noVenta;
        this.fechaVenta = fechaVenta;
        this.totalVenta = totalVenta;
        this.cuiCliente = cuiCliente;
        this.idUsuario = idUsuario;
    }

    /**
     * obtiene el numero de la venta 
     * @return devuelve el numero de venta realizada
     */
    public int getNoVenta() {
        return noVenta;
    }

    /**
     * establece el numero de la venta
     * @param noVenta devuelve el numero de venta realizada
     */
    public void setNoVenta(int noVenta) {
        this.noVenta = noVenta;
    }

    /**
     * obtiene la fecha en la que se hizo la venta
     * @return devuelve la fecha de la venta
     */
    public String getFechaVenta() {
        return fechaVenta;
    }

    /**
     * establece cuando se hizo la venta
     * @param fechaVenta fecha de la venta realizada
     */
    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    /**
     * obtiene el total de la venta
     * @return devuelve el total de la venta
     */
    public double getTotalVenta() {
        return totalVenta;
    }

    /**
     * establece el total monetario para la venta
     * @param totalVenta total de la venta
     */
    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    /**
     * obtiene el cui del cliente que compra
     * @return devuelve el cui del cliente
     */
    public long getCuiCliente() {
        return cuiCliente;
    }

    /**
     * establece el cui del cliente
     * @param cuiCliente identificador del cliente
     */
    public void setCuiCliente(long cuiCliente) {
        this.cuiCliente = cuiCliente;
    }

    /**
     * obtiene el id del usuario
     * @return devuelve el identificador del usuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * establece el identificador del usuario
     * @param idUsuario identificador del usuario
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}