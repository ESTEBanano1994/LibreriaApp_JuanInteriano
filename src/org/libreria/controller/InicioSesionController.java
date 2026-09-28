package org.libreria.controller;

import java.io.IOException;
import org.libreria.DAO.UsuarioDAO;
import org.libreria.DAOImpl.UsuarioDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.exception.ValidacionException;
import org.libreria.util.SecurityUtil;
import org.libreria.model.Usuario;
import org.libreria.system.Main;
import org.libreria.manager.SesionContext;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controlador encargado de gestionar el inicio de sesión de los usuarios
 * en la aplicación.
 * Permite validar las credenciales, autenticar al usuario mediante el DAO,
 * establecer la sesión actual y redirigir al dashboard correspondiente
 * según el rol del usuario.
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.Usuario
 * @see org.libreria.DAO.UsuarioDAO
 * @see org.libreria.DAOImpl.UsuarioDAOImpl
 * @see org.libreria.manager.SesionContext
 * @see org.libreria.util.SecurityUtil
 * @see org.libreria.exception.ValidacionException
 * @see org.libreria.exception.DaoException
 * @see org.libreria.system.Main
 */
public class InicioSesionController implements Initializable {

    /**
     * Campo de texto donde el usuario introduce su nombre de usuario.
     */
    @FXML
    private TextField txtUsuario;

    /**
     * Campo donde el usuario introduce su contraseña.
     */
    @FXML
    private PasswordField txtPassword;

    /**
     * Botón utilizado para iniciar sesión.
     */
    @FXML
    private Button btnIniciarSesion;

    /**
     * Etiqueta utilizada para mostrar mensajes relacionados con el inicio
     * de sesión.
     */
    @FXML
    private Label lblMensaje;

    /**
     * DAO utilizado para realizar las operaciones relacionadas con los
     * usuarios.
     */
    private UsuarioDAO usuarioDAO;

    /**
     * Inicializa el controlador y prepara el DAO de usuarios.
     * También limpia el mensaje mostrado en la interfaz.
     *
     * @param url ubicación utilizada para resolver rutas relativas
     * @param rb recursos utilizados para la localización de la interfaz
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioDAO = new UsuarioDAOImpl();
        lblMensaje.setText("");
    }

    /**
     * Procesa el intento de inicio de sesión.
     * Valida que el usuario y la contraseña no estén vacíos, genera el hash
     * de la contraseña y consulta las credenciales mediante el DAO.
     *
     * @param evento evento generado al presionar el botón de inicio de sesión
     */
    @FXML
    public void eventoInicioSesion(ActionEvent evento) {
        try {
            ValidacionException.validarNoVacio(txtUsuario.getText(), "usuario");
            ValidacionException.validarNoVacio(txtPassword.getText(), "contraseña");
            String usuario = txtUsuario.getText();
            String password = txtPassword.getText();
            String passwordHash = SecurityUtil.hashSHA256(password);
            Usuario usuarioIniciado = usuarioDAO.iniciarSesion(usuario, passwordHash);

            if (usuarioIniciado != null) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Inicio correcto");
                abrirDashboard(usuarioIniciado);
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Usuario o contraseña incorrectos");
            }
        } catch (ValidacionException e) {
            mostrarAlerta(Alert.AlertType.WARNING, e.getMessage());
            lblMensaje.setText(e.getMessage());
        } catch (DaoException e) {
            mostrarAlerta(Alert.AlertType.ERROR, e.getMessage());
            lblMensaje.setText("Error al iniciar sesión");
        }
    }

    /**
     * Procesa la acción para acceder al formulario de registro de usuarios.
     * Redirige a la vista correspondiente y muestra un mensaje en caso
     * de que ocurra un error al cargarla.
     *
     * @param evento evento generado al presionar el botón de registro
     */
    @FXML
    public void eventoRegistrarse(ActionEvent evento) {
        try {
            Main.cambiarEscena("/org/ac/view/fxml/RegistrarUsuarioView.fxml");
        } catch (IOException e) {
            System.err.println("Error al cargar registro: " + e.getMessage());
            lblMensaje.setText("Error interno");
        }
    }

    /**
     * Establece el usuario autenticado en el contexto de sesión y abre
     * el dashboard correspondiente según su rol.
     *
     * @param usuario usuario que ha iniciado sesión correctamente
     */
    private void abrirDashboard(Usuario usuario) {
        SesionContext.getInstancia().setUsuarioActual(usuario);

        String rutaFXML = Main.rutaDashboardSegunRol();
        if (rutaFXML.equals("/org/ac/view/fxml/InicioSesionView.fxml")) {
            mostrarAlerta(Alert.AlertType.ERROR, "Rol desconocido: " + usuario.getRol());
            SesionContext.getInstancia().cerrarSesion();
            return;
        }
        try {
            Main.cambiarEscena(rutaFXML);
        } catch (IOException e) {
            System.err.println("Error al cargar la vista:" + rutaFXML + e.getMessage());
            lblMensaje.setText("Error interno");
        }
    }

    /**
     * Muestra una ventana de alerta con el tipo y mensaje especificados.
     *
     * @param tipo tipo de alerta que se mostrará
     * @param mensaje mensaje que será mostrado al usuario
     */
    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alerta = new Alert(tipo, mensaje, ButtonType.OK);
        alerta.showAndWait();
    }
}
