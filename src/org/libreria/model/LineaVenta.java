package org.libreria.model;

/**
 * Representa la trayectoria de datos que requiere una venta para ser realizada
 * 
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.LineaVenta
 */
//Línea de venta: un libro con su cantidad, usada como fila temporal en la pantalla de venta
//antes de guardar los DetalleVenta en la base de datos.
public class LineaVenta {
    private Libro libro;
    private int cantidad;

    /**
     *Constructor vacío que permite crear una instancia de {@link AutorLibro}
     * sin establecer valores iniciales.
     */
    public LineaVenta() {
    }

    /**
     *construye una instancia de {@link Libro} con los datos especificados
     * 
     * @param libro producto que se esta vendiendo
     * @param cantidad cantidad del producto que se vende
     */
    public LineaVenta(Libro libro, int cantidad) {
        this.libro = libro;
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el libro que se vende
     * @return devuelve el libro que se vende
     */
    public Libro getLibro() {
        return libro;
    }

    /**
     * establece el libro de la venta
     * @param libro libro vendido
     */
    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    /**
     * obtiene la cantidad de libros vendidos
     * @return devuelve la cantidad de libros vendidos
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * establece la cantidad de libros que se vendieron
     * @param cantidad de libros vendidos
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * obtiene el identificador de cada libro
     * @return devuelve el identificador de cada libro
     */
    public String getIsbn() {
        return libro.getIsbn();
    }

    /**
     * obtiene el titulo de cada libro
     * @return devuelve el titulo de cada libro
     */
    public String getTitulo() {
        return libro.getTitulo();
    }

    /**
     * obtiene el precio de cada libro
     * @return devuelve el precio de cada libro
     */
    public double getPrecio() {
        return libro.getPrecio();
    }

    /**
     * obtiene el subtotal de la venta
     * @return devuelve el subtotal de la venta en base al precio de cada libro y la cantidad de cada uno vendido
     */
    public double getSubtotal() {
        return libro.getPrecio() * cantidad;
    }
}