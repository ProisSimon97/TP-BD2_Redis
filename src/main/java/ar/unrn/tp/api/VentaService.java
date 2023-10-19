package ar.unrn.tp.api;

import ar.unrn.tp.modelo.Venta;
import ar.unrn.tp.modelo.VentaSimple;

import java.util.List;

public interface VentaService {
    // Crea una venta. El monto se calcula aplicando los descuentos a la fecha
    // validaciones:
    // - debe ser un cliente existente
    // - la lista de productos no debe estar vacía
    // - La tarjeta debe pertenecer al cliente
    void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta);

    // Devuelve el monto total aplicando los descuentos al día de la fecha
    // validar que no llegue una lista vacía y la tarjeta exista
    float calcularMonto(List<Long> productos, Long idTarjeta);

    //Devuelve todas las ventas realizadas
    List<Venta> ventas();

    List<VentaSimple> misCompras(Long idCliente);
}