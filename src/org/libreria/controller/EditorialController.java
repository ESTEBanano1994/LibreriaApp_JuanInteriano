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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.libreria.DAO.EditorialDAO;
import org.libreria.DAOImpl.EditorialDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.exception.ValidacionException;
import org.libreria.model.Editorial;
import org.libreria.system.Main;

/**
 * Controlador encargado de gestionar la interfaz gráfica de las editoriales.
 * Permite registrar, editar, consultar, buscar y navegar entre las editoriales
 * almacenadas en el sistema.
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.Editorial
 * @see org.libreria.DAO.EditorialDAO
 * @see org.libreria.DAOImpl.EditorialDAOImpl
 */
public class EditorialController implements Initializable {

    /**
     * Campo de texto utilizado para ingresar el NIT de la editorial.
     */
    @FXML
    private TextField txtNit;

    /**
     * Campo de texto utilizado para ingresar el nombre de la editorial.
     */
    @FXML
    private TextField txtNombre;

    /**
     * Campo de texto utilizado para ingresar el teléfono de la editorial.
     */
    @FXML
    private TextField txtTelefono;

    /**
     * Campo de texto utilizado para ingresar la dirección de la editorial.
     */
    @FXML
    private TextField txtDireccion;

    /**
     * Etiqueta utilizada para mostrar mensajes al usuario.
     */
    @FXML
    private Label lblMensaje;

    /**
     * Tabla que muestra las editoriales registradas.
     */
    @FXML
    private TableView<Editorial> tablaEditoriales;

    /**
     * Columna que muestra el NIT de la editorial.
     */
    @FXML
    private TableColumn colNit;

    /**
     * Columna que muestra el nombre de la editorial.
     */
    @FXML
    private TableColumn colNombre;

    /**
     * Columna que muestra el teléfono de la editorial.
     */
    @FXML
    private TableColumn colTelefono;

    /**
     * Columna que muestra la dirección de la editorial.
     */
    @FXML
    private TableColumn colDireccion;

    /**
     * Botón utilizado para registrar una nueva editorial.
     */
    @FXML
    private Button btnNuevo;

    /**
     * Botón utilizado para editar una editorial seleccionada.
     */
    @FXML
    private Button btnEditar;

    /**
     * Botón utilizado para seleccionar la primera editorial.
     */
    @FXML
    private Button btnPrimero;

    /**
     * Botón utilizado para seleccionar la editorial anterior.
     */
    @FXML
    private Button btnAnterior;

    /**
     * Botón utilizado para seleccionar la editorial siguiente.
     */
    @FXML
    private Button btnSiguiente;

    /**
     * Botón utilizado para seleccionar la última editorial.
     */
    @FXML
    private Button btnUltimo;

    /**
     * Campo de texto utilizado para buscar editoriales.
     */
    @FXML
    private TextField txtBuscar;

    /**
     * Indica si el formulario se encuentra en modo edición.
     */
    private boolean modoEdicion = false;

    /**
     * Objeto encargado de realizar las operaciones de acceso a datos
     * relacionadas con las editoriales.
     */
    private final EditorialDAO editorialDAO = new EditorialDAOImpl();

    /**
     * Lista observable que contiene las editoriales registradas.
     */
    private final ObservableList<Editorial> listaEditoriales
            = FXCollections.observableArrayList();

    /**
     * Lista filtrada utilizada para realizar búsquedas dinámicas
     * sobre las editoriales registradas.
     */
    private final FilteredList<Editorial> editorialesFiltradas
            = new FilteredList<>(listaEditoriales, p -> true);

    /**
     * Inicializa el controlador y configura los componentes de la vista.
     *
     * @param location ubicación utilizada para resolver la vista
     * @param resources recursos utilizados por la vista
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        tablaEditoriales.setItems(editorialesFiltradas);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Configura las columnas de la tabla para mostrar los atributos
     * correspondientes de cada editorial.
     */
    public void configurarTabla() {
        colNit.setCellValueFactory(
                new PropertyValueFactory<Editorial, String>("nit"));
        colNombre.setCellValueFactory(
                new PropertyValueFactory<Editorial, String>("nombreEditorial"));
        colTelefono.setCellValueFactory(
                new PropertyValueFactory<Editorial, String>("telefonoEditorial"));
        colDireccion.setCellValueFactory(
                new PropertyValueFactory<Editorial, String>("direccionEditoria"));
    }

    /**
     * Carga desde la base de datos la lista de editoriales registradas.
     */
    private void cargarTabla() {
        try {
            listaEditoriales.setAll(editorialDAO.listarTodos());
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
                (obs, oldValue, newValue) -> filtrarEditoriales());
    }

    /**
     * Filtra la lista de editoriales utilizando el NIT, nombre,
     * teléfono o dirección.
     */
    private void filtrarEditoriales() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();

        if (busqueda.isEmpty()) {
            editorialesFiltradas.setPredicate(p -> true);
        } else {
            editorialesFiltradas.setPredicate(editorial ->
                    editorial.getNit().toLowerCase().contains(busqueda)
                    || editorial.getNombreEditorial().toLowerCase().contains(busqueda)
                    || editorial.getTelefonoEditorial().toLowerCase().contains(busqueda)
                    || editorial.getDireccionEditoria().toLowerCase().contains(busqueda));
        }
    }

    /**
     * Configura el comportamiento de selección de filas de la tabla.
     * Al seleccionar una editorial, sus datos son cargados en el formulario.
     */
    private void seleccionarFila() {
        tablaEditoriales.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        txtNit.setText(newSelection.getNit());
                        txtNombre.setText(newSelection.getNombreEditorial());
                        txtTelefono.setText(newSelection.getTelefonoEditorial());
                        txtDireccion.setText(newSelection.getDireccionEditoria());
                        desactivarFormulario();
                    }
                });
    }

    /**
     * Guarda una nueva editorial o actualiza una existente dependiendo
     * del modo en que se encuentre el formulario.
     */
    @FXML
    private void handleGuardar() {
        try {
            ValidacionException.validarNoVacio(
                    txtNit.getText(), "NIT");
            ValidacionException.validarNoVacio(
                    txtNombre.getText(), "nombre");
            ValidacionException.validarNoVacio(
                    txtTelefono.getText(), "teléfono");
            ValidacionException.validarNoVacio(
                    txtDireccion.getText(), "dirección");

            Editorial editorial = new Editorial();
            editorial.setNit(txtNit.getText().trim());
            editorial.setNombreEditorial(txtNombre.getText().trim());
            editorial.setTelefonoEditorial(txtTelefono.getText().trim());
            editorial.setDireccionEditoria(txtDireccion.getText().trim());

            boolean guardado;

            if (modoEdicion) {
                guardado = editorialDAO.actualizar(editorial);
            } else {
                guardado = editorialDAO.crear(editorial);
            }

            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Editorial actualizada exitosamente."
                        : "Editorial registrada exitosamente.");

                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;
            } else {
                mostrarError("No se pudo guardar la editorial.");
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
        lblMensaje.setText("");
    }

    /**
     * Prepara el formulario para registrar una nueva editorial.
     */
    @FXML
    private void handleNuevo() {
        modoEdicion = false;
        limpiarFormulario();
        activarFormulario();
        desactivarNavegacion();
        tablaEditoriales.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        txtNit.requestFocus();
    }

    /**
     * Prepara el formulario para editar la editorial seleccionada.
     */
    @FXML
    private void handleEditar() {
        Editorial seleccion
                = tablaEditoriales.getSelectionModel().getSelectedItem();

        if (seleccion == null) {
            mostrarError(
                    "Seleccione una editorial de la tabla para editar.");
            return;
        }

        modoEdicion = true;
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Selecciona la primera editorial de la tabla.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaEditoriales.getItems().isEmpty()) {
            tablaEditoriales.getSelectionModel().selectFirst();
            tablaEditoriales.scrollTo(0);
        }
    }

    /**
     * Selecciona la editorial anterior al registro actual.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaEditoriales.getItems().isEmpty()) {
            tablaEditoriales.getSelectionModel().selectPrevious();

            if (tablaEditoriales.getSelectionModel().getSelectedIndex() >= 0) {
                tablaEditoriales.scrollTo(
                        tablaEditoriales.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona la editorial siguiente al registro actual.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaEditoriales.getItems().isEmpty()) {
            tablaEditoriales.getSelectionModel().selectNext();

            if (tablaEditoriales.getSelectionModel().getSelectedIndex() >= 0) {
                tablaEditoriales.scrollTo(
                        tablaEditoriales.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona la última editorial de la tabla.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaEditoriales.getItems().isEmpty()) {
            tablaEditoriales.getSelectionModel().selectLast();
            tablaEditoriales.scrollTo(
                    tablaEditoriales.getItems().size() - 1);
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
     * Limpia todos los campos del formulario de editoriales.
     */
    private void limpiarFormulario() {
        txtNit.clear();
        txtNombre.clear();
        txtTelefono.clear();
        txtDireccion.clear();
    }

    /**
     * Habilita los campos del formulario para permitir su edición.
     */
    private void activarFormulario() {
        txtNit.setDisable(false);
        txtNombre.setDisable(false);
        txtTelefono.setDisable(false);
        txtDireccion.setDisable(false);
    }

    /**
     * Deshabilita los campos del formulario para evitar modificaciones.
     */
    private void desactivarFormulario() {
        txtNit.setDisable(true);
        txtNombre.setDisable(true);
        txtTelefono.setDisable(true);
        txtDireccion.setDisable(true);
    }

    /**
     * Habilita la tabla, botones y campo de búsqueda utilizados
     * para navegar entre las editoriales.
     */
    private void activarNavegacion() {
        tablaEditoriales.setDisable(false);
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
     * para navegar entre las editoriales.
     */
    private void desactivarNavegacion() {
        tablaEditoriales.setDisable(true);
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
