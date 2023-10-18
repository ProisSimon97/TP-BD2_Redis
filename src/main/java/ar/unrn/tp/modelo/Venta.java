package ar.unrn.tp.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime fecha;
    @ManyToOne
    private Cliente cliente;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_venta")
    private List<ProductoVendido> productosComprados;
    @Column(name = "monto_total")
    private double montoTotal;
    @Column(name = "numero_unico", unique = true)
    private String numeroUnico;

    protected Venta() { }

    public Venta(LocalDateTime fechaHora, Cliente cliente, List<ProductoVendido> productosComprados, double montoTotal) {
        this.fecha = fechaHora;
        this.cliente = cliente;
        this.productosComprados = productosComprados;
        this.montoTotal = montoTotal;
    }

    public Venta(Long id, LocalDateTime fechaHora, Cliente cliente, List<ProductoVendido> productosComprados, double montoTotal, String numeroUnico) {
        this.id = id;
        this.fecha = fechaHora;
        this.cliente = cliente;
        this.productosComprados = productosComprados;
        this.montoTotal = montoTotal;
        this.numeroUnico = numeroUnico;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ProductoVendido> getProductosComprados() {
        return productosComprados;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public String getNumeroUnico() {
        return numeroUnico;
    }

    public void numeroUnico(String numeroUnico) {
        this.numeroUnico = numeroUnico;
    }

    public String numeroUnico() {
        return this.numeroUnico;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setProductosComprados(List<ProductoVendido> productosComprados) {
        this.productosComprados = productosComprados;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public void setNumeroUnico(String numeroUnico) {
        this.numeroUnico = numeroUnico;
    }
}