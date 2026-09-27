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
import org.libreria.DAO.CategoriaDAO;
import org.libreria.DAO.EditorialDAO;
import org.libreria.DAO.LibroDao;
import org.libreria.DAOImpl.CategoriaDAOImpl;
import org.libreria.DAOImpl.EditorialDAOImpl;
import org.libreria.DAOImpl.LibroDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.exception.ValidacionException;
import org.libreria.model.Categoria;
import org.libreria.model.Editorial;
import org.libreria.model.Libro;
import org.libreria.system.Main;

/**
 * Controlador encargado de gestionar la vista de libros.
 * Permite registrar, editar, consultar y navegar entre los libros
 * almacenados en el sistema, así como realizar búsquedas y seleccionar
 * categorías y editoriales asociadas.
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.Libro
 * @see org.libreria.model.Categoria
 * @see org.libreria.model.Editorial
 * @see org.libreria.DAO.LibroDao
 * @see org.libreria.DAO.CategoriaDAO
 * @see org.libreria.DAO.EditorialDAO
 * @see org.libreria.DAOImpl.LibroDAOImpl
 * @see org.libreria.DAOImpl.CategoriaDAOImpl
 * @see org.libreria.DAOImpl.EditorialDAOImpl
 * @see org.libreria.exception.DaoException
 * @see org.libreria.exception.ValidacionException
 * @see org.libreria.system.Main
 */
public class LibroController implements Initializable {

    /**
     * Campo de texto utilizado para ingresar el ISBN del libro.
     */
    @FXML
    private TextField txtIsbn;

    /**
     * Campo de texto utilizado para ingresar el título del libro.
     */
    @FXML
    private TextField txtTitulo;

    /**
     * Campo de texto utilizado para ingresar la fecha de publicación.
     */
    @FXML
    private TextField txtFecha;

    /**
     * Campo de texto utilizado para ingresar el precio del libro.
     */
    @FXML
    private TextField txtPrecio;

    /**
     * Campo de texto utilizado para ingresar la cantidad disponible
     * en inventario.
     */
    @FXML
    private TextField txtStock;

    /**
     * ComboBox utilizado para seleccionar la categoría del libro.
     */
    @FXML
    private ComboBox<Categoria> cmbCategoria;

    /**
     * ComboBox utilizado para seleccionar la editorial del libro.
     */
    @FXML
    private ComboBox<Editorial> cmbEditorial;

    /**
     * Etiqueta utilizada para mostrar mensajes al usuario.
     */
    @FXML
    private Label lblMensaje;

    /**
     * Tabla donde se muestran los libros registrados.
     */
    @FXML
    private TableView<Libro> tablaLibros;

    /**
     * Columna que muestra el ISBN de los libros.
     */
    @FXML
    private TableColumn colIsbn;

    /**
     * Columna que muestra el título de los libros.
     */
    @FXML
    private TableColumn colTitulo;

    /**
     * Columna que muestra la fecha de publicación de los libros.
     */
    @FXML
    private TableColumn colFecha;

    /**
     * Columna que muestra el precio de los libros.
     */
    @FXML
    private TableColumn colPrecio;

    /**
     * Columna que muestra el stock disponible de los libros.
     */
    @FXML
    private TableColumn colStock;

    /**
     * Columna que muestra el identificador de la categoría.
     */
    @FXML
    private TableColumn colIdCategoria;

    /**
     * Columna que muestra el NIT de la editorial.
     */
    @FXML
    private TableColumn colNitEditorial;

    /**
     * Botón utilizado para registrar un nuevo libro.
     */
    @FXML
    private Button btnNuevo;

    /**
     * Botón utilizado para editar el libro seleccionado.
     */
    @FXML
    private Button btnEditar;

    /**
     * Botón utilizado para seleccionar el primer libro.
     */
    @FXML
    private Button btnPrimero;

    /**
     * Botón utilizado para seleccionar el libro anterior.
     */
    @FXML
    private Button btnAnterior;

    /**
     * Botón utilizado para seleccionar el libro siguiente.
     */
    @FXML
    private Button btnSiguiente;

    /**
     * Botón utilizado para seleccionar el último libro.
     */
    @FXML
    private Button btnUltimo;

    /**
     * Campo de texto utilizado para buscar libros.
     */
    @FXML
    private TextField txtBuscar;

    /**
     * Indica si el formulario se encuentra actualmente en modo edición.
     */
    private boolean modoEdicion = false;

    /**
     * DAO utilizado para realizar operaciones relacionadas con los libros.
     */
    private final LibroDao libroDAO = new LibroDAOImpl();

    /**
     * DAO utilizado para consultar las categorías disponibles.
     */
    private final CategoriaDAO categoriaDAO = new CategoriaDAOImpl();

    /**
     * DAO utilizado para consultar las editoriales disponibles.
     */
    private final EditorialDAO editorialDAO = new EditorialDAOImpl();

    /**
     * Lista observable que contiene los libros registrados.
     */
    private final ObservableList<Libro> listaLibros = FXCollections.observableArrayList();

    /**
     * Lista filtrada utilizada para mostrar los resultados de búsqueda.
     */
    private final FilteredList<Libro> librosFiltrados =
            new FilteredList<>(listaLibros, p -> true);

    /**
     * Inicializa el controlador, carga los libros, categorías y editoriales,
     * configura la tabla, la búsqueda y la selección de registros.
     *
     * @param location ubicación utilizada para resolver rutas relativas
     * @param resources recursos utilizados para la localización de la interfaz
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        cargarCombos();
        tablaLibros.setItems(librosFiltrados);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Configura las columnas de la tabla de libros y establece las
     * propiedades del modelo que serán mostradas en cada columna.
     */
    public void configurarTabla() {
        colIsbn.setCellValueFactory(new PropertyValueFactory<Libro, String>("isbn"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<Libro, String>("titulo"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Libro, String>("fechaPublicacion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Libro, Double>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("stock"));
        colIdCategoria.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("idCategoria"));
        colNitEditorial.setCellValueFactory(new PropertyValueFactory<Libro, String>("nitEditorial"));
    }

    /**
     * Carga desde el DAO la lista de libros registrados y la asigna
     * a la lista observable.
     * Si ocurre un error durante la consulta, muestra una alerta.
     */
    private void cargarTabla() {
        try {
            listaLibros.setAll(libroDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Carga las categorías y editoriales disponibles en sus respectivos
     * controles ComboBox.
     * Si ocurre un error durante la consulta, muestra una alerta.
     */
    private void cargarCombos() {
        try {
            cmbCategoria.setItems(FXCollections.observableArrayList(categoriaDAO.listarTodos()));
            cmbEditorial.setItems(FXCollections.observableArrayList(editorialDAO.listarTodos()));
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura el campo de búsqueda para detectar cambios en el texto
     * introducido y ejecutar el filtrado de libros.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener((obs, oldValue, newValue) -> filtrarLibros());
    }

    /**
     * Filtra los libros según el texto introducido en el campo de búsqueda.
     * La búsqueda permite encontrar coincidencias por ISBN, título,
     * fecha de publicación, precio, stock, categoría o editorial.
     */
    private void filtrarLibros() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();
        if (busqueda.isEmpty()) {
            librosFiltrados.setPredicate(p -> true);
        } else {
            librosFiltrados.setPredicate(libro ->
                    libro.getIsbn().toLowerCase().contains(busqueda)
                    || libro.getTitulo().toLowerCase().contains(busqueda)
                    || libro.getFechaPublicacion().toLowerCase().contains(busqueda)
                    || String.valueOf(libro.getPrecio()).contains(busqueda)
                    || String.valueOf(libro.getStock()).contains(busqueda)
                    || String.valueOf(libro.getIdCategoria()).contains(busqueda)
                    || libro.getNitEditorial().toLowerCase().contains(busqueda));
        }
    }

    /**
     * Configura el listener de selección de la tabla para cargar
     * automáticamente los datos del libro seleccionado en el formulario.
     */
    private void seleccionarFila() {
        tablaLibros.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        txtIsbn.setText(newSelection.getIsbn());
                        txtTitulo.setText(newSelection.getTitulo());
                        txtFecha.setText(newSelection.getFechaPublicacion());
                        txtPrecio.setText(String.valueOf(newSelection.getPrecio()));
                        txtStock.setText(String.valueOf(newSelection.getStock()));
                        cmbCategoria.setValue(null);
                        for (Categoria categoria : cmbCategoria.getItems()) {
                            if (categoria.getIdCategoria() == newSelection.getIdCategoria()) {
                                cmbCategoria.setValue(categoria);
                                break;
                            }
                        }
                        cmbEditorial.setValue(null);
                        for (Editorial editorial : cmbEditorial.getItems()) {
                            if (editorial.getNit().equals(newSelection.getNitEditorial())) {
                                cmbEditorial.setValue(editorial);
                                break;
                            }
                        }
                        desactivarFormulario();
                    }
                });
    }

    /**
     * Valida los datos ingresados en el formulario y registra un nuevo libro
     * o actualiza el libro seleccionado dependiendo del modo de edición.
     * Después de guardar correctamente, actualiza la tabla y restablece
     * el estado del formulario.
     */
    @FXML
    private void handleGuardar() {
        try {
            ValidacionException.validarNoVacio(txtIsbn.getText(), "ISBN");
            ValidacionException.validarNoVacio(txtTitulo.getText(), "título");
            ValidacionException.validarNoVacio(txtFecha.getText(), "fecha de publicación");
            ValidacionException.validarNoVacio(txtPrecio.getText(), "precio");
            ValidacionException.validarDecimal(txtPrecio.getText(), "precio");
            ValidacionException.validarNoVacio(txtStock.getText(), "stock");
            ValidacionException.validarNumero(txtStock.getText(), "stock");
            if (Integer.parseInt(txtStock.getText().trim()) < 0) {
                throw new ValidacionException("El campo stock no puede ser negativo.");
            }
            ValidacionException.validarFormatoFecha(txtFecha.getText(),
                    "La fecha de publicación debe tener formato YYYY-MM-DD.");
            ValidacionException.validarNoNulo(cmbCategoria.getValue(),
                    "Seleccione una categoría.");
            ValidacionException.validarNoNulo(cmbEditorial.getValue(),
                    "Seleccione una editorial.");

            Libro libro = new Libro(
                    txtIsbn.getText().trim(),
                    txtTitulo.getText().trim(),
                    txtFecha.getText().trim(),
                    Double.parseDouble(txtPrecio.getText().trim()),
                    cmbCategoria.getValue().getIdCategoria(),
                    cmbEditorial.getValue().getNit(),
                    Integer.parseInt(txtStock.getText().trim()));

            boolean guardado;
            if (modoEdicion) {
                guardado = libroDAO.actualizar(libro);
            } else {
                guardado = libroDAO.crear(libro);
            }

            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Libro actualizado exitosamente."
                        : "Libro registrado exitosamente.");
                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;
            } else {
                mostrarError("No se pudo guardar el libro.");
            }
        } catch (ValidacionException e) {
            mostrarAdvertencia(e.getMessage());
            lblMensaje.setText(e.getMessage());
        } catch (Exception e) {
            mostrarError("Error al guardar: " + e.getMessage());
        }
    }

    /**
     * Cancela la operación actual y restablece el formulario y los
     * controles de navegación a su estado original.
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
     * Prepara el formulario para registrar un nuevo libro.
     * Limpia los campos, activa el formulario y desactiva la navegación.
     */
    @FXML
    private void handleNuevo() {
        modoEdicion = false;
        limpiarFormulario();
        activarFormulario();
        desactivarNavegacion();
        tablaLibros.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        txtIsbn.requestFocus();
    }

    /**
     * Prepara el formulario para editar el libro seleccionado.
     * Si no existe una selección, muestra un mensaje de error.
     */
    @FXML
    private void handleEditar() {
        Libro seleccion = tablaLibros.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarError("Seleccione un libro de la tabla para editar.");
            return;
        }
        modoEdicion = true;
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Selecciona el primer libro disponible en la tabla.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaLibros.getItems().isEmpty()) {
            tablaLibros.getSelectionModel().selectFirst();
            tablaLibros.scrollTo(0);
        }
    }

    /**
     * Selecciona el libro anterior al registro actualmente seleccionado.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaLibros.getItems().isEmpty()) {
            tablaLibros.getSelectionModel().selectPrevious();
            if (tablaLibros.getSelectionModel().getSelectedIndex() >= 0) {
                tablaLibros.scrollTo(tablaLibros.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona el libro siguiente al registro actualmente seleccionado.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaLibros.getItems().isEmpty()) {
            tablaLibros.getSelectionModel().selectNext();
            if (tablaLibros.getSelectionModel().getSelectedIndex() >= 0) {
                tablaLibros.scrollTo(tablaLibros.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona el último libro disponible en la tabla.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaLibros.getItems().isEmpty()) {
            tablaLibros.getSelectionModel().selectLast();
            tablaLibros.scrollTo(tablaLibros.getItems().size() - 1);
        }
    }

    /**
     * Regresa al dashboard correspondiente al rol del usuario actual.
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
     * Limpia todos los campos del formulario y restablece los valores
     * seleccionados de categoría y editorial.
     */
    private void limpiarFormulario() {
        txtIsbn.clear();
        txtTitulo.clear();
        txtFecha.clear();
        txtPrecio.clear();
        txtStock.clear();
        cmbCategoria.setValue(null);
        cmbEditorial.setValue(null);
    }

    /**
     * Activa los campos del formulario para permitir la edición
     * o el registro de información.
     */
    private void activarFormulario() {
        txtIsbn.setDisable(false);
        txtTitulo.setDisable(false);
        txtFecha.setDisable(false);
        txtPrecio.setDisable(false);
        txtStock.setDisable(false);
        cmbCategoria.setDisable(false);
        cmbEditorial.setDisable(false);
    }

    /**
     * Desactiva los campos del formulario para impedir modificaciones
     * mientras no se encuentre en modo de registro o edición.
     */
    private void desactivarFormulario() {
        txtIsbn.setDisable(true);
        txtTitulo.setDisable(true);
        txtFecha.setDisable(true);
        txtPrecio.setDisable(true);
        txtStock.setDisable(true);
        cmbCategoria.setDisable(true);
        cmbEditorial.setDisable(true);
    }

    /**
     * Activa la tabla, los botones de navegación y el campo de búsqueda.
     */
    private void activarNavegacion() {
        tablaLibros.setDisable(false);
        btnNuevo.setDisable(false);
        btnEditar.setDisable(false);
        btnPrimero.setDisable(false);
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        btnUltimo.setDisable(false);
        txtBuscar.setDisable(false);
    }

    /**
     * Desactiva la tabla, los botones de navegación y el campo de búsqueda
     * mientras se realiza una operación de registro o edición.
     */
    private void desactivarNavegacion() {
        tablaLibros.setDisable(true);
        btnNuevo.setDisable(true);
        btnEditar.setDisable(true);
        btnPrimero.setDisable(true);
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnUltimo.setDisable(true);
        txtBuscar.setDisable(true);
    }

    /**
     * Muestra una alerta de tipo error con el mensaje especificado.
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
     * Muestra una alerta de tipo advertencia con el mensaje especificado.
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
