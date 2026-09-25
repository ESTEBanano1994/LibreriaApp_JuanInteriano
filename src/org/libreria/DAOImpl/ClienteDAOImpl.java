package org.libreria.DAOImpl;

/**
 * Implementa las operaciones de acceso a datos relacionadas con las categorias 
 * de la libreria y permite realizar consultas y modificaciones en la base de datos relacionadas a ClienteDaoImpl
 * 
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.AutorLibro
 */
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.libreria.dao.ClienteDAO;
import org.libreria.exception.DaoException;
import org.libreria.model.Cliente;
import org.libreria.util.Conexion;

/**
 *
 * @author Esteban Interiano 
 */
public class ClienteDAOImpl implements ClienteDAO {

    /**
     * Obtiene una lista de los clientes junto con sus datos
     * @return devuelve la lista de clientes mostrando sus nombres, apellidos y correos electronicos
     */
    @Override
    public ArrayList<Cliente> listarTodos() {
        ArrayList<Cliente> lista = new ArrayList<>();
        String sql = "{call sp_listarclientes()}";
        try (Connection conexion = Conexion.getInstancia().conectar(); CallableStatement consulta = conexion.prepareCall(sql); ResultSet rs = consulta.executeQuery()) {
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setCui(rs.getLong("cui"));
                c.setNombreCliente(rs.getString("nombre_cliente"));
                c.setApellidoCliente(rs.getString("apellido_cliente"));
                c.setCorreoElectronico(rs.getString("correo_electronico"));
                lista.add(c);
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar clientes: " + e.getMessage(), e);
        }
        return lista;
    }

    /**
     * Obtiene el cui del cliente
     * @param cui identificador numerario unico por cliente en su factura
     * @return devuelve el cui del cliente
     */
    @Override
    public Cliente buscarPorId(Long cui) {
        Cliente c = null;
        String sql = "{call sp_buscarcliente(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar(); CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setLong(1, cui);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    c = new Cliente();
                    c.setCui(rs.getLong("cui"));
                    c.setNombreCliente(rs.getString("nombre_cliente"));
                    c.setApellidoCliente(rs.getString("apellido_cliente"));
                    c.setCorreoElectronico(rs.getString("correo_electronico"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al buscar cliente: " + e.getMessage(), e);
        }
        return c;
    }

    /**
     * obtiene un dato verdadero o falso para poder crear un cliente
     * @param cliente comprador
     * @return devuelve la validacion para póder crear un nuevo cliente en la base de datos
     */
    @Override
    public boolean crear(Cliente cliente) {
        String sql = "{call sp_insertarcliente(?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar(); CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setLong(1, cliente.getCui());
            consulta.setString(2, cliente.getNombreCliente());
            consulta.setString(3, cliente.getApellidoCliente());
            consulta.setString(4, cliente.getCorreoElectronico());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al insertar cliente: " + e.getMessage(), e);
        }
    }

    /**
     * obtiene un valor verdadero o falso para poder actualizar datos de un cliente
     * @param cliente comprador
     * @return devuelve la validacion para poder realizar una actualizacion a un cliente
     */
    @Override
    public boolean actualizar(Cliente cliente) {
        String sql = "{call sp_actualizarcliente(?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar(); CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setLong(1, cliente.getCui());
            consulta.setString(2, cliente.getNombreCliente());
            consulta.setString(3, cliente.getApellidoCliente());
            consulta.setString(4, cliente.getCorreoElectronico());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al actualizar cliente: " + e.getMessage(), e);
        }
    }

    /**
     * obtiene un valor verdadero o falso para poder realizar la elminacion de un cui
     * @param cui identificador numeral unico del cliente
     * @return devuelve la validacion para realizar la eliminacion de un cui
     */
    @Override
    public boolean eliminar(Long cui) {
        String sql = "{call sp_eliminarcliente(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar(); CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setLong(1, cui);
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al eliminar cliente: " + e.getMessage(), e);
        }
    }
}