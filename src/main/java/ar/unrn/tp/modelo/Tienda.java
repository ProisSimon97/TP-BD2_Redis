package ar.unrn.tp.modelo;

import java.util.ArrayList;
import java.util.List;

public class Tienda {
    private List<Producto> productos;

    private List<Venta> ventas;

    protected Tienda() {
        productos = new ArrayList<>();
        ventas = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(Producto.crearProducto(producto));
    }

    public void agregarVenta(Venta venta) {
        ventas.add(venta);
    }

    public boolean estadoDeVentas() {
        if(this.ventas != null && !this.ventas.isEmpty())
            return true;

        return false;
    }
}