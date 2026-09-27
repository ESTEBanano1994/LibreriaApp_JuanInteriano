package org.libreria.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.libreria.DAO.LibroDao;
import org.libreria.DAOImpl.LibroDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.model.Libro;
import org.libreria.system.Main;

/**
 * Controlador encargado de gestionar la vista del inventario de libros.
 * Permite cargar los libros registrados, mostrarlos en una tabla,
 * realizar búsquedas y regresar al dashboard correspondiente.
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.Libro
 * @see org.libreria.DAO.LibroDao
 * @see org.libreria.DAOImpl.LibroDAOImpl
 * @see org.libreria.exception.DaoException
 * @see org.libreria.system.Main
 */
public class InventarioController implements Initializable {

    /**
     * Tabla donde se muestran los libros disponibles en el inventario.
     */
    @FXML
    private TableView<Libro> tablaInventario;

    /**
     * Columna que muestra el ISBN de cada libro.
     */
    @FXML
    private TableColumn colIsbn;

    /**
     * Columna que muestra el título de cada libro.
     */
    @FXML
    private TableColumn colTitulo;

    /**
     * Columna que muestra el precio de cada libro.
     */
    @FXML
    private TableColumn colPrecio;

    /**
     * Columna que muestra la cantidad disponible de cada libro.
     */
    @FXML
    private TableColumn colStock;

    /**
     * Campo de texto utilizado para realizar búsquedas en el inventario.
     */
    @FXML
    private TextField txtBuscar;

    /**
     * DAO utilizado para consultar la información de los libros.
     */
    private final LibroDao libroDAO = new LibroDAOImpl();

    /**
     * Lista observable que contiene todos los libros obtenidos desde
     * la base de datos.
     */
    private final ObservableList<Libro> listaLibros = FXCollections.observableArrayList();

    /**
     * Lista filtrada utilizada para mostrar únicamente los libros
     * que coinciden con el criterio de búsqueda.
     */
    private final FilteredList<Libro> librosFiltrados = new FilteredList<>(listaLibros, p -> true);

    /**
     * Inicializa el controlador, carga los libros del inventario,
     * configura la tabla y establece el funcionamiento de la búsqueda.
     *
     * @param location ubicación utilizada para resolver rutas relativas
     * @param resources recursos utilizados para la localización de la interfaz
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        tablaInventario.setItems(librosFiltrados);
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Configura las columnas de la tabla del inventario y establece
     * las propiedades del modelo que serán mostradas en cada columna.
     */
    public void configurarTabla() {
        colIsbn.setCellValueFactory(new PropertyValueFactory<Libro, String>("isbn"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<Libro, String>("titulo"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Libro, Double>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("stock"));
    }

    /**
     * Carga desde el DAO la lista de libros registrados y la asigna
     * a la lista observable del inventario.
     * Si ocurre un error durante la consulta, se muestra una alerta.
     */
    private void cargarTabla() {
        try {
            listaLibros.setAll(libroDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura el campo de búsqueda para detectar cambios en el texto
     * introducido por el usuario y ejecutar el filtrado de libros.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener((obs, oldValue, newValue) -> filtrarLibros());
    }

    /**
     * Filtra los libros del inventario utilizando el texto introducido
     * en el campo de búsqueda.
     * La búsqueda permite encontrar coincidencias por ISBN, título,
     * precio o cantidad de stock.
     */
    private void filtrarLibros() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();
        if (busqueda.isEmpty()) {
            librosFiltrados.setPredicate(p -> true);
        } else {
            librosFiltrados.setPredicate(libro ->
                    libro.getIsbn().toLowerCase().contains(busqueda)
                    || libro.getTitulo().toLowerCase().contains(busqueda)
                    || String.valueOf(libro.getPrecio()).contains(busqueda)
                    || String.valueOf(libro.getStock()).contains(busqueda));
        }
    }

    /**
     * Regresa al dashboard correspondiente al rol del usuario actual.
     * Si ocurre un error durante la navegación, se muestra una alerta.
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
     * Muestra una alerta de error con el mensaje especificado.
     *
     * @param mensaje mensaje que será mostrado en la alerta
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}

