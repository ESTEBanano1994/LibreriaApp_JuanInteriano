package org.libreria.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.libreria.dao.CategoriaDAO;
import org.libreria.exception.DaoException;
import org.libreria.model.Categoria;
import org.libreria.util.Conexion;

/**
 * Implementa las operaciones de acceso a datos relacionadas con las categorias
 * de la libreria y permite realizar consultas y modificaciones en la base de datos.
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.model.Categoria
 * @see org.libreria.dao.CategoriaDAO
 */
public class CategoriaDAOImpl implements CategoriaDAO {

    /**
     * Obtiene una lista con todas las categorias registradas en la base de datos.
     *
     * @return devuelve una lista con todas las categorias disponibles
     */
    @Override
    public ArrayList<Categoria> listarTodos() {
        ArrayList<Categoria> lista = new ArrayList<>();
        String sql = "{call sp_listarcategorias()}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet rs = consulta.executeQuery()) {
            while (rs.next()) {
                Categoria c = new Categoria();
                c.setIdCategoria(rs.getInt("id_categoria"));
                c.setNombreCategoria(rs.getString("nombre_categoria"));
                lista.add(c);
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar categorias: " + e.getMessage(), e);
        }
        return lista;
    }

    /**
     * Busca una categoria registrada utilizando su identificador.
     *
     * @param idCategoria identificador unico de la categoria
     * @return devuelve la categoria encontrada o null si no existe
     */
    @Override
    public Categoria buscarPorId(Integer idCategoria) {
        Categoria c = null;
        String sql = "{call sp_buscarcategoria(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, idCategoria);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    c = new Categoria();
                    c.setIdCategoria(rs.getInt("id_categoria"));
                    c.setNombreCategoria(rs.getString("nombre_categoria"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al buscar categoria: " + e.getMessage(), e);
        }
        return c;
    }

    /**
     * Registra una nueva categoria en la base de datos.
     *
     * @param categoria categoria que se desea registrar
     * @return devuelve true si la categoria fue creada correctamente
     */
    @Override
    public boolean crear(Categoria categoria) {
        String sql = "{call sp_insertarcategoria(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setString(1, categoria.getNombreCategoria());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al insertar categoria: " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza los datos de una categoria existente en la base de datos.
     *
     * @param categoria categoria que contiene los datos que se desean actualizar
     * @return devuelve true si la categoria fue actualizada correctamente
     */
    @Override
    public boolean actualizar(Categoria categoria) {
        String sql = "{call sp_actualizarcategoria(?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, categoria.getIdCategoria());
            consulta.setString(2, categoria.getNombreCategoria());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al actualizar categoria: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina una categoria de la base de datos utilizando su identificador.
     *
     * @param idCategoria identificador unico de la categoria que se desea eliminar
     * @return devuelve true si la categoria fue eliminada correctamente
     */
    @Override
    public boolean eliminar(Integer idCategoria) {
        String sql = "{call sp_eliminarcategoria(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
