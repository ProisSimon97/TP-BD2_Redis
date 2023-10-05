package ar.unrn.tp.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate fecha;
    @ManyToOne
    private Cliente cliente;

    public Long getId() {
        return id;
    }

    public LocalDate getFecha() {
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

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_venta")
    private List<ProductoVendido> productosComprados;
    @Column(name = "monto_total")
    private double montoTotal;
    @Column(name = "numero_unico", unique = true)
    private String numeroUnico;

    protected Venta() { }

    public Venta(LocalDate fechaHora, Cliente cliente, List<ProductoVendido> productosComprados, double montoTotal) {
        this.fecha = fechaHora;
        this.cliente = cliente;
        this.productosComprados = productosComprados;
        this.montoTotal = montoTotal;
    }

    public void numeroUnico(String numeroUnico) {
        this.numeroUnico = numeroUnico;
    }

    public String numeroUnico() {
        return this.numeroUnico;
    }
}