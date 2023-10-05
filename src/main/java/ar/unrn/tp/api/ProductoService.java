package ar.unrn.tp.api;

import ar.unrn.tp.modelo.Producto;

import java.util.List;

public interface ProductoService {
    //validar que sea una categoría existente y que codigo no se repita
    void crearProducto(String codigo, String descripcion, double precio, String marca, Long idCategoria);

    //validar que sea un producto existente
    void modificarProducto(Long idProducto, String codigo, String descripcion, double precio, Long idCategoria, Long version, String marca);

    //Devuelve todos los productos
    List listarProductos();

    Producto obtener(Long id);
}