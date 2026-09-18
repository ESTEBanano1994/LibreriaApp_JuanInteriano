package org.libreria.model;

/**
 * Representa la relación entre un autor y un libro dentro del sistema.
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.AutorLibro
 */
public class AutorLibro {

    private int idAutorLibro;
    private int idAutor;
    private String isbn;

    /**
     * Constructor vacío que permite crear una instancia de {@link AutorLibro}
     * sin establecer valores iniciales.
     */
    public AutorLibro() {
    }
    /**
     * Construye una instancia de {@link AutorLibro} con los datos especificados.
     *
     * @param idAutorLibro identificador de la relación entre autor y libro.
     * @param idAutor identificador del autor.
     * @param isbn ISBN del libro asociado.
     */
    public AutorLibro(int idAutorLibro, int idAutor, String isbn) {
        this.idAutorLibro = idAutorLibro;
        this.idAutor = idAutor;
        this.isbn = isbn;
    }

    /**
     * Obtiene el identificador de la relación entre autor y libro.
     *
     * @return el identificador de la relación.
     */
    public int getIdAutorLibro() {
        return idAutorLibro;
    }

    /**
     * Establece el identificador de la relación entre autor y libro.
     *
     * @param idAutorLibro identificador de la relación.
     */
    public void setIdAutorLibro(int idAutorLibro) {
        this.idAutorLibro = idAutorLibro;
    }

    /**
     * Establece el identificador del autor asociado.
     *
     * @param idAutor identificador del autor.
     */
    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    /**
     * Establece el identificador del autor asociado.
     *
     * @param idAutor identificador del autor.
     */
    public void setidAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    /**
     * Obtiene el ISBN del libro asociado.
     *
     * @return el ISBN del libro.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Establece el ISBN del libro asociado.
     *
     * @param isbn ISBN del libro.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}

