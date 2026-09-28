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
import javafx.util.StringConverter;
import org.libreria.DAO.DetalleVentaDAO;
import org.libreria.DAO.LibroDao;
import org.libreria.DAO.VentaDAO;
import org.libreria.DAOImpl.DetalleVentaDAOImpl;
import org.libreria.DAOImpl.LibroDAOImpl;
import org.libreria.DAOImpl.VentaDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.exception.ValidacionException;
import org.libreria.model.DetalleVenta;
import org.libreria.model.Libro;
import org.libreria.model.Venta;
import org.libreria.system.Main;

/**
 * Controlador encargado de gestionar la interfaz gráfica de los detalles
 * de las ventas.
 * Permite registrar, editar, consultar, buscar y navegar entre los
 * detalles de venta registrados en el sistema.
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.DetalleVenta
 * @see org.libreria.model.Venta
 * @see org.libreria.model.Libro
 * @see org.libreria.DAO.DetalleVentaDAO
 * @see org.libreria.DAO.VentaDAO
 * @see org.libreria.DAO.LibroDao
 */
public class DetalleVentaController implements Initializable {

    /**
     * ComboBox utilizado para seleccionar una venta.
     */
    @FXML
    private ComboBox<Venta> cmbVenta;

    /**
     * ComboBox utilizado para seleccionar un libro.
     */
    @FXML
    private ComboBox<Libro> cmbLibro;

    /**
     * Campo de texto utilizado para ingresar la cantidad del producto.
     */
    @FXML
    private TextField txtCantidad;

    /**
     * Campo de texto utilizado para ingresar el precio del producto.
     */
    @FXML
    private TextField txtPrecio;

    /**
     * Etiqueta utilizada para mostrar mensajes al usuario.
     */
    @FXML
    private Label lblMensaje;

    /**
     * Tabla que muestra los detalles de venta registrados.
     */
    @FXML
    private TableView<DetalleVenta> tablaDetalleVenta;

    /**
     * Columna que muestra el identificador del detalle de venta.
     */
    @FXML
    private TableColumn colIdDetalleVenta;

    /**
     * Columna que muestra el número de la venta.
     */
    @FXML
    private TableColumn colNoVenta;

    /**
     * Columna que muestra el ISBN del libro.
     */
    @FXML
    private TableColumn colIsbn;

    /**
     * Columna que muestra la cantidad de libros vendidos.
     */
    @FXML
    private TableColumn colCantidad;

    /**
     * Columna que muestra el precio del libro.
     */
    @FXML
    private TableColumn colPrecio;

    /**
     * Botón utilizado para crear un nuevo detalle de venta.
     */
    @FXML
    private Button btnNuevo;

    /**
     * Botón utilizado para editar un detalle de venta.
     */
    @FXML
    private Button btnEditar;

    /**
     * Botón utilizado para seleccionar el primer detalle de venta.
     */
    @FXML
    private Button btnPrimero;

    /**
     * Botón utilizado para seleccionar el detalle anterior.
     */
    @FXML
    private Button btnAnterior;

    /**
     * Botón utilizado para seleccionar el detalle siguiente.
     */
    @FXML
    private Button btnSiguiente;

    /**
     * Botón utilizado para seleccionar el último detalle de venta.
     */
    @FXML
    private Button btnUltimo;

    /**
     * Campo de texto utilizado para buscar detalles de venta.
     */
    @FXML
    private TextField txtBuscar;

    /**
     * Indica si el formulario se encuentra en modo edición.
     */
    private boolean modoEdicion = false;

    /**
     * Detalle de venta que se encuentra actualmente en edición.
     */
    private DetalleVenta enEdicion;

    /**
     * Objeto encargado de realizar operaciones de acceso a datos
     * relacionadas con los detalles de venta.
     */
    private final DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAOImpl();

    /**
     * Objeto encargado de realizar operaciones de acceso a datos
     * relacionadas con las ventas.
     */
    private final VentaDAO ventaDAO = new VentaDAOImpl();

    /**
     * Objeto encargado de realizar operaciones de acceso a datos
     * relacionadas con los libros.
     */
    private final LibroDao libroDAO = new LibroDAOImpl();

    /**
     * Lista observable que contiene los detalles de venta registrados.
     */
    private final ObservableList<DetalleVenta> listaDetalles
            = FXCollections.observableArrayList();

    /**
     * Lista filtrada utilizada para realizar búsquedas dinámicas
     * sobre los detalles de venta.
     */
    private final FilteredList<DetalleVenta> detallesFiltrados
            = new FilteredList<>(listaDetalles, p -> true);

    /**
     * Inicializa el controlador y configura los componentes de la vista.
     *
     * @param location ubicación utilizada para resolver la vista
     * @param resources recursos utilizados por la vista
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        cargarCombos();
        tablaDetalleVenta.setItems(detallesFiltrados);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Configura las columnas de la tabla para mostrar los atributos
     * correspondientes de cada detalle de venta.
     */
    public void configurarTabla() {
        colIdDetalleVenta.setCellValueFactory(
                new PropertyValueFactory<DetalleVenta, Integer>("idDetalleVenta"));
        colNoVenta.setCellValueFactory(
                new PropertyValueFactory<DetalleVenta, Integer>("noVenta"));
        colIsbn.setCellValueFactory(
                new PropertyValueFactory<DetalleVenta, String>("isbn"));
        colCantidad.setCellValueFactory(
                new PropertyValueFactory<DetalleVenta, Integer>("cantidad"));
        colPrecio.setCellValueFactory(
                new PropertyValueFactory<DetalleVenta, Double>("precio"));
    }

    /**
     * Carga desde la base de datos la lista de detalles de venta registrados.
     */
    private void cargarTabla() {
        try {
            listaDetalles.setAll(detalleVentaDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Carga las ventas y los libros disponibles en los ComboBox
     * utilizados por el formulario.
     */
    private void cargarCombos() {
        try {
            cmbVenta.setItems(FXCollections.observableArrayList(
                    ventaDAO.listarTodos()));

            cmbVenta.setConverter(new StringConverter<Venta>() {
                @Override
                public String toString(Venta venta) {
                    return venta == null ? "" : "Venta #" + venta.getNoVenta();
                }

                @Override
                public Venta fromString(String string) {
                    return null;
                }
            });

            cmbLibro.setItems(FXCollections.observableArrayList(
                    libroDAO.listarTodos()));

        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura el listener utilizado para detectar cambios
     * en el campo de búsqueda.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener(
                (obs, oldValue, newValue) -> filtrarDetalles());
    }

    /**
     * Filtra los detalles de venta utilizando el identificador,
     * número de venta, ISBN, cantidad o precio.
     */
    private void filtrarDetalles() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();

        if (busqueda.isEmpty()) {
            detallesFiltrados.setPredicate(p -> true);
        } else {
            detallesFiltrados.setPredicate(detalle ->
                    String.valueOf(detalle.getIdDetalleVenta()).contains(busqueda)
                    || String.valueOf(detalle.getNoVenta()).contains(busqueda)
                    || detalle.getIsbn().toLowerCase().contains(busqueda)
                    || String.valueOf(detalle.getCantidad()).contains(busqueda)
                    || String.valueOf(detalle.getPrecio()).contains(busqueda));
        }
    }

    /**
     * Configura el comportamiento de selección de filas de la tabla.
     * Al seleccionar un detalle de venta, sus datos son cargados
     * en el formulario.
     */
    private void seleccionarFila() {
        tablaDetalleVenta.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        cmbVenta.setValue(null);

                        for (Venta venta : cmbVenta.getItems()) {
                            if (venta.getNoVenta() == newSelection.getNoVenta()) {
                                cmbVenta.setValue(venta);
                                break;
                            }
                        }

                        cmbLibro.setValue(null);

                        for (Libro libro : cmbLibro.getItems()) {
                            if (libro.getIsbn().equals(newSelection.getIsbn())) {
                                cmbLibro.setValue(libro);
                                break;
                            }
                        }

                        txtCantidad.setText(
                                String.valueOf(newSelection.getCantidad()));
                        txtPrecio.setText(
                                String.valueOf(newSelection.getPrecio()));

                        desactivarFormulario();
                    }
                });
    }

    /**
     * Guarda un nuevo detalle de venta o actualiza uno existente
     * dependiendo del modo en que se encuentre el formulario.
     */
    @FXML
    private void handleGuardar() {
        try {
            ValidacionException.validarNoNulo(
                    cmbVenta.getValue(),
                    "Seleccione una venta.");

            ValidacionException.validarNoNulo(
                    cmbLibro.getValue(),
                    "Seleccione un libro.");

            ValidacionException.validarNoVacio(
                    txtCantidad.getText(),
                    "cantidad");

            ValidacionException.validarPositivo(
                    txtCantidad.getText(),
                    "cantidad");

            ValidacionException.validarNoVacio(
                    txtPrecio.getText(),
                    "precio");

            ValidacionException.validarDecimal(
                    txtPrecio.getText(),
                    "precio");

            DetalleVenta detalle = new DetalleVenta(
                    modoEdicion ? enEdicion.getIdDetalleVenta() : 0,
                    cmbVenta.getValue().getNoVenta(),
                    cmbLibro.getValue().getIsbn(),
                    Integer.parseInt(txtCantidad.getText().trim()),
                    Double.parseDouble(txtPrecio.getText().trim()));

            boolean guardado;

            if (modoEdicion) {
                guardado = detalleVentaDAO.actualizar(detalle);
            } else {
                guardado = detalleVentaDAO.crear(detalle);
            }

            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Detalle de venta actualizado exitosamente."
                        : "Detalle de venta registrado exitosamente.");

                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;
            } else {
                mostrarError("No se pudo guardar el detalle de venta.");
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
     * Prepara el formulario para registrar un nuevo detalle de venta.
     */
    @FXML
    private void handleNuevo() {
        modoEdicion = false;
        enEdicion = null;
        limpiarFormulario();
        activarFormulario();
        desactivarNavegacion();
        tablaDetalleVenta.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        cmbVenta.requestFocus();
    }

    /**
     * Prepara el formulario para editar el detalle de venta seleccionado.
     */
    @FXML
    private void handleEditar() {
        DetalleVenta seleccion
                = tablaDetalleVenta.getSelectionModel().getSelectedItem();

        if (seleccion == null) {
            mostrarError(
                    "Seleccione un detalle de venta de la tabla para editar.");
            return;
        }

        modoEdicion = true;
        enEdicion = seleccion;
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Selecciona el primer detalle de venta de la tabla.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaDetalleVenta.getItems().isEmpty()) {
            tablaDetalleVenta.getSelectionModel().selectFirst();
            tablaDetalleVenta.scrollTo(0);
        }
    }

    /**
     * Selecciona el detalle de venta anterior al registro actual.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaDetalleVenta.getItems().isEmpty()) {
            tablaDetalleVenta.getSelectionModel().selectPrevious();

            if (tablaDetalleVenta.getSelectionModel().getSelectedIndex() >= 0) {
                tablaDetalleVenta.scrollTo(
                        tablaDetalleVenta.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona el detalle de venta siguiente al registro actual.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaDetalleVenta.getItems().isEmpty()) {
            tablaDetalleVenta.getSelectionModel().selectNext();

            if (tablaDetalleVenta.getSelectionModel().getSelectedIndex() >= 0) {
                tablaDetalleVenta.scrollTo(
                        tablaDetalleVenta.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona el último detalle de venta de la tabla.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaDetalleVenta.getItems().isEmpty()) {
            tablaDetalleVenta.getSelectionModel().selectLast();
            tablaDetalleVenta.scrollTo(
                    tablaDetalleVenta.getItems().size() - 1);
        }
    }

    /**
     * Regresa a la vista correspondiente al rol del usuario actual.
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
     * Limpia todos los campos del formulario de detalle de venta.
     */
    private void limpiarFormulario() {
        cmbVenta.setValue(null);
        cmbLibro.setValue(null);
        txtCantidad.clear();
        txtPrecio.clear();
    }

    /**
     * Habilita los campos del formulario para permitir su edición.
     */
    private void activarFormulario() {
        cmbVenta.setDisable(false);
        cmbLibro.setDisable(false);
        txtCantidad.setDisable(false);
        txtPrecio.setDisable(false);
    }

    /**
     * Deshabilita los campos del formulario para evitar modificaciones.
     */
    private void desactivarFormulario() {
        cmbVenta.setDisable(true);
        cmbLibro.setDisable(true);
        txtCantidad.setDisable(true);
        txtPrecio.setDisable(true);
    }

    /**
     * Habilita la tabla, botones y campo de búsqueda utilizados
     * para navegar entre los detalles de venta.
     */
    private void activarNavegacion() {
        tablaDetalleVenta.setDisable(false);
        btnNuevo.setDisable(false);
        btnEditar.setDisable(false);
        btnPrimero.setDisable(false);
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        btnUltimo.setDisable(false);
        txtBuscar.setDisable(false);
    }

    /**
     * Deshabilita la tabla, botones y campo de búsqueda utilizados
     * para navegar entre los detalles de venta.
     */
    private void desactivarNavegacion() {
        tablaDetalleVenta.setDisable(true);
        btnNuevo.setDisable(true);
        btnEditar.setDisable(true);
        btnPrimero.setDisable(true);
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnUltimo.setDisable(true);
        txtBuscar.setDisable(true);
    }

    /**
     * Muestra una ventana de alerta indicando un error.
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
     * Muestra una ventana de alerta indicando una advertencia.
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

