package ar.unrn.tp.modelo;

import lombok.Builder;

import jakarta.persistence.*;

@Builder
@Table(name = "producto_vendido")
@Entity
public class ProductoVendido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String codigo;
    private String descripcion;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Categoria categoria;
    private double precio;
    @Embedded
    private Marca marca;

    protected ProductoVendido() { }

    public ProductoVendido(String codigo, String descripcion, Categoria categoria, double precio, Marca marca) throws RuntimeException {

        if (categoria == null || descripcion == null || codigo == null) {
            throw new RuntimeException("Los datos proporcionados no son válidos");
        }

        this.codigo = codigo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.marca = marca;
    }

    public ProductoVendido(Long id, String codigo, String descripcion, Categoria categoria, double precio, Marca marca) throws RuntimeException {

        if (categoria == null || descripcion == null || codigo == null) {
            throw new RuntimeException("Los datos proporcionados no son válidos");
        }

        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.marca = marca;
    }

    public static ProductoVendido mapProductoToProductoVendido(Producto producto) {
        return ProductoVendido.builder()
                .codigo(producto.codigo())
                .descripcion(producto.descripcion())
                .categoria(producto.categoria())
                .precio(producto.getPrecio())
                .marca(producto.getMarca())
                .build();
    }

    public double getPrecio() {
        return precio;
    }

    public Marca getMarca() {
        return marca;
    }

    private Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    private String getCodigo() {
        return codigo;
    }

    private void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    private String getDescripcion() {
        return descripcion;
    }

    private void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    private Categoria getCategoria() {
        return categoria;
    }

    private void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    private void setMarca(Marca marca) {
        this.marca = marca;
    }

    public void codigo(String codigo) {
        this.codigo = codigo;
    }

    public void descripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void precio(double precio) {
        this.precio = precio;
    }

    public void categoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
