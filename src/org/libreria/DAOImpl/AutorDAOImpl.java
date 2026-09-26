package org.libreria.DAOImpl;

/**
 * Representa la validacion de datos del AutorDAO y su conexion con DAOImpl
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.DAOImpl.AutorDAOImpl
 */
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.libreria.DAO.AutorDAO;
import org.libreria.exception.DaoException;
import org.libreria.model.Autor;
import org.libreria.util.Conexion;

/**
 * Representa la validacion de datos del AutorDAO y su conexion con DAOImpl
 * @author Esteban Interiano
 */
public class AutorDAOImpl implements AutorDAO {

    /**
     *
     * @return
     */
    @Override
    public ArrayList<Autor> listarTodos() {
        ArrayList<Autor> lista = new ArrayList<>();
        String sql = "{call sp_listarautores()}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet rs = consulta.executeQuery()) {
            while (rs.next()) {
                Autor a = new Autor();
                a.setIdAutor(rs.getInt("id_autor"));
                a.setNombreAutor(rs.getString("nombre_autor"));
                a.setApellidoAutor(rs.getString("apellido_autor"));
                a.setNacionalidad(rs.getString("nacionalidad"));
                a.setBiografia(rs.getString("biografia"));
                lista.add(a);
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar autores: " + e.getMessage(), e);
        }
        return lista;
    }

    /**
     * obtiene el identificador del autor para realizar una busqueda
     * obtiene los datos del autor, nombre, apellido, nacionalidad y biografia
     * @param idAutor identificador unico del autor
     * @return devuelve al autor buscado junto con sus datos
     */
    @Override
    public Autor buscarPorId(Integer idAutor) {
        Autor a = null;
        String sql = "{call sp_buscarautor(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, idAutor);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    a = new Autor();
                    a.setIdAutor(rs.getInt("id_autor"));
                    a.setNombreAutor(rs.getString("nombre_autor"));
                    a.setApellidoAutor(rs.getString("apellido_autor"));
                    a.setNacionalidad(rs.getString("nacionalidad"));
                    a.setBiografia(rs.getString("biografia"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al buscar autor: " + e.getMessage(), e);
        }
        return a;
    }

    /**
     * obtiene un valor verdadero o falso para poder crear un nuevo autor
     * para crear el nuevo autor se busca un nombre, apellido, nacionalidad y biografia
     * @param autor escritor de un libro
     * @return devuelve el valor verdero falso para la creacion de un nuevo autor
     */
    @Override
    public boolean crear(Autor autor) {
        String sql = "{call sp_insertarautor(?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setString(1, autor.getNombreAutor());
            consulta.setString(2, autor.getApellidoAutor());
            consulta.setString(3, autor.getNacionalidad());
            consulta.setString(4, autor.getBiografia());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al insertar autor: " + e.getMessage(), e);
        }
    }

    /**
     * obtiene un valor verdadero o falso para poder actualizar los datos de un autor
     * @param autor escritor del libro y persona a quien se le hacen las modificaciones
     * @return devuelve las modificaciones realizadas al autor
     */
    @Override
    public boolean actualizar(Autor autor) {
        String sql = "{call sp_actualizarautor(?,?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, autor.getIdAutor());
            consulta.setString(2, autor.getNombreAutor());
            consulta.setString(3, autor.getApellidoAutor());
            consulta.setString(4, autor.getNacionalidad());
            consulta.setString(5, autor.getBiografia());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al actualizar autor: " + e.getMessage(), e);
        }
    }

    /**
     * obtiene el valor verdadero o falso para poder realizar la operacion de eliminar un autor en base a su id :)
     * @param idAutor identificador unico del autor
     * @return devuelve la eliminacion de un autor
     */
    @Override
    public boolean eliminar(Integer idAutor) {
        String sql = "{call sp_eliminarautor(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, idAutor);
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al eliminar autor: " + e.getMessage(), e);
        }
    }
}
