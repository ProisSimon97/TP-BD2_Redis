package ar.unrn.tp.modelo.promocion;

import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.modelo.Tarjeta;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_promocion")
public abstract class Promocion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "fecha_inicio")
    protected LocalDate fechaInicio;
    @Column(name = "fecha_fin")
    protected LocalDate fechaFin;
    protected double descuento;

    protected Promocion() { }

    public Promocion(LocalDate fechaInicio, LocalDate fechaFin, double descuento) {
        if (fechaInicio.isEqual(fechaFin)) {
            throw new RuntimeException("Las fechas de inicio y fin no pueden ser iguales");
        }
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descuento = descuento;
    }

    public boolean esDescuento(double descuento) {
        return this.descuento == descuento;
    }

    public boolean esFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        return (this.fechaInicio.equals(fechaInicio) && this.fechaFin.equals(fechaFin));
    }

    public Long id() {
        return this.id;
    }

    public LocalDate fechaInicio() {
        return this.fechaInicio;
    }

    public LocalDate fechaFin() {
        return this.fechaFin;
    }

    public double descuento() {
        return this.descuento;
    }

    public abstract double calcularDescuento(Tarjeta tarjeta);
    public abstract double calcularDescuento(Producto producto);
}