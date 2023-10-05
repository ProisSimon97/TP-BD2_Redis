package ar.unrn.tp.modelo;

import ar.unrn.tp.modelo.promocion.PromocionProducto;
import ar.unrn.tp.modelo.promocion.PromocionCompra;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private Cliente cliente;
    private List<Producto> productos;
    public Carrito(Cliente cliente) {
        this.cliente = cliente;
        this.productos = new ArrayList<>();
    }

    public Carrito() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        this.productos.add(Producto.crearProducto(producto));
    }

    public void agregarProductos(List<Producto> productos) {
        if (productos.isEmpty()) {
            throw new RuntimeException("La lista de productos no puede ser vacia");
        }
        this.productos.addAll(productos);
    }

    public double calcularMontoTotalConDescuento(List<PromocionProducto> promocionProducto, PromocionCompra promocionCompra, Tarjeta tarjeta) {
        double total = 0;

        for (Producto producto : productos) {

            total += producto.getPrecio();

            double descuentos = promocionProducto.stream()
                    .mapToDouble(promo -> producto.getPrecio() * promo.calcularDescuento(producto))
                    .sum();

            total -= descuentos;

        }
        return total - (total * promocionCompra.calcularDescuento(tarjeta));
    }

    public Venta realizarCompra(List <PromocionProducto> promocionProducto, PromocionCompra promocionCompra, Tarjeta tarjeta, String nextNumber) {

        if (this.productos.isEmpty()) {
            throw new RuntimeException("La lista de productos no puede ser vacia");
        }

        double totalCompra = calcularMontoTotalConDescuento(promocionProducto, promocionCompra, tarjeta);

        List<ProductoVendido> productoVendidos = productos.stream()
                .map(ProductoVendido::mapProductoToProductoVendido)
                .toList();

        try {
            tarjeta.realizarPago(totalCompra);

            Venta venta = new Venta(LocalDate.now(), this.cliente, productoVendidos, totalCompra);
            venta.numeroUnico(nextNumber);

            return venta;
        } catch(RuntimeException e) {
            throw e;
        }
    }
}