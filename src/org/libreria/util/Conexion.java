package org.libreria.util;

/**
 *Representa la conexion entre NeatBeans y la base de datos en MySql
 * 
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.util.Conexion
 */
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
/**
 * Constructor vacío que permite crear una instancia de {@link Conexion}
 * sin establecer valores iniciales.
*/
public class Conexion {
    private static Conexion instancia;

    private static final String CONFIG_FILE = "/db.properties";

    private final String url;
    private final String user;
    private final String password;

    //Constructor privado para evitar que hagan "new Conexion()" fuera de esta clase
    private Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error Driver: " + e.getMessage());
        }

        Properties config = new Properties();
        try (InputStream in = getClass().getResourceAsStream(CONFIG_FILE)) {
            if (in == null) {
                throw new IllegalStateException(
                        "No se encontro " + CONFIG_FILE + " en el classpath. "
                        + "Copia db.properties.example como src/db.properties y ajusta los valores.");
            }
            config.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Error al leer " + CONFIG_FILE, e);
        }

        this.url = config.getProperty("db.url");
        this.user = config.getProperty("db.user");
        this.password = config.getProperty("db.password");
        if (url == null || user == null || password == null) {
            throw new IllegalStateException(
                    "Faltan propiedades (db.url, db.user, db.password) en " + CONFIG_FILE);
        }
    }

    //Método público estático para obtener la única instancia del Gestor

    /**
     * Obtiene el estado de la conexion, buscando validarla al encontrar CONFIG-FILE en el class path 
     * @return devuelve el estado de la conexion
     */
    public static synchronized Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    //Método para entregar una conexión fresca cada vez que se pida

    /**
     * obtiene los datos necesarios para la conexion, url, usuario y contraseña
     * @return devuelve los datos de url, usuario y contraseña
     * @throws SQLException excepcion de SQL
     */
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }


}
