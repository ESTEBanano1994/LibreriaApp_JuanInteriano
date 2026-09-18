package org.libreria.model;

/**
 * Representa los datos del autor del libro 
 * 
 * @author nombre del creador del codigo {Esteban Interiano}
 * @version 1.0.0
 * @see org.libreria.model.Autor
 */
public class Autor {
    private int idAutor;
    private String nombreAutor;
    private String apellidoAutor;
    private String nacionalidad;
    private String biografia;
    
    /**
     * Constructor vacio que permite crear una instancia de {@link Autor}
     * sin establecer los valores iniciales
     */
    public Autor() {
    }
    
    /**
     * Construye una instancia de {@link Autor} con los datos ya especificados
     * @param idAutor identificador del autor
     * @param nombreAutor nombre del autor
     * @param apellidoAutor apellido respectivo del autor
     * @param nacionalidad nacionalidad del autor
     * @param biografia datos personales del autor
     */
    public Autor(int idAutor, String nombreAutor, String apellidoAutor, String nacionalidad, String biografia) {
     this.idAutor = idAutor;
     this.nombreAutor = nombreAutor;
     this.apellidoAutor = apellidoAutor;
     this.nacionalidad = nacionalidad;
     this.biografia = biografia;
    }
    
    /**
     * obtiene el identificador personal del autor
     * @return devueleve el identificador personal del autor
     */
    public int getIdAutor() {
        return idAutor;
    }
    
    /**
     * establece el identificador del autor asociado
     * @param idAutor el identificador del autor
     */
    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }
    
    /**
     * obtiene el nombre del autor
     * @return devuelve el nombre del autor
     */
    public String getNombreAutor() {
        return nombreAutor;
    }
    
    /**
     * establece el nombre del autor
     * @param nombreAutor nombre del autor
     */
    public void setNombreAutor(String nombreAutor) {
        this.apellidoAutor = apellidoAutor;
    }
    
    /**
     * obtiene el apellido del autor
     * @return devuelve el apellido del autor
     */
    public String getApellidoAutor() {
        return apellidoAutor;
    }

    /**
     * Establece la relacion entre el autor y su apellido
     * @param apellidoAutor apellido del autor
     */
    public void setApellidoAutor(String apellidoAutor) {
        this.apellidoAutor = apellidoAutor;
    }

    /**
     * obtiene la nacionalidad del autor
     * @return devuelve la nacionalidad del autor
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * establece la nacionalidad del autor
     * @param nacionalidad del autor
     */
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    /**
     * obtiene la biografia del autor
     * @return devuelve la biografia del autor
     */
    public String getBiografia() {
        return biografia;
    }

    /**
     * establece la biografia del autor
     * @param biografia biografia del autor
     */
    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    /**
     * Obtiene la relacion entre el nombre y el apellido del autor
     * @return devuelve nombre mas el apellido del autor
     */
    @Override
    public String toString() {
        return nombreAutor + " " + apellidoAutor;
    }
}