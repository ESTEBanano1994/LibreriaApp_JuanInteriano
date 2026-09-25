
package org.libreria.DAO;

/**
 *
 * @author informatica
 */
import java.util.List;
import org.libreria.model.LineaVenta;
import org.libreria.model.Venta;

public interface VentaDAO extends Crud<Venta, Integer>{
    //crearVenta inserta el encabezado de la venta, sus líneas y descuenta el stock.
    //Devuelve el no_venta generado (o -1 si falla).
    int crearVenta(Venta venta, List<LineaVenta> lineas);
}