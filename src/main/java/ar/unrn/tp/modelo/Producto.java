package ar.unrn.tp.modelo;

import jakarta.persistence.*;

@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Long version;
    @Column(unique = true)
    private String codigo;
    private String descripcion;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Categoria categoria;
    private double precio;
    @Embedded
    private Marca marca;

    protected Producto() { }

    public Producto(String codigo, String descripcion, Categoria categoria, double precio, Marca marca) throws RuntimeException {

        if (categoria == null || descripcion == null || codigo == null) {
            throw new RuntimeException("Los datos proporcionados no son válidos");
        }

        this.codigo = codigo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.marca = marca;
    }

    public Producto(Long id, String codigo, String descripcion, Categoria categoria, double precio, Marca marca) throws RuntimeException {

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

    public static Producto crearProducto(Producto producto) {
        return (producto.getId() != null)
                ? new Producto(producto.id, producto.codigo, producto.descripcion, producto.categoria, producto.precio, producto.marca)
                : new Producto(producto.codigo, producto.descripcion, producto.categoria, producto.precio, producto.marca);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public boolean esMarca(Marca marca) {
        return this.marca.equals(marca);
    }

    public boolean esMarca(String marca) { return this.marca.getTipo().equals(marca); }

    public boolean esCodigo(String codigo) {
        return this.codigo.equals(codigo);
    }

    public boolean esDescripcion(String descripcion) {
        return this.descripcion.equals(descripcion);
    }

    public boolean esCategoria(Categoria categoria) {
        return this.categoria.equals(categoria);
    }

    public Categoria categoria() {
        return this.categoria;
    }

    public String descripcion() {
        return  this.descripcion;
    }

    public String codigo() {
        return this.codigo;
    }

    public double getPrecio() {
        return this.precio;
    }

    public Long id() { return this.id; }

    public void id(Long id) {
        this.id = id;
    }

    public Marca getMarca() {
        return this.marca;
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