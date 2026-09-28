package org.libreria.controller;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import org.libreria.DAO.UsuarioDAO;
import org.libreria.DAOImpl.UsuarioDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.exception.ValidacionException;
import org.libreria.manager.SesionContext;
import org.libreria.model.Usuario;
import org.libreria.system.Main;
import org.libreria.util.SecurityUtil;

/**
 * Controlador encargado de gestionar los usuarios del sistema.
 * Permite registrar, editar, buscar, cambiar contraseñas, desactivar
 * y eliminar usuarios, además de controlar la navegación entre los
 * registros mostrados en la tabla.
 *
 * @author Juan Esteban Interiano Riera
 * @version 1.0.0
 */
public class UsuarioController implements Initializable {

    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private ComboBox<String> cmbRol;
    @FXML
    private CheckBox chkActivo;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblMensaje;
    @FXML
    private TableView<Usuario> tablaUsuarios;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colUsername;
    @FXML
    private TableColumn colEmail;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellido;
    @FXML
    private TableColumn colRol;
    @FXML
    private TableColumn colActivo;
    @FXML
    private TableColumn colFecha;
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
    private Button btnCambiarPassword;
    @FXML
    private Button btnDesactivar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TextField txtBuscar;

    private boolean modoEdicion = false;
    private Usuario enEdicion;
    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
    private final ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    private final FilteredList<Usuario> usuariosFiltrados = new FilteredList<>(listaUsuarios, p -> true);

    /**
     * Inicializa los componentes de la vista, configura los roles
     * disponibles, carga los usuarios y establece los eventos
     * necesarios para la interacción con la tabla y el formulario.
     *
     * @param location ubicación utilizada para resolver rutas relativas
     * @param resources recursos utilizados por la vista
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbRol.setItems(FXCollections.observableArrayList("admin", "empleado", "cajero"));
        cargarTabla();
        tablaUsuarios.setItems(usuariosFiltrados);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
        desactivarFormulario();
    }

    /**
     * Configura las columnas de la tabla de usuarios y establece
     * las propiedades del modelo que serán mostradas.
     */
    public void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<Usuario, String>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Usuario, String>("email"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Usuario, String>("firstName"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Usuario, String>("lastName"));
        colRol.setCellValueFactory(new PropertyValueFactory<Usuario, String>("rol"));
        colActivo.setCellValueFactory(new PropertyValueFactory<Usuario, Boolean>("activo"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Usuario, Timestamp>("fechaCreacion"));
    }

    /**
     * Obtiene todos los usuarios mediante el DAO y los carga
     * en la lista utilizada por la tabla.
     */
    private void cargarTabla() {
        try {
            listaUsuarios.setAll(usuarioDAO.listarTodosUsuarios());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura el listener del campo de búsqueda para actualizar
     * el filtro de usuarios cada vez que cambia el texto ingresado.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener((obs, oldValue, newValue) -> filtrarUsuarios());
    }

    /**
     * Filtra los usuarios de acuerdo con el texto ingresado.
     * La búsqueda se realiza sobre el identificador, nombre de usuario,
     * correo electrónico y rol.
     */
    private void filtrarUsuarios() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();
        if (busqueda.isEmpty()) {
            usuariosFiltrados.setPredicate(p -> true);
        } else {
            usuariosFiltrados.setPredicate(usuario ->
                    String.valueOf(usuario.getId()).contains(busqueda)
                    || usuario.getUsername().toLowerCase().contains(busqueda)
                    || (usuario.getEmail() != null && usuario.getEmail().toLowerCase().contains(busqueda))
                    || usuario.getRol().toLowerCase().contains(busqueda));
        }
    }

    /**
     * Configura el evento de selección de una fila de la tabla.
     * Cuando se selecciona un usuario, sus datos se muestran en
     * el formulario y este queda desactivado.
     */
    private void seleccionarFila() {
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        mostrarEnFormulario(newSelection);
                        desactivarFormulario();
                    }
                });
    }

    /**
     * Muestra los datos del usuario seleccionado en los controles
     * correspondientes del formulario.
     *
     * @param usuario usuario cuyos datos serán mostrados
     */
    private void mostrarEnFormulario(Usuario usuario) {
        txtUsername.setText(usuario.getUsername());
        txtEmail.setText(usuario.getEmail());
        txtNombre.setText(usuario.getFirstName());
        txtApellido.setText(usuario.getLastName());
        cmbRol.setValue(usuario.getRol());
        chkActivo.setSelected(usuario.isActivo());
        txtPassword.clear();
    }

    /**
     * Valida y guarda los datos ingresados en el formulario.
     * Dependiendo del modo actual, registra un nuevo usuario o
     * actualiza los datos de un usuario existente.
     */
    @FXML
    private void handleGuardar() {
        try {
            ValidacionException.validarNoVacio(txtUsername.getText(), "username");
            ValidacionException.validarNoVacio(txtEmail.getText(), "correo electrónico");
            ValidacionException.validarFormatoEmail(txtEmail.getText(), "El correo electrónico no es válido.");
            ValidacionException.validarNoNulo(cmbRol.getValue(), "Debe seleccionar un rol.");

            Usuario usuario = new Usuario();
            usuario.setId(modoEdicion ? enEdicion.getId() : 0);
            usuario.setUsername(txtUsername.getText().trim());
            usuario.setEmail(txtEmail.getText().trim());
            usuario.setFirstName(txtNombre.getText().trim());
            usuario.setLastName(txtApellido.getText().trim());
            usuario.setRol(cmbRol.getValue());
            usuario.setActivo(chkActivo.isSelected());

            boolean guardado;
            if (modoEdicion) {
                guardado = usuarioDAO.actualizarUsuario(usuario);
            } else {
                ValidacionException.validarNoVacio(txtPassword.getText(), "contraseña");
                ValidacionException.validarLongitudMinima(txtPassword.getText(), 6,
                        "La contraseña debe tener al menos 6 caracteres.");
                usuario.setPasswordHash(SecurityUtil.hashSHA256(txtPassword.getText()));
                guardado = usuarioDAO.crearUsuario(usuario);
            }

            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Usuario actualizado exitosamente."
                        : "Usuario registrado exitosamente.");
                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;
                enEdicion = null;
            } else {
                mostrarError("No se pudo guardar el usuario.");
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
     * Prepara el formulario para registrar un nuevo usuario.
     * Establece el rol de empleado y activa los controles del formulario.
     */
    @FXML
    private void handleNuevo() {
        modoEdicion = false;
        enEdicion = null;
        limpiarFormulario();
        chkActivo.setSelected(true);
        cmbRol.setValue("empleado");
        activarFormulario();
        desactivarNavegacion();
        tablaUsuarios.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        txtUsername.requestFocus();
    }

    /**
     * Prepara el formulario para editar el usuario seleccionado.
     * Muestra un mensaje de error si no existe un usuario seleccionado.
     */
    @FXML
    private void handleEditar() {
        Usuario seleccion = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarError("Seleccione un usuario de la tabla para editar.");
            return;
        }
        modoEdicion = true;
        enEdicion = seleccion;
        mostrarEnFormulario(seleccion);
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Solicita una nueva contraseña para el usuario seleccionado,
     * valida su contenido, genera su hash y actualiza la contraseña
     * mediante el DAO.
     */
    @FXML
    private void handleCambiarPassword() {
        Usuario seleccion = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarError("Seleccione un usuario para cambiar la contraseña.");
            return;
        }

        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Cambiar Contraseña");
        dialogo.setHeaderText("Nueva contraseña para: " + seleccion.getUsername());
        dialogo.setContentText("Contraseña:");
        dialogo.showAndWait().ifPresent(password -> {
            try {
                ValidacionException.validarNoVacio(password, "contraseña");
                ValidacionException.validarLongitudMinima(password, 6,
                        "La contraseña debe tener al menos 6 caracteres.");
                String hash = SecurityUtil.hashSHA256(password);
                if (usuarioDAO.cambiarPassword(seleccion.getId(), hash)) {
                    lblMensaje.setText("Contraseña actualizada exitosamente.");
                } else {
                    mostrarError("No se pudo cambiar la contraseña.");
                }
            } catch (ValidacionException e) {
                mostrarAdvertencia(e.getMessage());
            } catch (DaoException e) {
                mostrarError(e.getMessage());
            }
        });
    }

    /**
     * Desactiva el usuario seleccionado después de solicitar
     * confirmación al usuario actual.
     */
    @FXML
    private void handleDesactivar() {
        Usuario seleccion = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarError("Seleccione un usuario para desactivar.");
            return;
        }

        if (esUsuarioActual(seleccion)) {
            mostrarError("No puede desactivar su propio usuario.");
            return;
        }

        if (!confirmar("Desactivar usuario", "¿Desea desactivar al usuario " + seleccion.getUsername() + "?")) {
            return;
        }

        try {
            if (usuarioDAO.desactivarUsuario(seleccion.getId())) {
                lblMensaje.setText("Usuario desactivado exitosamente.");
                cargarTabla();
            } else {
                mostrarError("No se pudo desactivar el usuario.");
            }
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Elimina definitivamente el usuario seleccionado después
     * de solicitar confirmación y verificar que no sea el usuario actual.
     */
    @FXML
    private void handleEliminar() {
        Usuario seleccion = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarError("Seleccione un usuario para eliminar.");
            return;
        }

        if (esUsuarioActual(seleccion)) {
            mostrarError("No puede eliminar su propio usuario.");
            return;
        }

        if (!confirmar("Eliminar usuario",
                "¿Desea eliminar definitivamente al usuario " + seleccion.getUsername() + "?")) {
            return;
        }

        try {
            if (usuarioDAO.eliminarUsuario(seleccion.getId())) {
                lblMensaje.setText("Usuario eliminado exitosamente.");
                cargarTabla();
            } else {
                mostrarError("No se pudo eliminar el usuario.");
            }
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Verifica si el usuario proporcionado corresponde al usuario
     * que actualmente tiene iniciada la sesión.
     *
     * @param usuario usuario que será comparado con el usuario actual
     * @return true si corresponde al usuario actual; false en caso contrario
     */
    private boolean esUsuarioActual(Usuario usuario) {
        Usuario actual = SesionContext.getInstancia().getUsuarioActual();
        return actual != null && actual.getId() == usuario.getId();
    }

    /**
     * Selecciona el primer usuario disponible en la tabla.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaUsuarios.getItems().isEmpty()) {
            tablaUsuarios.getSelectionModel().selectFirst();
            tablaUsuarios.scrollTo(0);
        }
    }

    /**
     * Selecciona el usuario anterior al registro actualmente seleccionado.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaUsuarios.getItems().isEmpty()) {
            tablaUsuarios.getSelectionModel().selectPrevious();
            if (tablaUsuarios.getSelectionModel().getSelectedIndex() >= 0) {
                tablaUsuarios.scrollTo(tablaUsuarios.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona el usuario siguiente al registro actualmente seleccionado.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaUsuarios.getItems().isEmpty()) {
            tablaUsuarios.getSelectionModel().selectNext();
            if (tablaUsuarios.getSelectionModel().getSelectedIndex() >= 0) {
                tablaUsuarios.scrollTo(tablaUsuarios.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona el último usuario disponible en la tabla.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaUsuarios.getItems().isEmpty()) {
            tablaUsuarios.getSelectionModel().selectLast();
            tablaUsuarios.scrollTo(tablaUsuarios.getItems().size() - 1);
        }
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
     * Limpia todos los campos del formulario de usuarios.
     */
    private void limpiarFormulario() {
        txtUsername.clear();
        txtEmail.clear();
        txtNombre.clear();
        txtApellido.clear();
        cmbRol.setValue(null);
        chkActivo.setSelected(false);
        txtPassword.clear();
    }

    /**
     * Habilita los controles del formulario para permitir
     * el ingreso o modificación de información.
     */
    private void activarFormulario() {
        txtUsername.setDisable(false);
        txtEmail.setDisable(false);
        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        cmbRol.setDisable(false);
        chkActivo.setDisable(false);
        txtPassword.setDisable(modoEdicion);
    }

    /**
     * Deshabilita todos los controles del formulario de usuarios.
     */
    private void desactivarFormulario() {
        txtUsername.setDisable(true);
        txtEmail.setDisable(true);
        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        cmbRol.setDisable(true);
        chkActivo.setDisable(true);
        txtPassword.setDisable(true);
    }

    /**
     * Habilita la tabla, botones de navegación, acciones de usuario
     * y campo de búsqueda.
     */
    private void activarNavegacion() {
        tablaUsuarios.setDisable(false);
        btnNuevo.setDisable(false);
        btnEditar.setDisable(false);
        btnPrimero.setDisable(false);
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        btnUltimo.setDisable(false);
        btnCambiarPassword.setDisable(false);
        btnDesactivar.setDisable(false);
        btnEliminar.setDisable(false);
        txtBuscar.setDisable(false);
    }

    /**
     * Deshabilita la tabla, botones de navegación, acciones de usuario
     * y campo de búsqueda mientras se realiza una operación en el formulario.
     */
    private void desactivarNavegacion() {
        tablaUsuarios.setDisable(true);
        btnNuevo.setDisable(true);
        btnEditar.setDisable(true);
        btnPrimero.setDisable(true);
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnUltimo.setDisable(true);
        btnCambiarPassword.setDisable(true);
        btnDesactivar.setDisable(true);
        btnEliminar.setDisable(true);
        txtBuscar.setDisable(true);
    }

    /**
     * Muestra un cuadro de confirmación y obtiene la respuesta del usuario.
     *
     * @param titulo título mostrado en la ventana de confirmación
     * @param mensaje mensaje que será presentado al usuario
     * @return true si el usuario selecciona la opción Sí; false en caso contrario
     */
    private boolean confirmar(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, mensaje, ButtonType.YES, ButtonType.NO);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        return alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
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
