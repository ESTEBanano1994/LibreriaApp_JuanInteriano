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
 * Controlador encargado de gestionar la interfaz principal del cajero.
 * Permite acceder a las diferentes funciones relacionadas con ventas,
 * detalles de ventas, lista de ventas e inventario.
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.Usuario
 * @see org.libreria.manager.SesionContext
 * @see org.libreria.system.Main
 */
public class CajeroController implements Initializable {

    /**
     * Etiqueta utilizada para mostrar el nombre del usuario actual.
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
     * Botón utilizado para acceder al módulo de ventas.
     */
    @FXML
    private Button btnVenta;

    /**
     * Botón utilizado para acceder al detalle de una venta.
     */
    @FXML
    private Button btnDetalleVenta;

    /**
     * Botón utilizado para acceder a la lista de ventas.
     */
    @FXML
    private Button btnListaVentas;

    /**
     * Botón utilizado para acceder al inventario.
     */
    @FXML
    private Button btnInventario;

    /**
     * Tarjeta utilizada para acceder al módulo de agregar ventas.
     */
    @FXML
    private VBox cardAgregarVenta;

    /**
     * Tarjeta utilizada para acceder al detalle de ventas.
     */
    @FXML
    private VBox cardDetalleVenta;

    /**
     * Tarjeta utilizada para acceder a la lista de ventas.
     */
    @FXML
    private VBox cardListaVentas;

    /**
     * Tarjeta utilizada para acceder al inventario.
     */
    @FXML
    private VBox cardVerInventario;

    /**
     * Usuario que actualmente tiene iniciada la sesión.
     */
    private Usuario usuarioActual;

    /**
     * Inicializa el controlador y obtiene la información del usuario
     * almacenada en la sesión actual.
     *
     * @param url ubicación utilizada para resolver rutas relativas
     * @param rb recursos utilizados por la vista
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioActual = SesionContext.getInstancia().getUsuarioActual();

        if (usuarioActual != null) {
            lblBienvenida.setText(usuarioActual.getUsername());

            String iniciales = usuarioActual.getUsername()
                    .substring(0, Math.min(2, usuarioActual.getUsername().length()))
                    .toUpperCase();

            lblRol.setText(
                    iniciales + " · " + capitalize(usuarioActual.getRol()));
        } else {
            lblBienvenida.setText("Invitado");
            lblRol.setText("?? · Sin sesión");
        }
    }

    /**
     * Convierte la primera letra de un texto a mayúscula
     * y el resto a minúsculas.
     *
     * @param texto texto que se desea convertir
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
     * Cierra la sesión del usuario actual y regresa a la pantalla
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
     * Abre la vista correspondiente al registro de una nueva venta.
     *
     * @param evento evento generado al presionar el botón de ventas
     */
    @FXML
    public void irAVenta(ActionEvent evento) {
        navegar("/org/ac/view/fxml/VentaView.fxml");
    }

    /**
     * Abre la vista correspondiente al detalle de una venta.
     *
     * @param evento evento generado al presionar el botón de detalle de venta
     */
    @FXML
    public void irADetalleVenta(ActionEvent evento) {
        navegar("/org/ac/view/fxml/DetalleVentaView.fxml");
    }

    /**
     * Abre la vista que contiene la lista de ventas registradas.
     *
     * @param evento evento generado al presionar el botón de lista de ventas
     */
    @FXML
    public void irAListaVentas(ActionEvent evento) {
        navegar("/org/ac/view/fxml/ListaVentasView.fxml");
    }

    /**
     * Abre la vista correspondiente al inventario.
     *
     * @param evento evento generado al presionar el botón de inventario
     */
    @FXML
    public void irAInventario(ActionEvent evento) {
        navegar("/org/ac/view/fxml/InventarioView.fxml");
    }

    /**
     * Abre la vista para registrar una nueva venta desde la tarjeta
     * correspondiente.
     *
     * @param evento evento generado al seleccionar la tarjeta de venta
     */
    @FXML
    public void agregarVenta(MouseEvent evento) {
        navegar("/org/ac/view/fxml/VentaView.fxml");
    }

    /**
     * Abre la vista correspondiente al detalle de una venta
     * desde la tarjeta correspondiente.
     *
     * @param evento evento generado al seleccionar la tarjeta de detalle
     */
    @FXML
    public void detalleVenta(MouseEvent evento) {
        navegar("/org/ac/view/fxml/DetalleVentaView.fxml");
    }

    /**
     * Abre la vista que contiene la lista de ventas desde
     * la tarjeta correspondiente.
     *
     * @param evento evento generado al seleccionar la tarjeta de ventas
     */
    @FXML
    public void listaVentas(MouseEvent evento) {
        navegar("/org/ac/view/fxml/ListaVentasView.fxml");
    }

    /**
     * Abre la vista del inventario desde la tarjeta correspondiente.
     *
     * @param evento evento generado al seleccionar la tarjeta de inventario
     */
    @FXML
    public void verInventario(MouseEvent evento) {
        navegar("/org/ac/view/fxml/InventarioView.fxml");
    }

    /**
     * Realiza la navegación hacia la vista indicada.
     * Si la ruta no existe o ocurre un error durante la navegación,
     * muestra un mensaje indicando que la sección se encuentra
     * en construcción.
     *
     * @param ruta ruta del archivo FXML que se desea abrir
     */
    private void navegar(String ruta) {
        try {
            Main.cambiarEscena(ruta);
        } catch (IOException | NullPointerException e) {
            Alert alerta = new Alert(
                    Alert.AlertType.INFORMATION,
                    "Esta sección estará disponible próximamente.",
                    ButtonType.OK);

            alerta.setTitle("En construcción");
            alerta.setHeaderText(null);
            alerta.showAndWait();
        }
    }
}

