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
import org.libreria.DAO.CategoriaDAO;
import org.libreria.DAOImpl.CategoriaDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.exception.ValidacionException;
import org.libreria.model.Categoria;
import org.libreria.system.Main;

/**
 * Controlador encargado de gestionar las categorías de la biblioteca.
 * Permite registrar, editar, buscar y navegar entre las categorías
 * almacenadas en el sistema.
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.Categoria
 * @see org.libreria.DAO.CategoriaDAO
 * @see org.libreria.DAOImpl.CategoriaDAOImpl
 */
public class CategoriaController implements Initializable {

    /**
     * Campo de texto utilizado para ingresar el nombre de la categoría.
     */
    @FXML
    private TextField txtNombre;

    /**
     * Etiqueta utilizada para mostrar mensajes al usuario.
     */
    @FXML
    private Label lblMensaje;

    /**
     * Tabla que muestra las categorías registradas.
     */
    @FXML
    private TableView<Categoria> tablaCategorias;

    /**
     * Columna que muestra el identificador de la categoría.
     */
    @FXML
    private TableColumn colIdCategoria;

    /**
     * Columna que muestra el nombre de la categoría.
     */
    @FXML
    private TableColumn colNombreCategoria;

    /**
     * Botón utilizado para crear una nueva categoría.
     */
    @FXML
    private Button btnNuevo;

    /**
     * Botón utilizado para editar una categoría existente.
     */
    @FXML
    private Button btnEditar;

    /**
     * Botón utilizado para seleccionar la primera categoría.
     */
    @FXML
    private Button btnPrimero;

    /**
     * Botón utilizado para seleccionar la categoría anterior.
     */
    @FXML
    private Button btnAnterior;

    /**
     * Botón utilizado para seleccionar la categoría siguiente.
     */
    @FXML
    private Button btnSiguiente;

    /**
     * Botón utilizado para seleccionar la última categoría.
     */
    @FXML
    private Button btnUltimo;

    /**
     * Campo de texto utilizado para buscar categorías.
     */
    @FXML
    private TextField txtBuscar;

    /**
     * Indica si el formulario se encuentra en modo edición.
     */
    private boolean modoEdicion = false;

    /**
     * Categoría que se encuentra actualmente en edición.
     */
    private Categoria enEdicion;

    /**
     * DAO utilizado para realizar operaciones sobre las categorías.
     */
    private final CategoriaDAO categoriaDAO = new CategoriaDAOImpl();

    /**
     * Lista observable que contiene todas las categorías.
     */
    private final ObservableList<Categoria> listaCategorias =
            FXCollections.observableArrayList();

    /**
     * Lista filtrada utilizada para mostrar los resultados de búsqueda.
     */
    private final FilteredList<Categoria> categoriasFiltradas =
            new FilteredList<>(listaCategorias, p -> true);

    /**
     * Inicializa el controlador y configura los componentes de la vista.
     *
     * @param location ubicación utilizada para resolver rutas relativas
     * @param resources recursos utilizados por la vista
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        tablaCategorias.setItems(categoriasFiltradas);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Configura las columnas de la tabla utilizando los atributos
     * correspondientes del modelo Categoria.
     */
    public void configurarTabla() {
        colIdCategoria.setCellValueFactory(
                new PropertyValueFactory<Categoria, Integer>("idCategoria"));
        colNombreCategoria.setCellValueFactory(
                new PropertyValueFactory<Categoria, String>("nombreCategoria"));
    }

    /**
     * Carga desde la base de datos todas las categorías registradas.
     * Si ocurre un error de acceso a datos, muestra una alerta.
     */
    private void cargarTabla() {
        try {
            listaCategorias.setAll(categoriaDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura el campo de búsqueda para ejecutar el filtrado
     * cuando cambia su contenido.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener(
                (obs, oldValue, newValue) -> filtrarCategorias());
    }

    /**
     * Filtra las categorías utilizando el texto ingresado
     * en el campo de búsqueda.
     */
    private void filtrarCategorias() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();

        if (busqueda.isEmpty()) {
            categoriasFiltradas.setPredicate(p -> true);
        } else {
            categoriasFiltradas.setPredicate(categoria ->
                    String.valueOf(categoria.getIdCategoria()).contains(busqueda)
                    || categoria.getNombreCategoria()
                            .toLowerCase()
                            .contains(busqueda));
        }
    }

    /**
     * Configura el comportamiento al seleccionar una categoría
     * en la tabla y muestra su nombre en el formulario.
     */
    private void seleccionarFila() {
        tablaCategorias.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        txtNombre.setText(newSelection.getNombreCategoria());
                        desactivarFormulario();
                    }
                });
    }

    /**
     * Guarda una nueva categoría o actualiza una categoría existente.
     *
     * @throws ValidacionException si el nombre de la categoría está vacío
     */
    @FXML
    private void handleGuardar() {
        try {
            ValidacionException.validarNoVacio(
                    txtNombre.getText(),
                    "nombre de la categoría");

            Categoria categoria = new Categoria(
                    modoEdicion ? enEdicion.getIdCategoria() : 0,
                    txtNombre.getText().trim());

            boolean guardado;

            if (modoEdicion) {
                guardado = categoriaDAO.actualizar(categoria);
            } else {
                guardado = categoriaDAO.crear(categoria);
            }

            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Categoría actualizada exitosamente."
                        : "Categoría registrada exitosamente.");

                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;
            } else {
                mostrarError("No se pudo guardar la categoría.");
            }

        } catch (ValidacionException e) {
            mostrarAdvertencia(e.getMessage());
            lblMensaje.setText(e.getMessage());

        } catch (Exception e) {
            mostrarError("Error al guardar: " + e.getMessage());
        }
    }

    /**
     * Cancela la operación actual y restablece el formulario.
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
     * Prepara el formulario para registrar una nueva categoría.
     */
    @FXML
    private void handleNuevo() {
        modoEdicion = false;
        enEdicion = null;
        limpiarFormulario();
        activarFormulario();
        desactivarNavegacion();
        tablaCategorias.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        txtNombre.requestFocus();
    }

    /**
     * Prepara el formulario para editar la categoría seleccionada.
     * Si no existe una selección, muestra un mensaje de error.
     */
    @FXML
    private void handleEditar() {
        Categoria seleccion =
                tablaCategorias.getSelectionModel().getSelectedItem();

        if (seleccion == null) {
            mostrarError(
                    "Seleccione una categoría de la tabla para editar.");
            return;
        }

        modoEdicion = true;
        enEdicion = seleccion;
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Selecciona la primera categoría disponible en la tabla.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaCategorias.getItems().isEmpty()) {
            tablaCategorias.getSelectionModel().selectFirst();
            tablaCategorias.scrollTo(0);
        }
    }

    /**
     * Selecciona la categoría anterior a la selección actual.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaCategorias.getItems().isEmpty()) {
            tablaCategorias.getSelectionModel().selectPrevious();

            if (tablaCategorias.getSelectionModel().getSelectedIndex() >= 0) {
                tablaCategorias.scrollTo(
                        tablaCategorias.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona la categoría siguiente a la selección actual.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaCategorias.getItems().isEmpty()) {
            tablaCategorias.getSelectionModel().selectNext();

            if (tablaCategorias.getSelectionModel().getSelectedIndex() >= 0) {
                tablaCategorias.scrollTo(
                        tablaCategorias.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona la última categoría disponible en la tabla.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaCategorias.getItems().isEmpty()) {
            tablaCategorias.getSelectionModel().selectLast();
            tablaCategorias.scrollTo(
                    tablaCategorias.getItems().size() - 1);
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
     * Limpia el campo utilizado para ingresar el nombre de la categoría.
     */
    private void limpiarFormulario() {
        txtNombre.clear();
    }

    /**
     * Habilita el campo de texto del formulario.
     */
    private void activarFormulario() {
        txtNombre.setDisable(false);
    }

    /**
     * Deshabilita el campo de texto del formulario.
     */
    private void desactivarFormulario() {
        txtNombre.setDisable(true);
    }

    /**
     * Habilita la tabla, los botones de navegación y el campo de búsqueda.
     */
    private void activarNavegacion() {
        tablaCategorias.setDisable(false);
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
        tablaCategorias.setDisable(true);
        btnNuevo.setDisable(true);
        btnEditar.setDisable(true);
        btnPrimero.setDisable(true);
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnUltimo.setDisable(true);
        txtBuscar.setDisable(true);
    }

    /**
     * Muestra una alerta de tipo error al usuario.
     *
     * @param mensaje mensaje que se mostrará en la alerta
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta de tipo advertencia al usuario.
     *
     * @param mensaje mensaje que se mostrará en la alerta
     */
    private void mostrarAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

