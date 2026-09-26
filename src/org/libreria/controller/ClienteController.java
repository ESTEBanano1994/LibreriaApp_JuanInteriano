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
import org.libreria.DAO.ClienteDAO;
import org.libreria.DAOImpl.ClienteDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.exception.ValidacionException;
import org.libreria.model.Cliente;
import org.libreria.system.Main;

/**
 * Controlador encargado de gestionar la interfaz gráfica de clientes.
 * Permite registrar, editar, consultar, buscar y navegar entre los
 * registros de clientes almacenados en el sistema.
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.Cliente
 * @see org.libreria.DAO.ClienteDAO
 * @see org.libreria.DAOImpl.ClienteDAOImpl
 */
public class ClienteController implements Initializable {

    /**
     * Campo de texto para ingresar el CUI del cliente.
     */
    @FXML
    private TextField txtCui;

    /**
     * Campo de texto para ingresar el nombre del cliente.
     */
    @FXML
    private TextField txtNombre;

    /**
     * Campo de texto para ingresar el apellido del cliente.
     */
    @FXML
    private TextField txtApellido;

    /**
     * Campo de texto para ingresar el correo electrónico del cliente.
     */
    @FXML
    private TextField txtCorreo;

    /**
     * Etiqueta utilizada para mostrar mensajes al usuario.
     */
    @FXML
    private Label lblMensaje;

    /**
     * Tabla que muestra los clientes registrados.
     */
    @FXML
    private TableView<Cliente> tablaClientes;

    /**
     * Columna que muestra el CUI del cliente.
     */
    @FXML
    private TableColumn colCUI;

    /**
     * Columna que muestra el nombre del cliente.
     */
    @FXML
    private TableColumn colNombreCliente;

    /**
     * Columna que muestra el apellido del cliente.
     */
    @FXML
    private TableColumn colApellidoCliente;

    /**
     * Columna que muestra el correo electrónico del cliente.
     */
    @FXML
    private TableColumn colCorreoElectronico;

    /**
     * Botón utilizado para crear un nuevo cliente.
     */
    @FXML
    private Button btnNuevo;

    /**
     * Botón utilizado para editar un cliente seleccionado.
     */
    @FXML
    private Button btnEditar;

    /**
     * Botón utilizado para seleccionar el primer registro.
     */
    @FXML
    private Button btnPrimero;

    /**
     * Botón utilizado para seleccionar el registro anterior.
     */
    @FXML
    private Button btnAnterior;

    /**
     * Botón utilizado para seleccionar el registro siguiente.
     */
    @FXML
    private Button btnSiguiente;

    /**
     * Botón utilizado para seleccionar el último registro.
     */
    @FXML
    private Button btnUltimo;

    /**
     * Campo de texto utilizado para buscar clientes.
     */
    @FXML
    private TextField txtBuscar;

    /**
     * Indica si el formulario se encuentra en modo edición.
     */
    private boolean modoEdicion = false;

    /**
     * Objeto encargado de realizar las operaciones de acceso a datos
     * relacionadas con los clientes.
     */
    private final ClienteDAO clienteDAO = new ClienteDAOImpl();

    /**
     * Lista observable que contiene los clientes registrados.
     */
    private final ObservableList<Cliente> listaClientes
            = FXCollections.observableArrayList();

    /**
     * Lista filtrada utilizada para realizar búsquedas dinámicas
     * sobre los clientes registrados.
     */
    private final FilteredList<Cliente> clientesFiltrados
            = new FilteredList<>(listaClientes, p -> true);

    /**
     * Inicializa el controlador y configura los componentes de la vista.
     *
     * @param location ubicación utilizada para resolver la vista
     * @param resources recursos utilizados por la vista
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        tablaClientes.setItems(clientesFiltrados);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Configura las columnas de la tabla para mostrar los atributos
     * correspondientes de cada cliente.
     */
    public void configurarTabla() {
        colCUI.setCellValueFactory(new PropertyValueFactory<Cliente, Long>("cui"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombreCliente"));
        colApellidoCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellidoCliente"));
        colCorreoElectronico.setCellValueFactory(new PropertyValueFactory<Cliente, String>("correoElectronico"));
    }

    /**
     * Carga desde la base de datos la lista de clientes registrados.
     */
    private void cargarTabla() {
        try {
            listaClientes.setAll(clienteDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura el listener utilizado para detectar cambios
     * en el campo de búsqueda.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener((obs, oldValue, newValue) -> filtrarClientes());
    }

    /**
     * Filtra la lista de clientes utilizando el CUI, nombre o apellido.
     */
    private void filtrarClientes() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();

        if (busqueda.isEmpty()) {
            clientesFiltrados.setPredicate(p -> true);
        } else {
            clientesFiltrados.setPredicate(cliente ->
                    String.valueOf(cliente.getCui()).contains(busqueda)
                    || cliente.getNombreCliente().toLowerCase().contains(busqueda)
                    || cliente.getApellidoCliente().toLowerCase().contains(busqueda));
        }
    }

    /**
     * Configura el comportamiento de selección de filas de la tabla.
     * Al seleccionar un cliente, sus datos son cargados en el formulario.
     */
    private void seleccionarFila() {
        tablaClientes.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        txtCui.setText(String.valueOf(newSelection.getCui()));
                        txtNombre.setText(newSelection.getNombreCliente());
                        txtApellido.setText(newSelection.getApellidoCliente());
                        txtCorreo.setText(newSelection.getCorreoElectronico());
                        desactivarFormulario();
                    }
                });
    }

    /**
     * Guarda un nuevo cliente o actualiza uno existente dependiendo
     * del modo en que se encuentre el formulario.
     */
    @FXML
    private void handleGuardar() {
        try {
            ValidacionException.validarNoVacio(txtCui.getText(), "CUI");
            ValidacionException.validarNoVacio(txtNombre.getText(), "nombre");
            ValidacionException.validarNoVacio(txtApellido.getText(), "apellido");
            ValidacionException.validarNoVacio(txtCorreo.getText(), "correo electrónico");
            ValidacionException.validarNumero(txtCui.getText(), "CUI");
            ValidacionException.validarLongitudExacta(txtCui.getText().trim(), 13,
                    "El CUI debe tener exactamente 13 dígitos.");
            ValidacionException.validarFormatoEmail(txtCorreo.getText(),
                    "El correo electrónico no tiene un formato válido.");

            Cliente cliente = new Cliente();
            cliente.setCui(Long.parseLong(txtCui.getText().trim()));
            cliente.setNombreCliente(txtNombre.getText().trim());
            cliente.setApellidoCliente(txtApellido.getText().trim());
            cliente.setCorreoElectronico(txtCorreo.getText().trim());

            boolean guardado;

            if (modoEdicion) {
                guardado = clienteDAO.actualizar(cliente);
            } else {
                guardado = clienteDAO.crear(cliente);
            }

            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Cliente actualizado exitosamente."
                        : "Cliente registrado exitosamente.");
                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;
            } else {
                mostrarError("No se pudo guardar el cliente.");
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
     * Prepara el formulario para registrar un nuevo cliente.
     */
    @FXML
    private void handleNuevoCliente() {
        modoEdicion = false;
        limpiarFormulario();
        activarFormulario();
        desactivarNavegacion();
        tablaClientes.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        txtCui.requestFocus();
    }

    /**
     * Prepara el formulario para editar el cliente seleccionado.
     */
    @FXML
    private void handleEditar() {
        Cliente seleccion = tablaClientes.getSelectionModel().getSelectedItem();

        if (seleccion == null) {
            mostrarError("Seleccione un cliente de la tabla para editar.");
            return;
        }

        modoEdicion = true;
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Selecciona el primer cliente de la tabla.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaClientes.getItems().isEmpty()) {
            tablaClientes.getSelectionModel().selectFirst();
            tablaClientes.scrollTo(0);
        }
    }

    /**
     * Selecciona el cliente anterior al registro actualmente seleccionado.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaClientes.getItems().isEmpty()) {
            tablaClientes.getSelectionModel().selectPrevious();

            if (tablaClientes.getSelectionModel().getSelectedIndex() >= 0) {
                tablaClientes.scrollTo(
                        tablaClientes.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona el cliente siguiente al registro actualmente seleccionado.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaClientes.getItems().isEmpty()) {
            tablaClientes.getSelectionModel().selectNext();

            if (tablaClientes.getSelectionModel().getSelectedIndex() >= 0) {
                tablaClientes.scrollTo(
                        tablaClientes.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona el último cliente de la tabla.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaClientes.getItems().isEmpty()) {
            tablaClientes.getSelectionModel().selectLast();
            tablaClientes.scrollTo(tablaClientes.getItems().size() - 1);
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
     * Limpia todos los campos del formulario de clientes.
     */
    private void limpiarFormulario() {
        txtCui.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtCorreo.clear();
    }

    /**
     * Habilita los campos del formulario para permitir su edición.
     */
    private void activarFormulario() {
        txtCui.setDisable(false);
        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        txtCorreo.setDisable(false);
    }

    /**
     * Deshabilita los campos del formulario para evitar modificaciones.
     */
    private void desactivarFormulario() {
        txtCui.setDisable(true);
        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        txtCorreo.setDisable(true);
    }

    /**
     * Habilita la tabla, botones y campo de búsqueda utilizados
     * para navegar entre los clientes.
     */
    private void activarNavegacion() {
        tablaClientes.setDisable(false);
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
     * para navegar entre los clientes.
     */
    private void desactivarNavegacion() {
        tablaClientes.setDisable(true);
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
