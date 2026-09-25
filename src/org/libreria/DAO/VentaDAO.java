
package org.libreria.DAO;

/**
 * Representa la separacion de codigo con la base de datos SQL de venta
 *
 * @author Esteban Interiano
 * @version 1.0.0
 * @see org.libreria.DAO.VentaDAO
 */
import java.util.List;
import org.libreria.model.LineaVenta;
import org.libreria.model.Venta;

/**
 *
 * @author Esteban Interiano
 */
public interface VentaDAO extends Crud<Venta, Integer>{
    //crearVenta inserta el encabezado de la venta, sus líneas y descuenta el stock.
    //Devuelve el no_venta generado (o -1 si falla).

    /**
     * obtiene los datos necesarios para generar una venta
     * @param venta accion de vender algo
     * @param lineas espacio entre texto
     * @return deveuelve la creacion de una venta
     */
    int crearVenta(Venta venta, List<LineaVenta> lineas);
}