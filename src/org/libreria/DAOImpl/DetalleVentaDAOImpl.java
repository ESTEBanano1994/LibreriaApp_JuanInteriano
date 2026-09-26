package org.libreria.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.libreria.DAO.DetalleVentaDAO;
import org.libreria.exception.DaoException;
import org.libreria.model.DetalleVenta;
import org.libreria.util.Conexion;

/**
 * Implementa las operaciones de acceso a datos relacionadas con los detalles
 * de las ventas y permite realizar consultas y modificaciones en la base de datos.
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.DetalleVenta
 * @see org.libreria.DAO.DetalleVentaDAO
 */
public class DetalleVentaDAOImpl implements DetalleVentaDAO {

    /**
     * Obtiene una lista con todos los detalles de ventas registrados
     * en la base de datos.
     *
     * @return devuelve una lista con todos los detalles de ventas registrados
     */
    @Override
    public ArrayList<DetalleVenta> listarTodos() {
        ArrayList<DetalleVenta> lista = new ArrayList<>();
        String sql = "{call sp_listar_detalle_venta()}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet rs = consulta.executeQuery()) {
            while (rs.next()) {
                DetalleVenta dv = new DetalleVenta();
                dv.setIdDetalleVenta(rs.getInt("id_detalle_venta"));
                dv.setNoVenta(rs.getInt("no_venta"));
                dv.setIsbn(rs.getString("isbn"));
                dv.setCantidad(rs.getInt("cantidad"));
                dv.setPrecio(rs.getDouble("precio"));
                lista.add(dv);
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar detalle_venta: " + e.getMessage(), e);
        }
        return lista;
    }

    /**
     * Busca un detalle de venta utilizando su identificador.
     *
     * @param idDetalleVenta identificador unico del detalle de venta
     * @return devuelve el detalle de venta encontrado o null si no existe
     */
    @Override
    public DetalleVenta buscarPorId(Integer idDetalleVenta) {
        DetalleVenta dv = null;
        String sql = "{call sp_buscar_detalle_venta(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, idDetalleVenta);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    dv = new DetalleVenta();
                    dv.setIdDetalleVenta(rs.getInt("id_detalle_venta"));
                    dv.setNoVenta(rs.getInt("no_venta"));
                    dv.setIsbn(rs.getString("isbn"));
                    dv.setCantidad(rs.getInt("cantidad"));
                    dv.setPrecio(rs.getDouble("precio"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al buscar detalle_venta: " + e.getMessage(), e);
        }
        return dv;
    }

    /**
     * Registra un nuevo detalle de venta en la base de datos.
     *
     * @param detalleVenta detalle de venta que se desea registrar
     * @return devuelve true si el detalle de venta fue creado correctamente
     */
    @Override
    public boolean crear(DetalleVenta detalleVenta) {
        String sql = "{call sp_insertar_detalle_venta(?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, detalleVenta.getNoVenta());
            consulta.setString(2, detalleVenta.getIsbn());
            consulta.setInt(3, detalleVenta.getCantidad());
            consulta.setDouble(4, detalleVenta.getPrecio());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al insertar detalle_venta: " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza los datos de un detalle de venta existente en la base de datos.
     *
     * @param detalleVenta detalle de venta que contiene los datos que se desean actualizar
     * @return devuelve true si el detalle de venta fue actualizado correctamente
     */
    @Override
    public boolean actualizar(DetalleVenta detalleVenta) {
        String sql = "{call sp_actualizar_detalle_venta(?,?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, detalleVenta.getIdDetalleVenta());
            consulta.setInt(2, detalleVenta.getNoVenta());
            consulta.setString(3, detalleVenta.getIsbn());
            consulta.setInt(4, detalleVenta.getCantidad());
            consulta.setDouble(5, detalleVenta.getPrecio());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al actualizar detalle_venta: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina un detalle de venta de la base de datos utilizando su identificador.
     *
     * @param idDetalleVenta identificador unico del detalle de venta que se desea eliminar
     * @return devuelve true si el detalle de venta fue eliminado correctamente
     */
    @Override
    public boolean eliminar(Integer idDetalleVenta) {
        String sql = "{call sp_eliminar_detalle_venta(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, idDetalleVenta);
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al eliminar detalle_venta: " + e.getMessage(), e);
        }
    }
}

