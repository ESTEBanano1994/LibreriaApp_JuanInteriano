package org.libreria.DAOImpl;

/**
 * Representa la separacion entre este codigo y su base de datos SQL
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
import org.libreria.DAO.AutorLibroDAO;
import org.libreria.exception.DaoException;
import org.libreria.model.AutorLibro;
import org.libreria.util.Conexion;

/**
 * Representa la separacion entre este codigo y su base de datos SQL
 * @author Esteban Interiano
 */
public class AutorLibroDAOImpl implements AutorLibroDAO {

    /**
     * obtiene una lista de los libros junto con sus autores, el id del autor y el isbn del libro
     * @return devuelve una lista de todos los autores de libro
     */
    @Override
    public ArrayList<AutorLibro> listarTodos() {
        ArrayList<AutorLibro> lista = new ArrayList<>();
        String sql = "{call sp_listarautoreslibro()}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet rs = consulta.executeQuery()) {
            while (rs.next()) {
                AutorLibro al = new AutorLibro();
                al.setIdAutorLibro(rs.getInt("id_autor_libro"));
                al.setIdAutor(rs.getInt("id_autor"));
                al.setIsbn(rs.getString("isbn"));
                lista.add(al);
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar autores_libro: " + e.getMessage(), e);
        }
        return lista;
    }

    /**
     * obtiene el id del autor libro
     * @param idAutorLibro identificador unico del autorlibro
     * @return devuelve el id de autor libro
     */
    @Override
    public AutorLibro buscarPorId(Integer idAutorLibro) {
        AutorLibro al = null;
        String sql = "{call sp_buscarautorlibro(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, idAutorLibro);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    al = new AutorLibro();
                    al.setIdAutorLibro(rs.getInt("id_autor_libro"));
                    al.setIdAutor(rs.getInt("id_autor"));
                    al.setIsbn(rs.getString("isbn"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al buscar autor_libro: " + e.getMessage(), e);
        }
        return al;
    }

    /**
     * obtiene un valor verdadero o falso para poder crear un nuevo autor libro
     * @param autorLibro persona que escribio el libro
     * @return devuelve la validacion para la creacion de un nuevo autor libro
     */
    @Override
    public boolean crear(AutorLibro autorLibro) {
        String sql = "{call sp_insertarautorlibro(?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, autorLibro.getIdAutor());
            consulta.setString(2, autorLibro.getIsbn());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al insertar autor_libro: " + e.getMessage(), e);
        }
    }

    /**
     * obtiene un valor verdadero o falso para poder hacer una actualizacion de un autorlibro
     * @param autorLibro persona que escribio el libro
     * @return devuelve la validacion para el proceso de actualizacion de autorlibro
     */
    @Override
    public boolean actualizar(AutorLibro autorLibro) {
        String sql = "{call sp_actualizarautorlibro(?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, autorLibro.getIdAutorLibro());
            consulta.setInt(2, autorLibro.getIdAutor());
            consulta.setString(3, autorLibro.getIsbn());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al actualizar autor_libro: " + e.getMessage(), e);
        }
    }

    /**
     *  obtiene un valor verdadero o falso para poder proceder con la eliminacion de un autor libro por medio de su id
     * @param idAutorLibro identificador del autorlibro
     * @return devuelve la validacion para poder eliminar un autorlibro 
     */
    @Override
    public boolean eliminar(Integer idAutorLibro) {
        String sql = "{call sp_eliminarautorlibro(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, idAutorLibro);
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al eliminar autor_libro: " + e.getMessage(), e);
        }
    }
}
