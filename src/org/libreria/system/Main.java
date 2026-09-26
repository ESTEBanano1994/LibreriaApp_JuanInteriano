
package org.libreria.system;

import java.io.IOException;
import java.util.logging.Level;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Logger;
import org.libreria.manager.SesionContext;
import org.libreria.model.Usuario;

/**
 * Clase principal de la aplicación LibreriaApp que extiende de JavaFX Application.
 * Se encarga de gestionar la ventana principal, el cambio de escenas y el flujo del sistema.
 * 
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.LineaVenta
 */
public class Main extends Application {

    private static Stage escenarioPrincipal;
    private static final Logger log = Logger.getLogger(Main.class.getName());

    /**
     * Cambia la escena actual del escenario principal cargando un archivo FXML.
     * 
     * @param rutaFXML La ruta relativa o absoluta del archivo FXML que se desea cargar.
     * @throws IOException Si ocurre un error al intentar cargar el archivo FXML.
     */
    public static void cambiarEscena(String rutaFXML) throws IOException {
        log.log(Level.INFO, "Se cambio de escena a: {0}", rutaFXML);
        Parent raiz = FXMLLoader.load(
                Main.class.getResource(rutaFXML));
        Scene escena = new Scene(raiz);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        escenarioPrincipal.centerOnScreen();
        escenarioPrincipal.show();
    }

    /**
     * Devuelve la ruta del dashboard correspondiente al rol del usuario con
     * sesion activa. Si no hay sesion o el rol es desconocido, devuelve la
     * ruta del login.
     * 
     * @return Una cadena de texto con la ruta del archivo FXML del dashboard correspondiente.
     */
    public static String rutaDashboardSegunRol() {
        Usuario usuario = SesionContext.getInstancia().getUsuarioActual();
        if (usuario == null || usuario.getRol() == null) {
            return "/org/ac/view/fxml/InicioSesionView.fxml";
        }
        switch (usuario.getRol().toLowerCase()) {
            case "admin":
                return "/org/ac/view/fxml/AdminDashboradView.fxml";
            case "empleado":
                return "/org/ac/view/fxml/EmpleadoView.fxml";
            case "cajero":
                return "/org/ac/view/fxml/CajeroView.fxml";
            default:
                return "/org/ac/view/fxml/InicioSesionView.fxml";
        }
    }

    /**
     * Método principal que sirve como punto de entrada para la ejecución de la aplicación.
     * 
     * @param args Argumentos de la línea de comandos pasados al iniciar el programa.
     */
    public static void main(String[] args) {
        log.info("Se inicio el programa");
        launch(args);
    }

    /**
     * Método de inicio de JavaFX que se ejecuta al arrancar la aplicación.
     * Configura el escenario principal y carga la vista de inicio de sesión.
     * 
     * @param escenarioPrincipal El escenario principal (Stage) proporcionado por JavaFX.
     * @throws Exception Si ocurre algún error durante la inicialización de la interfaz.
     */
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        Main.escenarioPrincipal = escenarioPrincipal;
        cambiarEscena("/org/ac/view/fxml/InicioSesionView.fxml");
    }
}