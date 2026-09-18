package org.libreria.model;

/**
 *
 * @Juan Esteban
 */
public class AutorLibro {
     private int idAutorLibro;
     private int idAutor;
     private String isbn;
     
     public AutorLibro() {
     }
     
     public AutorLibro(int idAutorLibro, int idAutor, String isbn) {
         this.idAutorLibro = idAutorLibro;
         this.idAutor = idAutor;
         this.isbn = isbn;
     }
     
     public int getIdAutorLibro() {
         return idAutorLibro;
     }
     
     public void setIdAutorLibro(int idAutorLibro) {
         this.idAutorLibro = idAutorLibro;
     }
     
     public void setIdAutor(int idAutor) {
         this.idAutor = idAutor;
     }
     
     public void setidAutor(int idAutor) {
         this.idAutor = idAutor;
     }
     
     public String getIsbn() {
         return isbn;
     }
     
     public void setIsbn(String isbn) {
         this.isbn = isbn;
     }
     
}
