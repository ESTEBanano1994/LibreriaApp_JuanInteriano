package org.libreria.model;

/**
 * Representa los datos y el detalle de la venta realizada
 * 
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.DetalleVenta
 */
public class DetalleVenta {
    private int idDetalleVenta;
    private int noVenta;
    private String isbn;
    private int cantidad;
    private double precio;

    /**
     *Constructor vacío que permite crear una instancia de {@link AutorLibro}
     * sin establecer valores iniciales.
     */
    public DetalleVenta() {
    }

    /**
     * construye una instancia de {@link Libro} con los datos especificados
     * @param idDetalleVenta identificador detallado de la venta
     * @param noVenta numero de venta realizada
     * @param isbn identificador del libro
     * @param cantidad cantidad de libros comprados
     * @param precio precio del o los libros comprados
     */
    public DetalleVenta(int idDetalleVenta, int noVenta, String isbn, int cantidad, double precio) {
        this.idDetalleVenta = idDetalleVenta;
        this.noVenta = noVenta;
        this.isbn = isbn;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    /**
     * obtiene el id de la venta
     * @return devuelve el id de la venta
     */
    public int getIdDetalleVenta() {
        return idDetalleVenta;
    }

    /**
     * establece el ide de venta
     * @param idDetalleVenta id detalle venta
     */
    public void setIdDetalleVenta(int idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    /**
     * obtiene el numero de la venta
     * @return devuelve el numero de venta
     */
    public int getNoVenta() {
        return noVenta;
    }

    /**
     * establece el numero de la venta realizada
     * @param noVenta numero de venta
     */
    public void setNoVenta(int noVenta) {
        this.noVenta = noVenta;
    }

    /**
     * obtiene el isbn del libro
     * @return devuelve el identificador del libro
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * establece el isbn
     * @param isbn identificador del libro
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * obtiene la cantidad de libros vendidos
     * @return devuelve esa cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * establece la cantidad de libros
     * @param cantidad
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * obtiene el precio del o los libros vendidos
     * @return devuelve ese precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * establece el precio del libro
     * @param precio precio del libro
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }
}