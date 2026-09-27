
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
import org.libreria.model.Usuario;
import org.libreria.system.Main;
import org.libreria.manager.SesionContext;

/**
 * Controlador de la vista principal del empleado.
 * Gestiona la información de la sesión actual y la navegación hacia
 * los diferentes módulos disponibles para el empleado.
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.Usuario
 * @see org.libreria.manager.SesionContext
 * @see org.libreria.system.Main
 */
public class EmpleadoController implements Initializable {

    /**
     * Etiqueta que muestra el nombre del usuario actualmente conectado.
     */
    @FXML private Label lblBienvenida;

    /**
     * Etiqueta que muestra las iniciales y el rol del usuario.
     */
    @FXML private Label lblRol;

    /**
     * Botón utilizado para cerrar la sesión actual.
     */
    @FXML private Button btnCerrarSesion;

    /**
     * Círculo utilizado como avatar del usuario.
     */
    @FXML private Circle avatarCircle;

    /**
     * Botón para acceder al módulo de inventario.
     */
    @FXML private Button btnInventario;

    /**
     * Botón para acceder al módulo de libros.
     */
    @FXML private Button btnLibro;

    /**
     * Botón para acceder al módulo de autores.
     */
    @FXML private Button btnAutor;

    /**
     * Botón para acceder al módulo de categorías.
     */
    @FXML private Button btnCategoria;

    /**
     * Botón para acceder al módulo de editoriales.
     */
    @FXML private Button btnEditorial;

    /**
     * Botón para acceder al módulo de clientes.
     */
    @FXML private Button btnClientes;

    /**
     * Tarjeta utilizada para visualizar el inventario.
     */
    @FXML private VBox cardVerInventario;

    /**
     * Tarjeta utilizada para acceder al registro de un nuevo libro.
     */
    @FXML private VBox cardNuevoLibro;

    /**
     * Tarjeta utilizada para acceder al registro de un nuevo autor.
     */
    @FXML private VBox cardNuevoAutor;

    /**
     * Tarjeta utilizada para acceder al registro de una nueva categoría.
     */
    @FXML private VBox cardNuevaCategoria;

    /**
     * Tarjeta utilizada para acceder al registro de una nueva editorial.
     */
    @FXML private VBox cardNuevaEditorial;

    /**
     * Tarjeta utilizada para acceder al registro de un nuevo cliente.
     */
    @FXML private VBox cardNuevoCliente;

    /**
     * Usuario que se encuentra actualmente autenticado en el sistema.
     */
    private Usuario usuarioActual;

    /**
     * Inicializa el controlador y obtiene la información del usuario
     * actualmente autenticado para mostrarla en la interfaz.
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
     * Convierte la primera letra de un texto a mayúscula y el resto
     * de los caracteres a minúscula.
     *
     * @param texto texto que será transformado
     * @return texto con la primera letra en mayúscula
     */
    private String capitalize(String texto) {
        if (texto == null || texto.isEmpty()) return "";
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }

    /**
     * Cierra la sesión del usuario actual y redirige a la pantalla
     * de inicio de sesión.
     *
     * @param evento evento generado al presionar el botón de cierre de sesión
     */
    @FXML
    public void cerrarSesion(ActionEvent evento) {
        SesionContext.getInstancia().cerrarSesion();
        navegar("/org/ac/view/fxml/InicioSesionView.fxml");
    }

    /**
     * Navega hacia la vista de inventario.
     *
     * @param evento evento generado por la acción del usuario
     */
    @FXML
    public void irAInventario(ActionEvent evento) {
        navegar("/org/ac/view/fxml/InventarioView.fxml");
    }

    /**
     * Navega hacia la vista de libros.
     *
     * @param evento evento generado por la acción del usuario
     */
    @FXML
    public void irALibro(ActionEvent evento) {
        navegar("/org/ac/view/fxml/LibroView.fxml");
    }

    /**
     * Navega hacia la vista de autores.
     *
     * @param evento evento generado por la acción del usuario
     */
    @FXML
    public void irAAutor(ActionEvent evento) {
        navegar("/org/ac/view/fxml/AutorView.fxml");
    }

    /**
     * Navega hacia la vista de categorías.
     *
     * @param evento evento generado por la acción del usuario
     */
    @FXML
    public void irACategoria(ActionEvent evento) {
        navegar("/org/ac/view/fxml/CategoriaView.fxml");
    }

    /**
     * Navega hacia la vista de editoriales.
     *
     * @param evento evento generado por la acción del usuario
     */
    @FXML
    public void irAEditorial(ActionEvent evento) {
        navegar("/org/ac/view/fxml/EditorialView.fxml");
    }

    /**
     * Navega hacia la vista de clientes.
     *
     * @param evento evento generado por la acción del usuario
     */
    @FXML
    public void irAClientes(ActionEvent evento) {
        navegar("/org/ac/view/fxml/ClienteView.fxml");
    }

    /**
     * Abre la vista de inventario desde la tarjeta correspondiente.
     *
     * @param evento evento generado al seleccionar la tarjeta
     */
    @FXML
    public void verInventario(MouseEvent evento) {
        navegar("/org/ac/view/fxml/InventarioView.fxml");
    }

    /**
     * Abre la vista de libros desde la tarjeta correspondiente.
     *
     * @param evento evento generado al seleccionar la tarjeta
     */
    @FXML
    public void nuevoLibro(MouseEvent evento) {
        navegar("/org/ac/view/fxml/LibroView.fxml");
    }

    /**
     * Abre la vista de autores desde la tarjeta correspondiente.
     *
     * @param evento evento generado al seleccionar la tarjeta
     */
    @FXML
    public void nuevoAutor(MouseEvent evento) {
        navegar("/org/ac/view/fxml/AutorView.fxml");
    }

    /**
     * Abre la vista de categorías desde la tarjeta correspondiente.
     *
     * @param evento evento generado al seleccionar la tarjeta
     */
    @FXML
    public void nuevaCategoria(MouseEvent evento) {
        navegar("/org/ac/view/fxml/CategoriaView.fxml");
    }

    /**
     * Abre la vista de editoriales desde la tarjeta correspondiente.
     *
     * @param evento evento generado al seleccionar la tarjeta
     */
    @FXML
    public void nuevaEditorial(MouseEvent evento) {
        navegar("/org/ac/view/fxml/EditorialView.fxml");
    }

    /**
     * Abre la vista de clientes desde la tarjeta correspondiente.
     *
     * @param evento evento generado al seleccionar la tarjeta
     */
    @FXML
    public void nuevoCliente(MouseEvent evento) {
        navegar("/org/ac/view/fxml/ClienteView.fxml");
    }

    /**
     * Realiza la navegación hacia una vista determinada.
     * Si ocurre un error al cargar la vista, muestra un mensaje
     * indicando que la sección se encuentra en construcción.
     *
     * @param ruta ruta del archivo FXML que se desea abrir
     */
    private void navegar(String ruta) {
        try {
            Main.cambiarEscena(ruta);
        } catch (IOException | NullPointerException e) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION,
                    "Esta sección estará disponible próximamente.", ButtonType.OK);
            alerta.setTitle("En construcción");
            alerta.setHeaderText(null);
            alerta.showAndWait();
        }
    }
}
