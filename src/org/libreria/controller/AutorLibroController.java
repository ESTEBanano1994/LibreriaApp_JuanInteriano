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
import org.libreria.DAO.AutorDAO;
import org.libreria.DAO.AutorLibroDAO;
import org.libreria.DAO.LibroDao;
import org.libreria.DAOImpl.AutorDAOImpl;
import org.libreria.DAOImpl.AutorLibroDAOImpl;
import org.libreria.DAOImpl.LibroDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.exception.ValidacionException;
import org.libreria.model.Autor;
import org.libreria.model.AutorLibro;
import org.libreria.model.Libro;
import org.libreria.system.Main;

/**
 * Controlador encargado de gestionar las relaciones entre autores y libros.
 * Permite registrar, editar, buscar y navegar entre las relaciones
 * existentes en el sistema.
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.AutorLibro
 * @see org.libreria.DAO.AutorLibroDAO
 * @see org.libreria.DAOImpl.AutorLibroDAOImpl
 */
public class AutorLibroController implements Initializable {

    /**
     * ComboBox utilizado para seleccionar un autor.
     */
    @FXML
    private ComboBox<Autor> cmbAutor;

    /**
     * ComboBox utilizado para seleccionar un libro.
     */
    @FXML
    private ComboBox<Libro> cmbLibro;

    /**
     * Etiqueta utilizada para mostrar mensajes al usuario.
     */
    @FXML
    private Label lblMensaje;

    /**
     * Tabla que muestra las relaciones entre autores y libros.
     */
    @FXML
    private TableView<AutorLibro> tablaAutoresLibro;

    /**
     * Columna que muestra el identificador de la relación autor-libro.
     */
    @FXML
    private TableColumn colIdAutorLibro;

    /**
     * Columna que muestra el identificador del autor.
     */
    @FXML
    private TableColumn colIdAutor;

    /**
     * Columna que muestra el ISBN del libro.
     */
    @FXML
    private TableColumn colIsbn;

    /**
     * Botón utilizado para crear una nueva relación.
     */
    @FXML
    private Button btnNuevo;

    /**
     * Botón utilizado para editar una relación existente.
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
     * Campo de texto utilizado para buscar relaciones.
     */
    @FXML
    private TextField txtBuscar;

    /**
     * Indica si el formulario se encuentra en modo edición.
     */
    private boolean modoEdicion = false;

    /**
     * Almacena la relación autor-libro que se encuentra en edición.
     */
    private AutorLibro enEdicion;

    /**
     * DAO utilizado para gestionar las relaciones entre autores y libros.
     */
    private final AutorLibroDAO autorLibroDAO = new AutorLibroDAOImpl();

    /**
     * DAO utilizado para gestionar la información de los autores.
     */
    private final AutorDAO autorDAO = new AutorDAOImpl();

    /**
     * DAO utilizado para gestionar la información de los libros.
     */
    private final LibroDao libroDAO = new LibroDAOImpl();

    /**
     * Lista observable que contiene todas las relaciones autor-libro.
     */
    private final ObservableList<AutorLibro> listaAutoresLibro =
            FXCollections.observableArrayList();

    /**
     * Lista filtrada utilizada para mostrar los resultados de búsqueda.
     */
    private final FilteredList<AutorLibro> autoresLibroFiltrados =
            new FilteredList<>(listaAutoresLibro, p -> true);

    /**
     * Inicializa el controlador y configura los componentes de la vista.
     *
     * @param location ubicación utilizada para resolver rutas relativas
     * @param resources recursos utilizados por la vista
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        cargarCombos();
        tablaAutoresLibro.setItems(autoresLibroFiltrados);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Configura las columnas de la tabla con los atributos correspondientes
     * del modelo AutorLibro.
     */
    public void configurarTabla() {
        colIdAutorLibro.setCellValueFactory(
                new PropertyValueFactory<AutorLibro, Integer>("idAutorLibro"));
        colIdAutor.setCellValueFactory(
                new PropertyValueFactory<AutorLibro, Integer>("idAutor"));
        colIsbn.setCellValueFactory(
                new PropertyValueFactory<AutorLibro, String>("isbn"));
    }

    /**
     * Carga las relaciones autor-libro desde la base de datos.
     * Si ocurre un error de acceso a datos, se muestra una alerta.
     */
    private void cargarTabla() {
        try {
            listaAutoresLibro.setAll(autorLibroDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Carga los autores y libros disponibles en sus respectivos ComboBox.
     * Si ocurre un error de acceso a datos, se muestra una alerta.
     */
    private void cargarCombos() {
        try {
            cmbAutor.setItems(
                    FXCollections.observableArrayList(autorDAO.listarTodos()));
            cmbLibro.setItems(
                    FXCollections.observableArrayList(libroDAO.listarTodos()));
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura el campo de búsqueda para ejecutar el filtrado
     * cada vez que cambia su contenido.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener(
                (obs, oldValue, newValue) -> filtrarAutoresLibro());
    }

    /**
     * Filtra las relaciones autor-libro utilizando el texto ingresado
     * en el campo de búsqueda.
     */
    private void filtrarAutoresLibro() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();

        if (busqueda.isEmpty()) {
            autoresLibroFiltrados.setPredicate(p -> true);
        } else {
            autoresLibroFiltrados.setPredicate(autorLibro ->
                    String.valueOf(autorLibro.getIdAutorLibro()).contains(busqueda)
                    || String.valueOf(autorLibro.getIdAutor()).contains(busqueda)
                    || autorLibro.getIsbn().toLowerCase().contains(busqueda));
        }
    }

    /**
     * Configura el evento de selección de una fila de la tabla.
     * Al seleccionar una relación, se muestran sus datos correspondientes
     * en los ComboBox.
     */
    private void seleccionarFila() {
        tablaAutoresLibro.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        cmbAutor.setValue(null);

                        for (Autor autor : cmbAutor.getItems()) {
                            if (autor.getIdAutor() == newSelection.getIdAutor()) {
                                cmbAutor.setValue(autor);
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

                        desactivarFormulario();
                    }
                });
    }

    /**
     * Guarda una nueva relación autor-libro o actualiza una existente.
     *
     * @throws ValidacionException si no se selecciona un autor o un libro
     */
    @FXML
    private void handleGuardar() {
        try {
            ValidacionException.validarNoNulo(
                    cmbAutor.getValue(),
                    "Seleccione un autor.");

            ValidacionException.validarNoNulo(
                    cmbLibro.getValue(),
                    "Seleccione un libro.");

            AutorLibro autorLibro = new AutorLibro(
                    modoEdicion ? enEdicion.getIdAutorLibro() : 0,
                    cmbAutor.getValue().getIdAutor(),
                    cmbLibro.getValue().getIsbn());

            boolean guardado;

            if (modoEdicion) {
                guardado = autorLibroDAO.actualizar(autorLibro);
            } else {
                guardado = autorLibroDAO.crear(autorLibro);
            }

            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Relación autor-libro actualizada exitosamente."
                        : "Relación autor-libro registrada exitosamente.");

                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;
            } else {
                mostrarError("No se pudo guardar la relación autor-libro.");
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
     * Prepara el formulario para registrar una nueva relación.
     */
    @FXML
    private void handleNuevo() {
        modoEdicion = false;
        enEdicion = null;
        limpiarFormulario();
        activarFormulario();
        desactivarNavegacion();
        tablaAutoresLibro.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        cmbAutor.requestFocus();
    }

    /**
     * Prepara el formulario para editar la relación seleccionada.
     * Si no se selecciona ningún registro, muestra un mensaje de error.
     */
    @FXML
    private void handleEditar() {
        AutorLibro seleccion =
                tablaAutoresLibro.getSelectionModel().getSelectedItem();

        if (seleccion == null) {
            mostrarError(
                    "Seleccione una relación autor-libro de la tabla para editar.");
            return;
        }

        modoEdicion = true;
        enEdicion = seleccion;
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Selecciona el primer registro de la tabla.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaAutoresLibro.getItems().isEmpty()) {
            tablaAutoresLibro.getSelectionModel().selectFirst();
            tablaAutoresLibro.scrollTo(0);
        }
    }

    /**
     * Selecciona el registro anterior al registro actual.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaAutoresLibro.getItems().isEmpty()) {
            tablaAutoresLibro.getSelectionModel().selectPrevious();

            if (tablaAutoresLibro.getSelectionModel().getSelectedIndex() >= 0) {
                tablaAutoresLibro.scrollTo(
                        tablaAutoresLibro.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona el registro siguiente al registro actual.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaAutoresLibro.getItems().isEmpty()) {
            tablaAutoresLibro.getSelectionModel().selectNext();

            if (tablaAutoresLibro.getSelectionModel().getSelectedIndex() >= 0) {
                tablaAutoresLibro.scrollTo(
                        tablaAutoresLibro.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona el último registro de la tabla.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaAutoresLibro.getItems().isEmpty()) {
            tablaAutoresLibro.getSelectionModel().selectLast();
            tablaAutoresLibro.scrollTo(
                    tablaAutoresLibro.getItems().size() - 1);
        }
    }

    /**
     * Regresa al dashboard correspondiente al rol del usuario.
     * Si ocurre un error durante la navegación, muestra una alerta.
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
     * Limpia los valores seleccionados en los ComboBox.
     */
    private void limpiarFormulario() {
        cmbAutor.setValue(null);
        cmbLibro.setValue(null);
    }

    /**
     * Habilita los controles utilizados para ingresar información.
     */
    private void activarFormulario() {
        cmbAutor.setDisable(false);
        cmbLibro.setDisable(false);
    }

    /**
     * Deshabilita los controles utilizados para ingresar información.
     */
    private void desactivarFormulario() {
        cmbAutor.setDisable(true);
        cmbLibro.setDisable(true);
    }

    /**
     * Habilita la tabla, los botones de navegación y el campo de búsqueda.
     */
    private void activarNavegacion() {
        tablaAutoresLibro.setDisable(false);
        btnNuevo.setDisable(false);
        btnEditar.setDisable(false);
        btnPrimero.setDisable(false);
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        btnUltimo.setDisable(false);
        txtBuscar.setDisable(false);
    }

    /**
     * Deshabilita la tabla, los botones de navegación y el campo de búsqueda.
     */
    private void desactivarNavegacion() {
        tablaAutoresLibro.setDisable(true);
        btnNuevo.setDisable(true);
        btnEditar.setDisable(true);
        btnPrimero.setDisable(true);
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnUltimo.setDisable(true);
        txtBuscar.setDisable(true);
    }

    /**
     * Muestra una alerta de tipo error.
     *
     * @param mensaje mensaje que se mostrará al usuario
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta de tipo advertencia.
     *
     * @param mensaje mensaje que se mostrará al usuario
     */
    private void mostrarAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
