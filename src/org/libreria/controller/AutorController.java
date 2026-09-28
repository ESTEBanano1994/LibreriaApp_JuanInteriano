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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.libreria.DAO.AutorDAO;
import org.libreria.DAOImpl.AutorDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.exception.ValidacionException;
import org.libreria.model.Autor;
import org.libreria.system.Main;

/**
 * Controlador encargado de gestionar la interfaz de administración
 * de autores de la aplicación.
 *
 * Permite registrar, editar, consultar y buscar autores, además de
 * controlar la navegación y selección de registros dentro de la tabla.
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.Autor
 * @see org.libreria.DAO.AutorDAO
 * @see org.libreria.DAOImpl.AutorDAOImpl
 */
public class AutorController implements Initializable {

    /**
     * Campo de texto utilizado para ingresar el nombre del autor.
     */
    @FXML
    private TextField txtNombre;

    /**
     * Campo de texto utilizado para ingresar el apellido del autor.
     */
    @FXML
    private TextField txtApellido;

    /**
     * Campo de texto utilizado para ingresar la nacionalidad del autor.
     */
    @FXML
    private TextField txtNacionalidad;

    /**
     * Área de texto utilizada para ingresar la biografía del autor.
     */
    @FXML
    private TextArea txtBiografia;

    /**
     * Etiqueta utilizada para mostrar mensajes al usuario.
     */
    @FXML
    private Label lblMensaje;

    /**
     * Tabla utilizada para mostrar los autores registrados.
     */
    @FXML
    private TableView<Autor> tablaAutores;

    /**
     * Columna que muestra el identificador del autor.
     */
    @FXML
    private TableColumn colIdAutor;

    /**
     * Columna que muestra el nombre del autor.
     */
    @FXML
    private TableColumn colNombreAutor;

    /**
     * Columna que muestra el apellido del autor.
     */
    @FXML
    private TableColumn colApellidoAutor;

    /**
     * Columna que muestra la nacionalidad del autor.
     */
    @FXML
    private TableColumn colNacionalidad;

    /**
     * Columna que muestra la biografía del autor.
     */
    @FXML
    private TableColumn colBiografia;

    /**
     * Botón utilizado para crear un nuevo registro de autor.
     */
    @FXML
    private Button btnNuevo;

    /**
     * Botón utilizado para editar el autor seleccionado.
     */
    @FXML
    private Button btnEditar;

    /**
     * Botón utilizado para seleccionar el primer registro de la tabla.
     */
    @FXML
    private Button btnPrimero;

    /**
     * Botón utilizado para seleccionar el registro anterior.
     */
    @FXML
    private Button btnAnterior;

    /**
     * Botón utilizado para seleccionar el siguiente registro.
     */
    @FXML
    private Button btnSiguiente;

    /**
     * Botón utilizado para seleccionar el último registro de la tabla.
     */
    @FXML
    private Button btnUltimo;

    /**
     * Campo de texto utilizado para buscar autores.
     */
    @FXML
    private TextField txtBuscar;

    /**
     * Indica si el formulario se encuentra en modo edición.
     */
    private boolean modoEdicion = false;

    /**
     * Autor que actualmente se encuentra en edición.
     */
    private Autor enEdicion;

    /**
     * Objeto DAO utilizado para realizar operaciones sobre los autores.
     */
    private final AutorDAO autorDAO = new AutorDAOImpl();

    /**
     * Lista observable que contiene todos los autores registrados.
     */
    private final ObservableList<Autor> listaAutores =
            FXCollections.observableArrayList();

    /**
     * Lista filtrada utilizada para realizar búsquedas dinámicas
     * dentro de la tabla de autores.
     */
    private final FilteredList<Autor> autoresFiltrados =
            new FilteredList<>(listaAutores, p -> true);

    /**
     * Inicializa el controlador y configura la tabla, selección,
     * búsqueda y carga inicial de autores.
     *
     * @param location ubicación utilizada para resolver rutas relativas
     * @param resources recursos utilizados para la localización de la interfaz
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        tablaAutores.setItems(autoresFiltrados);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Configura las columnas de la tabla asociando cada una con
     * la propiedad correspondiente del modelo Autor.
     */
    public void configurarTabla() {
        colIdAutor.setCellValueFactory(
                new PropertyValueFactory<Autor, Integer>("idAutor"));

        colNombreAutor.setCellValueFactory(
                new PropertyValueFactory<Autor, String>("nombreAutor"));

        colApellidoAutor.setCellValueFactory(
                new PropertyValueFactory<Autor, String>("apellidoAutor"));

        colNacionalidad.setCellValueFactory(
                new PropertyValueFactory<Autor, String>("nacionalidad"));

        colBiografia.setCellValueFactory(
                new PropertyValueFactory<Autor, String>("biografia"));
    }

    /**
     * Carga desde la base de datos la lista de autores registrados.
     * En caso de producirse un error de acceso a datos, muestra
     * el mensaje correspondiente al usuario.
     */
    private void cargarTabla() {
        try {
            listaAutores.setAll(autorDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura el campo de búsqueda para actualizar el filtro
     * de autores cada vez que cambia su contenido.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener(
                (obs, oldValue, newValue) -> filtrarAutores());
    }

    /**
     * Filtra la lista de autores utilizando el texto ingresado
     * en el campo de búsqueda.
     *
     * La búsqueda se realiza sobre el identificador, nombre,
     * apellido, nacionalidad y biografía.
     */
    private void filtrarAutores() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();

        if (busqueda.isEmpty()) {
            autoresFiltrados.setPredicate(p -> true);
        } else {
            autoresFiltrados.setPredicate(autor ->
                    String.valueOf(autor.getIdAutor()).contains(busqueda)
                    || autor.getNombreAutor().toLowerCase().contains(busqueda)
                    || autor.getApellidoAutor().toLowerCase().contains(busqueda)
                    || autor.getNacionalidad().toLowerCase().contains(busqueda)
                    || autor.getBiografia().toLowerCase().contains(busqueda));
        }
    }

    /**
     * Configura el evento de selección de una fila de la tabla.
     * Al seleccionar un autor, sus datos se cargan en el formulario
     * y este se desactiva para evitar modificaciones accidentales.
     */
    private void seleccionarFila() {
        tablaAutores.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        txtNombre.setText(newSelection.getNombreAutor());
                        txtApellido.setText(newSelection.getApellidoAutor());
                        txtNacionalidad.setText(newSelection.getNacionalidad());
                        txtBiografia.setText(newSelection.getBiografia());
                        desactivarFormulario();
                    }
                });
    }

    /**
     * Guarda un nuevo autor o actualiza el autor seleccionado
     * cuando el formulario se encuentra en modo edición.
     *
     * @throws ValidacionException si alguno de los campos obligatorios
     * no cumple las reglas de validación
     */
    @FXML
    private void handleGuardar() {
        try {
            ValidacionException.validarNoVacio(
                    txtNombre.getText(), "nombre");

            ValidacionException.validarNoVacio(
                    txtApellido.getText(), "apellido");

            ValidacionException.validarNoVacio(
                    txtNacionalidad.getText(), "nacionalidad");

            Autor autor = new Autor(
                    modoEdicion ? enEdicion.getIdAutor() : 0,
                    txtNombre.getText().trim(),
                    txtApellido.getText().trim(),
                    txtNacionalidad.getText().trim(),
                    txtBiografia.getText().trim());

            boolean guardado;

            if (modoEdicion) {
                guardado = autorDAO.actualizar(autor);
            } else {
                guardado = autorDAO.crear(autor);
            }

            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Autor actualizado exitosamente."
                        : "Autor registrado exitosamente.");

                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;
            } else {
                mostrarError("No se pudo guardar el autor.");
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
     * Prepara el formulario para registrar un nuevo autor.
     */
    @FXML
    private void handleNuevo() {
        modoEdicion = false;
        enEdicion = null;
        limpiarFormulario();
        activarFormulario();
        desactivarNavegacion();
        tablaAutores.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        txtNombre.requestFocus();
    }

    /**
     * Prepara el formulario para editar el autor seleccionado.
     * Si no existe una selección, muestra un mensaje de error.
     */
    @FXML
    private void handleEditar() {
        Autor seleccion =
                tablaAutores.getSelectionModel().getSelectedItem();

        if (seleccion == null) {
            mostrarError("Seleccione un autor de la tabla para editar.");
            return;
        }

        modoEdicion = true;
        enEdicion = seleccion;
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Selecciona el primer autor disponible en la tabla.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaAutores.getItems().isEmpty()) {
            tablaAutores.getSelectionModel().selectFirst();
            tablaAutores.scrollTo(0);
        }
    }

    /**
     * Selecciona el autor anterior al registro actualmente seleccionado.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaAutores.getItems().isEmpty()) {
            tablaAutores.getSelectionModel().selectPrevious();

            if (tablaAutores.getSelectionModel().getSelectedIndex() >= 0) {
                tablaAutores.scrollTo(
                        tablaAutores.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona el autor siguiente al registro actualmente seleccionado.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaAutores.getItems().isEmpty()) {
            tablaAutores.getSelectionModel().selectNext();

            if (tablaAutores.getSelectionModel().getSelectedIndex() >= 0) {
                tablaAutores.scrollTo(
                        tablaAutores.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona el último autor disponible en la tabla.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaAutores.getItems().isEmpty()) {
            tablaAutores.getSelectionModel().selectLast();
            tablaAutores.scrollTo(tablaAutores.getItems().size() - 1);
        }
    }

    /**
     * Regresa al dashboard correspondiente al rol del usuario actual.
     * Si ocurre un error durante la navegación, muestra un mensaje.
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
     * Limpia todos los campos del formulario de autores.
     */
    private void limpiarFormulario() {
        txtNombre.clear();
        txtApellido.clear();
        txtNacionalidad.clear();
        txtBiografia.clear();
    }

    /**
     * Habilita los campos del formulario para permitir
     * el ingreso o modificación de información.
     */
    private void activarFormulario() {
        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        txtNacionalidad.setDisable(false);
        txtBiografia.setDisable(false);
    }

    /**
     * Deshabilita los campos del formulario para impedir
     * modificaciones mientras se consulta la información.
     */
    private void desactivarFormulario() {
        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        txtNacionalidad.setDisable(true);
        txtBiografia.setDisable(true);
    }

    /**
     * Habilita los controles utilizados para navegar y buscar
     * registros dentro de la tabla.
     */
    private void activarNavegacion() {
        tablaAutores.setDisable(false);
        btnNuevo.setDisable(false);
        btnEditar.setDisable(false);
        btnPrimero.setDisable(false);
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        btnUltimo.setDisable(false);
        txtBuscar.setDisable(false);
    }

    /**
     * Deshabilita los controles utilizados para navegar y buscar
     * registros mientras se realiza una operación de edición.
     */
    private void desactivarNavegacion() {
        tablaAutores.setDisable(true);
        btnNuevo.setDisable(true);
        btnEditar.setDisable(true);
        btnPrimero.setDisable(true);
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnUltimo.setDisable(true);
        txtBuscar.setDisable(true);
    }

    /**
     * Muestra una ventana de alerta de tipo error.
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
     * Muestra una ventana de alerta de tipo advertencia.
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