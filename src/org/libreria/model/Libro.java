package org.libreria.model;

/**
 * Representa un libro dentro del sistema de libreria, con elementos como su isbn, el titulo, precio, etc
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.AutorLibro
 */
public class Libro {
    private String isbn;
    private String titulo;
    private String fechaPublicacion;
    private double precio;
    private int idCategoria;
    private String nitEditorial;
    private int stock;
/**
 * constructor vacio que permite crear una instancia de {@link Libro}
 * sin establecer valores iniciales
 */
    public Libro() {
    }

    /**
     * construye una instancia de {@link Libro} con los datos especificados
     * @param isbn identificador del libro
     * @param titulo nombre del libro
     * @param fechaPublicacion fecha en la que salio el libro
     * @param precio precio al que se vende el libro
     * @param idCategoria identificador de la categoria del libro
     * @param nitEditorial nit de la editorial que distribuye el libro
     * @param stock cantidad de unidades del libro en bodega
     */
    public Libro(String isbn, String titulo, String fechaPublicacion, double precio, int idCategoria, String nitEditorial, int stock) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.fechaPublicacion = fechaPublicacion;
        this.precio = precio;
        this.idCategoria = idCategoria;
        this.nitEditorial = nitEditorial;
        this.stock = stock;
    }

    /**
     * obtiene el identificador del libro
     * @return devuelve el identificador del libro
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * establece el identificador del libro
     * @param isbn identificador del libro
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * obtiene el nombre del libro
     * @return devuelve el nombre del libro
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * establece el nombre del libro
     * @param titulo nombre del libro
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /** 
     * obtiene la fecha en la que salio el libro
     * @return devuelve la fecha de publicacion
     */
    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * establece la fecha en la que salio el libro
     * @param fechaPublicacion fecha de salida del libro
     */
    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * obtiene el precio al que se vende el libro
     * @return devuelve el precio de venta del libro
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

    /**
     * obtiene el id para determinar la categoria del libro
     * @return devuelve el id de la categoria del libro
     */
    public int getIdCategoria() {
        return idCategoria;
    }

    /**
     * establece la categoria por medio del id
     * @param idCategoria id de la categoria del libro
     */
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    /**
     * obtiene el nit de la editorial que produce el libro
     * @return devuelve el nit de la editorial
     */
    public String getNitEditorial() {
        return nitEditorial;
    }

    /**
     * establece el nit de la editorial
     * @param nitEditorial nit de la editorial que produce el libro
     */
    public void setNitEditorial(String nitEditorial) {
        this.nitEditorial = nitEditorial;
    }

    /**
     * obtiene la cantidad de stock del libro en bodega
     * @return devuelve el stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * establece el stock en bodega
     * @param stock cantidad de libros existentes en bodega
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * obtiene el titulo del libro
     * @return devuelve el titulo
     */
    @Override
    public String toString() {
        return titulo;
    }
}