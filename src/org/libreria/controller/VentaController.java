package org.libreria.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.libreria.DAO.ClienteDAO;
import org.libreria.DAO.LibroDao;
import org.libreria.DAO.VentaDAO;
import org.libreria.DAOImpl.ClienteDAOImpl;
import org.libreria.DAOImpl.LibroDAOImpl;
import org.libreria.DAOImpl.VentaDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.exception.ValidacionException;
import org.libreria.manager.SesionContext;
import org.libreria.model.Cliente;
import org.libreria.model.Libro;
import org.libreria.model.LineaVenta;
import org.libreria.model.Venta;
import org.libreria.system.Main;

/**
 * Controlador encargado de gestionar el registro de ventas.
 * Permite seleccionar un cliente y libros, establecer las cantidades,
 * agregar y quitar líneas de venta, calcular el total y registrar
 * la venta junto con sus detalles y el descuento correspondiente
 * del stock.
 *
 * @author Juan Esteban Interiano Riera
 * @version 1.0.0
 */
public class VentaController implements Initializable {

    @FXML
    private ComboBox<Cliente> cmbCliente;
    @FXML
    private ComboBox<Libro> cmbLibro;
    @FXML
    private Spinner<Integer> spCantidad;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnQuitar;
    @FXML
    private Button btnVaciar;
    @FXML
    private TableView<LineaVenta> tablaLineas;
    @FXML
    private TableColumn colIsbn;
    @FXML
    private TableColumn colTitulo;
    @FXML
    private TableColumn colPrecio;
    @FXML
    private TableColumn colCantidad;
    @FXML
    private TableColumn colSubtotal;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblMensaje;

    private final VentaDAO ventaDAO = new VentaDAOImpl();
    private final ClienteDAO clienteDAO = new ClienteDAOImpl();
    private final LibroDao libroDAO = new LibroDAOImpl();
    private final ObservableList<LineaVenta> lineasVenta = FXCollections.observableArrayList();

    /**
     * Inicializa la vista de registro de ventas.
     * Carga los clientes y libros disponibles, configura la tabla,
     * establece el control de cantidad y calcula el total inicial.
     *
     * @param location ubicación utilizada para resolver rutas relativas
     * @param resources recursos utilizados por la vista
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarCombos();
        tablaLineas.setItems(lineasVenta);
        configurarTabla();
        configurarSpinner();
        calcularTotal();
    }

    /**
     * Carga los clientes y libros disponibles desde sus respectivos DAO
     * y los establece en los ComboBox correspondientes.
     */
    private void cargarCombos() {
        try {
            cmbCliente.setItems(FXCollections.observableArrayList(clienteDAO.listarTodos()));
            cmbLibro.setItems(FXCollections.observableArrayList(libroDAO.listarTodos()));
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura las columnas de la tabla de líneas de venta y establece
     * las propiedades del modelo que serán mostradas.
     */
    private void configurarTabla() {
        colIsbn.setCellValueFactory(new PropertyValueFactory<LineaVenta, String>("isbn"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<LineaVenta, String>("titulo"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<LineaVenta, Double>("precio"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<LineaVenta, Integer>("cantidad"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<LineaVenta, Double>("subtotal"));
    }

    /**
     * Configura el Spinner utilizado para seleccionar la cantidad
     * de libros que serán agregados a la venta.
     */
    private void configurarSpinner() {
        spCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999, 1));
    }

    /**
     * Calcula el total de la venta sumando los subtotales
     * de todas las líneas agregadas y actualiza la etiqueta
     * que muestra el total.
     */
    private void calcularTotal() {
        double total = 0;
        for (LineaVenta linea : lineasVenta) {
            total += linea.getSubtotal();
        }
        lblTotal.setText(String.format("Total: Q%.2f", total));
    }

    /**
     * Agrega un libro seleccionado a la lista temporal de líneas
     * de la venta, verificando previamente que exista suficiente stock.
     */
    @FXML
    private void handleAgregarLinea() {
        Libro libro = cmbLibro.getValue();
        if (libro == null) {
            mostrarAdvertencia("Seleccione un libro para agregar a la venta.");
            return;
        }

        int cantidad = spCantidad.getValue();
        if (libro.getStock() < cantidad) {
            mostrarAdvertencia("Stock insuficiente. Disponible: " + libro.getStock() + ".");
            return;
        }

        lineasVenta.add(new LineaVenta(libro, cantidad));
        calcularTotal();
        lblMensaje.setText("");
        cmbLibro.setValue(null);
        spCantidad.getValueFactory().setValue(1);
    }

    /**
     * Elimina de la lista la línea de venta que se encuentre seleccionada
     * en la tabla.
     */
    @FXML
    private void handleQuitarLinea() {
        LineaVenta seleccion = tablaLineas.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarAdvertencia("Seleccione una línea de la tabla para quitar.");
            return;
        }

        lineasVenta.remove(seleccion);
        calcularTotal();
    }

    /**
     * Elimina todas las líneas de venta agregadas actualmente
     * y actualiza el total mostrado.
     */
    @FXML
    private void handleVaciar() {
        lineasVenta.clear();
        calcularTotal();
        lblMensaje.setText("");
    }

    /**
     * Valida la información de la venta y registra el encabezado
     * junto con sus líneas mediante el DAO.
     * También obtiene el usuario que tiene iniciada la sesión
     * para asociarlo a la venta.
     */
    @FXML
    private void handleRegistrarVenta() {
        try {
            ValidacionException.validarNoNulo(cmbCliente.getValue(),
                    "Seleccione el cliente de la venta.");

            if (lineasVenta.isEmpty()) {
                throw new ValidacionException("Agregue al menos un libro a la venta.");
            }

            double total = 0;
            for (LineaVenta linea : lineasVenta) {
                total += linea.getSubtotal();
            }

            Venta venta = new Venta(
                    0,
                    null,
                    total,
                    cmbCliente.getValue().getCui(),
                    SesionContext.getInstancia().getUsuarioActual().getId());

            int noVenta = ventaDAO.crearVenta(venta, lineasVenta);

            if (noVenta <= 0) {
                mostrarError("No se pudo registrar la venta.");
                return;
            }

            lblMensaje.setText("Venta #" + noVenta + " registrada exitosamente.");
            limpiarVenta();

        } catch (ValidacionException e) {
            mostrarAdvertencia(e.getMessage());
            lblMensaje.setText(e.getMessage());
        } catch (Exception e) {
            mostrarError("Error al registrar la venta: " + e.getMessage());
        }
    }

    /**
     * Limpia los datos de la venta actual y restablece los controles
     * de selección a sus valores iniciales.
     */
    private void limpiarVenta() {
        lineasVenta.clear();
        cmbCliente.setValue(null);
        cmbLibro.setValue(null);
        spCantidad.getValueFactory().setValue(1);
        calcularTotal();
    }

    /**
     * Regresa al dashboard correspondiente al rol del usuario
     * que actualmente tiene iniciada la sesión.
     */
    @FXML
    private void handleVolver() {
        try {
            Main.cambiarEscena(Main.rutaDashboardSegunRol());
        } catch (Exception e) {
            mostrarError("Error al volver al menú: " + e.getMessage());
        }
    }

    /**
     * Muestra una ventana de alerta de tipo error con el mensaje indicado.
     *
     * @param mensaje mensaje que será mostrado al usuario
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra una ventana de alerta de tipo advertencia con el mensaje indicado.
     *
     * @param mensaje mensaje que será mostrado al usuario
     */
    private void mostrarAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
