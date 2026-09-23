package org.libreria.model;

/**
 * Representa la trayectoria y datos que recolecta la factura antes de ser impresa
 * 
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.LineaFactura
 */
//Proyeccion de solo lectura para la factura: una fila del resultado del SP
//sp_buscar_factura (venta + cliente + libro + usuario). No es una entidad.
public class LineaFactura {
    private int numeroFactura;
    private String fechaEmision;
    private long cuiCliente;
    private String nombreCliente;
    private String correoCliente;
    private String isbnLibro;
    private String tituloLibro;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    private String usuarioAtendio;
    private double granTotal;

    /**
     *Constructor vacío que permite crear una instancia de {@link AutorLibro}
     * sin establecer valores iniciales.
     */
    public LineaFactura() {
    }

    /**
     *construye una instancia de {@link Libro} con los datos especificados
     * 
     * obtiene el numero de la factura
     * @return devuelve el numero de la factura
     */
    public int getNumeroFactura() {
        return numeroFactura;
    }

    /**
     * establece el numero de factura
     * @param numeroFactura numero de la factura
     */
    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    /**
     * obtiene la fecha de emision de la factura
     * @return devuelve la fecha de la factura
     */
    public String getFechaEmision() {
        return fechaEmision;
    }

    /**
     * establece la fecha de emision
     * @param fechaEmision fecha de la factura
     */
    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    /**
     * obtiene el cui del cliente que compra
     * @return devuelve el cui del cliente
     */
    public long getCuiCliente() {
        return cuiCliente;
    }

    /**
     * establece el cui de compra
     * @param cuiCliente cui del cliente que compra
     */
    public void setCuiCliente(long cuiCliente) {
        this.cuiCliente = cuiCliente;
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
     * @param nombreCliente nombre del cliente que compra
     */
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    /**
     * obtiene el correo personal del cliente para la factura
     * @return devuelve el crreo del cliente
     */
    public String getCorreoCliente() {
        return correoCliente;
    }

    /**
     * establece el correo para la factura
     * @param correoCliente correo para la factura
     */
    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    /**
     * obtiene el isbn del libro
     * @return devuelve el ibsn del libro
     */
    public String getIsbnLibro() {
        return isbnLibro;
    }

    /**
     * establece el identificador del libro
     * @param isbnLibro identificador del libro
     */
    public void setIsbnLibro(String isbnLibro) {
        this.isbnLibro = isbnLibro;
    }

    /**
     * obtiene el titulo del libro
     * @return devuelve el titulo
     */
    public String getTituloLibro() {
        return tituloLibro;
    }

    /**
     * establece el nombre del libro
     * @param tituloLibro nombre del libro
     */
    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }

    /**
     * obtiene la cantidad de libros vendidos
     * @return devuelve cuantos libros se vendieron
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * establece la cantidad de venta
     * @param cantidad libros vendidos
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * obtiene el precio de cada libro
     * @return devuelve el precio de cada libro
     */
    public double getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * establece el precio de cada libro
     * @param precioUnitario precio de cada libro
     */
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    /**
     * obtiene el subtotal de venta
     * @return devuelve el subtotal de venta
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * establece el subtotal de la venta
     * @param subtotal subtotal de venta
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * obtiene el usuario que fue atendido
     * @return deuvelve el usuario que se atendio
     */
    public String getUsuarioAtendio() {
        return usuarioAtendio;
    }

    /**
     * establece el usuario que feu atendido
     * @param usuarioAtendio usuario que compró
     */
    public void setUsuarioAtendio(String usuarioAtendio) {
        this.usuarioAtendio = usuarioAtendio;
    }

    /**
     * obtiene el total de la venta
     * @return devuelve el total de la venta
     */
    public double getGranTotal() {
        return granTotal;
    }

    /**
     * establece el precio final de todo
     * @param granTotal total de venta
     */
    public void setGranTotal(double granTotal) {
        this.granTotal = granTotal;
    }
}