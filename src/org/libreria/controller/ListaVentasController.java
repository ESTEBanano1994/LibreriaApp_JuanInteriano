package org.libreria.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.libreria.DAO.ClienteDAO;
import org.libreria.DAO.VentaDAO;
import org.libreria.DAOImpl.ClienteDAOImpl;
import org.libreria.DAOImpl.VentaDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.exception.ValidacionException;
import org.libreria.manager.SesionContext;
import org.libreria.model.Cliente;
import org.libreria.model.Venta;
import org.libreria.system.Main;

/**
 * Controlador encargado de gestionar la vista de listado de ventas.
 * Permite consultar, buscar, registrar y editar las ventas almacenadas,
 * así como seleccionar clientes y navegar entre los registros.
 *
 * @author Juan Esteban Interiano Riera
 * @version 1.0.0
 */
public class ListaVentasController implements Initializable {

    @FXML
    private TextField txtTotal;
    @FXML
    private ComboBox<Cliente> cmbCliente;
    @FXML
    private Label lblMensaje;
    @FXML
    private TableView<Venta> tablaVentas;
    @FXML
    private TableColumn colNoVenta;
    @FXML
    private TableColumn colFechaVenta;
    @FXML
    private TableColumn colTotalVenta;
    @FXML
    private TableColumn colCuiCliente;
    @FXML
    private TableColumn colUsuario;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnPrimero;
    @FXML
    private Button btnAnterior;
    @FXML
    private Button btnSiguiente;
    @FXML
    private Button btnUltimo;
    @FXML
    private TextField txtBuscar;

    private boolean modoEdicion = false;
    private Venta enEdicion;
    private final VentaDAO ventaDAO = new VentaDAOImpl();
    private final ClienteDAO clienteDAO = new ClienteDAOImpl();
    private final ObservableList<Venta> listaVentas = FXCollections.observableArrayList();
    private final FilteredList<Venta> ventasFiltradas = new FilteredList<>(listaVentas, p -> true);

    /**
     * Inicializa los componentes de la vista y carga la información
     * necesaria para mostrar las ventas y clientes.
     *
     * @param location ubicación utilizada para resolver rutas relativas
     * @param resources recursos utilizados por la vista
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        cargarClientes();
        tablaVentas.setItems(ventasFiltradas);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Configura las columnas de la tabla de ventas y establece
     * las propiedades del modelo que serán mostradas.
     */
    public void configurarTabla() {
        colNoVenta.setCellValueFactory(new PropertyValueFactory<Venta, Integer>("noVenta"));
        colFechaVenta.setCellValueFactory(new PropertyValueFactory<Venta, String>("fechaVenta"));
        colTotalVenta.setCellValueFactory(new PropertyValueFactory<Venta, Double>("totalVenta"));
        colCuiCliente.setCellValueFactory(new PropertyValueFactory<Venta, Long>("cuiCliente"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<Venta, Integer>("idUsuario"));
    }

    /**
     * Obtiene todas las ventas mediante el DAO y las carga
     * en la lista utilizada por la tabla.
     */
    private void cargarTabla() {
        try {
            listaVentas.setAll(ventaDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Obtiene todos los clientes mediante el DAO y los carga
     * en el ComboBox de selección de clientes.
     */
    private void cargarClientes() {
        try {
            cmbCliente.setItems(FXCollections.observableArrayList(clienteDAO.listarTodos()));
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura el listener del campo de búsqueda para filtrar
     * las ventas cada vez que cambia el texto ingresado.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener((obs, oldValue, newValue) -> filtrarVentas());
    }

    /**
     * Filtra las ventas de acuerdo con el texto ingresado por el usuario.
     * La búsqueda se realiza sobre el número de venta, fecha, total,
     * CUI del cliente e identificador del usuario.
     */
    private void filtrarVentas() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();
        if (busqueda.isEmpty()) {
            ventasFiltradas.setPredicate(p -> true);
        } else {
            ventasFiltradas.setPredicate(venta ->
                    String.valueOf(venta.getNoVenta()).contains(busqueda)
                    || venta.getFechaVenta().toLowerCase().contains(busqueda)
                    || String.valueOf(venta.getTotalVenta()).contains(busqueda)
                    || String.valueOf(venta.getCuiCliente()).contains(busqueda)
                    || String.valueOf(venta.getIdUsuario()).contains(busqueda));
        }
    }

    /**
     * Configura el evento de selección de una fila de la tabla.
     * Cuando se selecciona una venta, sus datos se cargan en el formulario
     * y los controles de edición son desactivados.
     */
    private void seleccionarFila() {
        tablaVentas.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        txtTotal.setText(String.valueOf(newSelection.getTotalVenta()));
                        cmbCliente.setValue(null);
                        for (Cliente cliente : cmbCliente.getItems()) {
                            if (cliente.getCui() == newSelection.getCuiCliente()) {
                                cmbCliente.setValue(cliente);
                                break;
                            }
                        }
                        desactivarFormulario();
                    }
                });
    }

    /**
     * Guarda una nueva venta o actualiza una venta existente,
     * dependiendo del modo actual del formulario.
     *
     * @throws ValidacionException si los datos ingresados no cumplen
     * las validaciones establecidas
     */
    @FXML
    private void handleGuardar() {
        try {
            ValidacionException.validarNoVacio(txtTotal.getText(), "total");
            ValidacionException.validarDecimal(txtTotal.getText(), "total");
            ValidacionException.validarNoNulo(cmbCliente.getValue(),
                    "Seleccione un cliente.");

            Venta venta = new Venta(
                    modoEdicion ? enEdicion.getNoVenta() : 0,
                    null,
                    Double.parseDouble(txtTotal.getText().trim()),
                    cmbCliente.getValue().getCui(),
                    SesionContext.getInstancia().getUsuarioActual().getId());

            boolean guardado;
            if (modoEdicion) {
                guardado = ventaDAO.actualizar(venta);
            } else {
                guardado = ventaDAO.crear(venta);
            }

            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Venta actualizada exitosamente."
                        : "Venta registrada exitosamente.");
                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;
            } else {
                mostrarError("No se pudo guardar la venta.");
            }
        } catch (ValidacionException e) {
            mostrarAdvertencia(e.getMessage());
            lblMensaje.setText(e.getMessage());
        } catch (Exception e) {
            mostrarError("Error al guardar: " + e.getMessage());
        }
    }

    /**
     * Cancela la operación actual y restablece el formulario
     * a su estado inicial.
     */
    @FXML
    private void handleCancelar() {
        limpiarFormulario();
        desactivarFormulario();
        activarNavegacion();
        modoEdicion = false;
        enEdicion = null;
        lblMensaje.setText("");
    }

    /**
     * Prepara el formulario para registrar una nueva venta.
     */
    @FXML
    private void handleNuevo() {
        modoEdicion = false;
        enEdicion = null;
        limpiarFormulario();
        activarFormulario();
        desactivarNavegacion();
        tablaVentas.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        txtTotal.requestFocus();
    }

    /**
     * Prepara el formulario para editar la venta seleccionada.
     * Muestra un mensaje de error si no existe una venta seleccionada.
     */
    @FXML
    private void handleEditar() {
        Venta seleccion = tablaVentas.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarError("Seleccione una venta de la tabla para editar.");
            return;
        }
        modoEdicion = true;
        enEdicion = seleccion;
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Selecciona el primer registro disponible en la tabla.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaVentas.getItems().isEmpty()) {
            tablaVentas.getSelectionModel().selectFirst();
            tablaVentas.scrollTo(0);
        }
    }

    /**
     * Selecciona el registro anterior al registro actualmente seleccionado.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaVentas.getItems().isEmpty()) {
            tablaVentas.getSelectionModel().selectPrevious();
            if (tablaVentas.getSelectionModel().getSelectedIndex() >= 0) {
                tablaVentas.scrollTo(tablaVentas.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona el registro siguiente al registro actualmente seleccionado.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaVentas.getItems().isEmpty()) {
            tablaVentas.getSelectionModel().selectNext();
            if (tablaVentas.getSelectionModel().getSelectedIndex() >= 0) {
                tablaVentas.scrollTo(tablaVentas.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona el último registro disponible en la tabla.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaVentas.getItems().isEmpty()) {
            tablaVentas.getSelectionModel().selectLast();
            tablaVentas.scrollTo(tablaVentas.getItems().size() - 1);
        }
    }

    /**
     * Regresa a la vista del dashboard correspondiente al rol
     * del usuario que inició sesión.
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
     * Limpia los valores ingresados en el formulario de venta.
     */
    private void limpiarFormulario() {
        txtTotal.clear();
        cmbCliente.setValue(null);
    }

    /**
     * Habilita los controles utilizados para ingresar o modificar
     * información de una venta.
     */
    private void activarFormulario() {
        txtTotal.setDisable(false);
        cmbCliente.setDisable(false);
    }

    /**
     * Deshabilita los controles del formulario de venta.
     */
    private void desactivarFormulario() {
        txtTotal.setDisable(true);
        cmbCliente.setDisable(true);
    }

    /**
     * Habilita la tabla, los botones de navegación, edición,
     * creación y el campo de búsqueda.
     */
    private void activarNavegacion() {
        tablaVentas.setDisable(false);
        btnNuevo.setDisable(false);
        btnEditar.setDisable(false);
        btnPrimero.setDisable(false);
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        btnUltimo.setDisable(false);
        txtBuscar.setDisable(false);
    }

    /**
     * Deshabilita la tabla, los botones de navegación, edición,
     * creación y el campo de búsqueda.
     */
    private void desactivarNavegacion() {
        tablaVentas.setDisable(true);
        btnNuevo.setDisable(true);
        btnEditar.setDisable(true);
        btnPrimero.setDisable(true);
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnUltimo.setDisable(true);
        txtBuscar.setDisable(true);
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

