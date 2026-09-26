package org.libreria.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import org.libreria.system.Main;
import org.libreria.manager.SesionContext;
import org.libreria.model.Usuario;

/**
 * Controlador encargado de gestionar las acciones y la información
 * mostrada en el panel principal del administrador.
 *
 * Permite navegar entre los diferentes módulos del sistema, gestionar
 * la sesión del usuario y mostrar información del usuario actualmente
 * autenticado.
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.Usuario
 * @see org.libreria.manager.SesionContext
 * @see org.libreria.system.Main
 */
public class AdminDashboardController implements Initializable {

    /**
     * Etiqueta utilizada para mostrar el nombre del usuario.
     */
    @FXML
    private Label lblBienvenida;

    /**
     * Etiqueta utilizada para mostrar las iniciales y el rol del usuario.
     */
    @FXML
    private Label lblRol;

    /**
     * Botón utilizado para cerrar la sesión actual.
     */
    @FXML
    private Button btnCerrarSesion;

    /**
     * Círculo utilizado como avatar del usuario.
     */
    @FXML
    private Circle avatarCircle;

    /**
     * Botón para acceder al módulo de usuarios.
     */
    @FXML
    private Button btnUsuario;

    /**
     * Botón para acceder al módulo de libros.
     */
    @FXML
    private Button btnLibro;

    /**
     * Botón para acceder al módulo de autores.
     */
    @FXML
    private Button btnAutor;

    /**
     * Botón para acceder al módulo de categorías.
     */
    @FXML
    private Button btnCategoria;

    /**
     * Botón para acceder al módulo de editoriales.
     */
    @FXML
    private Button btnEditorial;

    /**
     * Botón para acceder al módulo de ventas.
     */
    @FXML
    private Button btnVentas;

    /**
     * Botón para acceder al módulo de relación entre autores y libros.
     */
    @FXML
    private Button btnAutorLibro;

    /**
     * Botón para acceder al detalle de las ventas.
     */
    @FXML
    private Button btnDetalleVenta;

    /**
     * Tarjeta para registrar un nuevo libro.
     */
    @FXML
    private VBox cardNuevoLibro;

    /**
     * Tarjeta para acceder al registro de una nueva venta.
     */
    @FXML
    private VBox cardAgregarVenta;

    /**
     * Tarjeta para consultar el inventario.
     */
    @FXML
    private VBox cardVerInventario;

    /**
     * Tarjeta para gestionar los usuarios del sistema.
     */
    @FXML
    private VBox cardGestionarUsuarios;

    /**
     * Tarjeta para acceder al módulo de reportes.
     */
    @FXML
    private VBox cardReportes;

    /**
     * Tarjeta para acceder a la configuración del sistema.
     */
    @FXML
    private VBox cardConfiguracion;

    /**
     * Usuario que actualmente tiene iniciada la sesión.
     */
    private Usuario usuarioActual;

    /**
     * Inicializa el controlador y carga la información del usuario
     * actualmente autenticado desde el contexto de sesión.
     *
     * @param url ubicación utilizada para resolver rutas relativas
     * @param rb recursos utilizados para la localización de la interfaz
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioActual = SesionContext.getInstancia().getUsuarioActual();

        if (usuarioActual != null) {
            lblBienvenida.setText(usuarioActual.getUsername());

            String iniciales = usuarioActual.getUsername()
                    .substring(0, Math.min(2, usuarioActual.getUsername().length()))
                    .toUpperCase();

            lblRol.setText(iniciales + " · " + capitalize(usuarioActual.getRol()));
        } else {
            lblBienvenida.setText("Invitado");
            lblRol.setText("?? · Sin sesión");
        }
    }

    /**
     * Convierte la primera letra de un texto a mayúscula y
     * el resto de caracteres a minúscula.
     *
     * @param texto texto que será convertido
     * @return texto con la primera letra en mayúscula
     */
    private String capitalize(String texto) {
        if (texto == null || texto.isEmpty()) {
            return "";
        }

        return texto.substring(0, 1).toUpperCase()
                + texto.substring(1).toLowerCase();
    }

    /**
     * Cierra la sesión del usuario actual y navega hacia la pantalla
     * de inicio de sesión.
     *
     * @param evento evento generado al presionar el botón de cerrar sesión
     */
    @FXML
    public void cerrarSesion(ActionEvent evento) {
        SesionContext.getInstancia().cerrarSesion();
        navegar("/org/ac/view/fxml/InicioSesionView.fxml");
    }

    /**
     * Abre la pantalla de gestión de usuarios.
     *
     * @param evento evento generado al seleccionar la opción de usuarios
     */
    @FXML
    public void irAUsuario(ActionEvent evento) {
        navegar("/org/ac/view/fxml/UsuarioView.fxml");
    }

    /**
     * Abre la pantalla de gestión de libros.
     *
     * @param evento evento generado al seleccionar la opción de libros
     */
    @FXML
    public void irALibro(ActionEvent evento) {
        navegar("/org/ac/view/fxml/LibroView.fxml");
    }

    /**
     * Abre la pantalla de gestión de autores.
     *
     * @param evento evento generado al seleccionar la opción de autores
     */
    @FXML
    public void irAAutor(ActionEvent evento) {
        navegar("/org/ac/view/fxml/AutorView.fxml");
    }

    /**
     * Abre la pantalla de gestión de categorías.
     *
     * @param evento evento generado al seleccionar la opción de categorías
     */
    @FXML
    public void irACategoria(ActionEvent evento) {
        navegar("/org/ac/view/fxml/CategoriaView.fxml");
    }

    /**
     * Abre la pantalla de gestión de editoriales.
     *
     * @param evento evento generado al seleccionar la opción de editoriales
     */
    @FXML
    public void irAEditorial(ActionEvent evento) {
        navegar("/org/ac/view/fxml/EditorialView.fxml");
    }

    /**
     * Abre la pantalla que contiene la lista de ventas realizadas.
     *
     * @param evento evento generado al seleccionar la opción de ventas
     */
    @FXML
    public void irAVentas(ActionEvent evento) {
        navegar("/org/ac/view/fxml/ListaVentasView.fxml");
    }

    /**
     * Abre la pantalla de relación entre autores y libros.
     *
     * @param evento evento generado al seleccionar la opción correspondiente
     */
    @FXML
    public void irAAutorLibro(ActionEvent evento) {
        navegar("/org/ac/view/fxml/AutorLibroView.fxml");
    }

    /**
     * Abre la pantalla que muestra el detalle de las ventas.
     *
     * @param evento evento generado al seleccionar la opción correspondiente
     */
    @FXML
    public void irADetalleVenta(ActionEvent evento) {
        navegar("/org/ac/view/fxml/DetalleVentaView.fxml");
    }

    /**
     * Abre la pantalla de gestión de clientes.
     *
     * @param evento evento generado al seleccionar la opción de clientes
     */
    @FXML
    public void irAClientes(ActionEvent evento) {
        try {
            Main.cambiarEscena("/org/ac/view/fxml/ClienteView.fxml");
        } catch (IOException e) {
            System.err.println("Error al cargar clientes: " + e.getMessage());
        }
    }

    /**
     * Abre el formulario para registrar un nuevo libro.
     *
     * @param evento evento generado al seleccionar la tarjeta de nuevo libro
     */
    @FXML
    public void nuevoLibro(MouseEvent evento) {
        navegar("/org/ac/view/fxml/LibroFormView.fxml");
    }

    /**
     * Abre la pantalla para registrar una nueva venta.
     *
     * @param evento evento generado al seleccionar la tarjeta de nueva venta
     */
    @FXML
    public void agregarVenta(MouseEvent evento) {
        navegar("/org/ac/view/fxml/VentaView.fxml");
    }

    /**
     * Abre la pantalla de consulta del inventario.
     *
     * @param evento evento generado al seleccionar la tarjeta de inventario
     */
    @FXML
    public void verInventario(MouseEvent evento) {
        navegar("/org/ac/view/fxml/InventarioView.fxml");
    }

    /**
     * Abre la pantalla de gestión de usuarios.
     *
     * @param evento evento generado al seleccionar la tarjeta de usuarios
     */
    @FXML
    public void gestionarUsuarios(MouseEvent evento) {
        navegar("/org/ac/view/fxml/GestionUsuariosView.fxml");
    }

    /**
     * Abre la pantalla de reportes del sistema.
     *
     * @param evento evento generado al seleccionar la tarjeta de reportes
     */
    @FXML
    public void reportes(MouseEvent evento) {
        navegar("/org/ac/view/fxml/ReportesView.fxml");
    }

    /**
     * Abre la pantalla de configuración del sistema.
     *
     * @param evento evento generado al seleccionar la tarjeta de configuración
     */
    @FXML
    public void configuracion(MouseEvent evento) {
        navegar("/org/ac/view/fxml/ConfiguracionView.fxml");
    }

    /**
     * Realiza la navegación hacia una vista determinada.
     * Si la vista no puede ser cargada, muestra un mensaje indicando
     * que la sección se encuentra en construcción.
     *
     * @param ruta ruta del archivo FXML que se desea cargar
     */
    private void navegar(String ruta) {
        try {
            Main.cambiarEscena(ruta);
        } catch (IOException | NullPointerException e) {
            Alert alerta = new Alert(
                    Alert.AlertType.INFORMATION,
                    "Esta sección estará disponible próximamente.",
                    ButtonType.OK
            );

            alerta.setTitle("En construcción");
            alerta.setHeaderText(null);
            alerta.showAndWait();
        }
    }

    /**
     * Establece el usuario actual y actualiza la información mostrada
     * en el panel administrativo.
     *
     * @param usuario usuario que será establecido como usuario actual
     */
    public void iniciarUsuario(Usuario usuario) {
        this.usuarioActual = usuario;

        lblBienvenida.setText(usuario.getUsername());

        String iniciales = usuario.getUsername()
                .substring(0, Math.min(2, usuario.getUsername().length()))
                .toUpperCase();

        lblRol.setText(iniciales + " · " + capitalize(usuario.getRol()));
    }
}