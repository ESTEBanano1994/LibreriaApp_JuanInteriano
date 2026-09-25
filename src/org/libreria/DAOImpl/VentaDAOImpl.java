package org.libreria.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.libreria.dao.DetalleVentaDAO;
import org.libreria.dao.VentaDAO;
import org.libreria.exception.DaoException;
import org.libreria.model.DetalleVenta;
import org.libreria.model.LineaVenta;
import org.libreria.model.Venta;
import org.libreria.util.Conexion;

/**
 * Implementa las operaciones de acceso a datos relacionadas con las ventas
 * de la libreria y permite realizar consultas, modificaciones y eliminaciones
 * de registros en la base de datos.
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.Venta
 * @see org.libreria.dao.VentaDAO
 */
public class VentaDAOImpl implements VentaDAO {

    private final DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAOImpl();

    /**
     * Obtiene una lista con todas las ventas registradas en la base de datos.
     *
     * @return devuelve una lista con todas las ventas disponibles
     */
    @Override
    public ArrayList<Venta> listarTodos() {
        ArrayList<Venta> lista = new ArrayList<>();
        String sql = "{call sp_listar_ventas()}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet rs = consulta.executeQuery()) {
            while (rs.next()) {
                Venta v = new Venta();
                v.setNoVenta(rs.getInt("no_venta"));
                v.setFechaVenta(rs.getString("fecha_venta"));
                v.setTotalVenta(rs.getDouble("total_venta"));
                v.setCuiCliente(rs.getLong("cui_cliente"));
                v.setIdUsuario(rs.getInt("id_usuario"));
                lista.add(v);
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar ventas: " + e.getMessage(), e);
        }
        return lista;
    }

    /**
     * Busca una venta utilizando su numero de venta como identificador.
     *
     * @param noVenta identificador unico de la venta
     * @return devuelve la venta encontrada o null si no existe
     */
    @Override
    public Venta buscarPorId(Integer noVenta) {
        Venta v = null;
        String sql = "{call sp_buscar_venta(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, noVenta);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    v = new Venta();
                    v.setNoVenta(rs.getInt("no_venta"));
                    v.setFechaVenta(rs.getString("fecha_venta"));
                    v.setTotalVenta(rs.getDouble("total_venta"));
                    v.setCuiCliente(rs.getLong("cui_cliente"));
                    v.setIdUsuario(rs.getInt("id_usuario"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al buscar venta: " + e.getMessage(), e);
        }
        return v;
    }

    /**
     * Registra una nueva venta en la base de datos.
     *
     * @param venta venta que se desea registrar
     * @return devuelve true si la venta fue creada correctamente
     */
    @Override
    public boolean crear(Venta venta) {
        String sql = "{call sp_insertar_venta(?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setDouble(1, venta.getTotalVenta());
            consulta.setString(2, String.valueOf(venta.getCuiCliente()));
            consulta.setInt(3, venta.getIdUsuario());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al insertar venta: " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza los datos de una venta existente en la base de datos.
     *
     * @param venta venta que contiene los datos que se desean actualizar
     * @return devuelve true si la venta fue actualizada correctamente
     */
    @Override
    public boolean actualizar(Venta venta) {
        String sql = "{call sp_actualizar_venta(?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, venta.getNoVenta());
            consulta.setDouble(2, venta.getTotalVenta());
            consulta.setLong(3, venta.getCuiCliente());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al actualizar venta: " + e.getMessage(), e);
        }
    }

    /**
     * Registra una venta junto con sus lineas de detalle y descuenta
     * las cantidades correspondientes del stock de cada libro.
     *
     * @param venta venta que contiene la informacion general de la operacion
     * @param lineas lista de lineas de venta que forman parte de la venta
     * @return devuelve el numero de venta generado o -1 si no fue posible crearla
     */
    @Override
    public int crearVenta(Venta venta, List<LineaVenta> lineas) {
        int noVenta = -1;
        String sql = "{call sp_insertar_venta(?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setDouble(1, venta.getTotalVenta());
            consulta.setString(2, String.valueOf(venta.getCuiCliente()));
            consulta.setInt(3, venta.getIdUsuario());
            int filasAfectadas = consulta.executeUpdate();
            if (filasAfectadas > 0) {
                try (Statement sentencia = conexion.createStatement();
                        ResultSet rs = sentencia.executeQuery("SELECT LAST_INSERT_ID()")) {
                    if (rs.next()) {
                        noVenta = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al insertar venta: " + e.getMessage(), e);
        }

        if (noVenta > 0) {
            for (LineaVenta linea : lineas) {
                DetalleVenta detalle = new DetalleVenta(0, noVenta,
                        linea.getIsbn(), linea.getCantidad(), linea.getPrecio());
                detalleVentaDAO.crear(detalle);
                descontarStock(linea.getIsbn(), linea.getCantidad());
            }
        }
        return noVenta;
    }

    /**
     * Descuenta del inventario la cantidad de libros correspondiente
     * a una linea de venta.
     *
     * @param isbn identificador del libro al que se le descontara stock
     * @param cantidad cantidad de unidades que se desea descontar
     * @return devuelve true si el stock fue descontado correctamente
     */
    private boolean descontarStock(String isbn, int cantidad) {
        String sql = "{call sp_descontarstock(?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setString(1, isbn);
            consulta.setInt(2, cantidad);
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al descontar stock: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina una venta de la base de datos utilizando su numero de venta.
     *
     * @param noVenta identificador unico de la venta que se desea eliminar
     * @return devuelve true si la venta fue eliminada correctamente
     */
    @Override
    public boolean eliminar(Integer noVenta) {
        String sql = "{call sp_eliminar_venta(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, noVenta);
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al eliminar venta: " + e.getMessage(), e);
        }
    }
}

