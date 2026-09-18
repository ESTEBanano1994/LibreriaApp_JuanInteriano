package org.libreria.model;

/**
 * Relacona el usuario y todos sus demas datos
 * 
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.Usuario
 */
import java.sql.Timestamp;

//POJO: Nombre, atributos de clase, constructores, metodos(get y set) otros...
//encapssulación, herencia, polimorfismo, abstractión

public class Usuario {
    //id, username, email, first_name, last_name, password_hash, rol, activo, fecha_creacion
    private int id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String passwordHash;
    private String rol;
    private boolean activo;
    private Timestamp fechaCreacion;

    /**
     *Constructor vacío que permite crear una instancia de {@link Usuario}
     * sin establecer valores iniciales.
     */
    public Usuario() {
    }

    /**
     *construye una instancia de {@link Usuario} con los datos especificados
     * 
     * @param id identificador del usuario
     * @param username nombre del usuario
     * @param rol rol del usuario en la pagina
     */
    public Usuario(int id, String username, String rol) {
        this.id = id;
        this.username = username;
        this.rol = rol;
    }

    /**
     * obtiene los datos del usuario
     * 
     * @param username nombre del usuario
     * @param email correo del usuario
     * @param firstName primero nombre del usuario
     * @param lastName apellido del usuario
     * @param passwordHash contraseña del usuario
     * @param rol rol del usuario
     */
    public Usuario(String username, String email, String firstName, String lastName,
            String passwordHash, String rol) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    /**
     * obtiene el rol del usuario
     * @return devuelve su rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * establece el rol unico del usuario
     * @param rol rol del usuario
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * obtiene el id del usuario
     * @return devuelve el id del usuario
     */
    public int getId() {
        return id;
    }

    /**
     * establece el identificador unico del usuario
     * @param id identificador unico del usuario
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * obtiene el nombre de usuario
     * @return devuelve el nombre de usuario
     */
    public String getUsername() {
        return username;
    }

    /**
     * establece el nombre del usuario
     * @param username nombre del usuario
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * obtiene el correo del usuario
     * @return devuelve el correo del usuario
     */
    public String getEmail() {
        return email;
    }

    /**
     * establece el correo del usuario
     * @param email correo personal del usuario
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * obtiene el primer nombre del usuario
     * @return devuelve el nombre del usuario
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * establece el nombre del usuario
     * @param firstName nombre del usuario
     */ 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * obtiene el apellido del usuario
     * @return devuelve el apellido del usuario
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * establece el apellido del usuario
     * @param lastName apellido del usuario
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * obtiene la contraseña del usuario
     * @return devuelve la contraseña encriptada del usuario
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * establece la contraseña del usuario
     * @param passwordHash contraseña encriptada del usuario
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * obtiene si el usuario esta activo o inactivo
     * @return devuelve si esta activo o inactivo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * establece que el usuario esta activo
     * @param activo el usuario activo
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * obtiene la fecha de creacion del usurio
     * @return devuelve la fecha de creacion
     */
    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * establece cuando se creo el usuario
     * @param fechaCreacion fecha de creacion del usuario
     */
    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * obtiene los datos del usuario
     * @return devuelve el nombre de usuario
     */
    @Override
    public String toString() {
        return username;
    }
}
