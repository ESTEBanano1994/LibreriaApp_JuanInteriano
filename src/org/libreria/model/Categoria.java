package org.libreria.model;

/**
 * Representa los datos y informacion sobre la categoria del libro
 * 
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.Categoria
 */
public class Categoria {
    private int idCategoria;
    private String nombreCategoria;
    
    /**
     * Constructor vacío que permite crear una instancia de {@link AutorLibro}
     * sin establecer valores iniciales.
     */
    public Categoria(){
    }
    
    /**
     * Construye una instancia de {@link AutorLibro} con los datos especificados.
     *
     * @param idCategoria identificador de la categoria
     * @param nombreCategoria nombre de la categoria
     */
    public Categoria(int idCategoria, String nombreCategoria) {
       this.idCategoria = idCategoria;
       this.nombreCategoria = nombreCategoria;
   }
   
    /**
     * obtiene el identificador de la categoria
     * @return devuelve el id de la categoria
     */
    public int getIdCategoria() {
       return idCategoria;
   }
   
    /**
     * establece el nombre de la categoria
     * @param nombreCategoria nombre de la categoria
     */
    public void setNombreCategoria(String nombreCategoria) {
       this.nombreCategoria = nombreCategoria;
   }
   
    /**
     * obtiene los datos de la categoria
     * @return devuelve los datos de la categoria
     */
    @Override
   public String toString() {
       return nombreCategoria;
   }

    public String getNombreCategoria() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setIdCategoria(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
