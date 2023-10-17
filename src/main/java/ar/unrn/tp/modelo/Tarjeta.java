package ar.unrn.tp.modelo;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Tarjeta {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "numero_tarjeta")
    private String numeroTarjeta;
    private String nombre;
    private boolean activa;
    @Column(name = "fondos_disponibles")
    private double fondosDisponibles;

    protected Tarjeta() { }
    public Tarjeta(double fondosDisponibles, String nombre, String numeroTarjeta) {
        this.activa = true;
        this.numeroTarjeta = numeroTarjeta;
        this.nombre = nombre;
        this.fondosDisponibles = fondosDisponibles;
    }

    public Tarjeta(String nombre) {
        this.activa = true;
        this.nombre = nombre;
    }

    public boolean isActiva() {
        return activa;
    }

    public boolean estaActiva() {
        return this.activa;
    }

    public Long id() {
        return this.id;
    }

    public String nombre() {
        return this.nombre;
    }

    public String numero() {
        return  this.numeroTarjeta;
    }

    public double fondos() {
        return this.fondosDisponibles;
    }

    public boolean aplica(Tarjeta tarjeta) {
        return this.nombre.equals(tarjeta.getNombre());
    }

    public void realizarPago(double monto) {
        if (!estaActiva()) {
            throw new RuntimeException("La tarjeta no está activa");
        }

        if (monto > fondos()) {
            throw new RuntimeException("Fondos insuficientes en la tarjeta");
        }

        this.fondosDisponibles -= monto;
    }

    public boolean esTarjeta(Tarjeta tarjeta) {
        return this.nombre.equals(tarjeta.getNombre());
    }

    public String getNombre() {
        return nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public double getFondosDisponibles() {
        return fondosDisponibles;
    }

    public void setFondosDisponibles(double fondosDisponibles) {
        this.fondosDisponibles = fondosDisponibles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarjeta tarjeta = (Tarjeta) o;
        return Objects.equals(numeroTarjeta, tarjeta.numeroTarjeta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroTarjeta);
    }
}