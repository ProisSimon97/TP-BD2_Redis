package ar.unrn.tp.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "next_number")
public class NextNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private int anio;
    private int actual;

    public NextNumber(int anio, int actual) {
        this.anio = anio;
        this.actual = actual;
    }

    protected NextNumber() {}

    public int recuperarSiguiente() {
        this.actual += 1;
        return this.actual;
    }

    public void actual(int actual) {
        this.actual = actual;
    }

    public String numeroUnico() {
        return this.actual + "-" + this.anio;
    }

    public int anio() {
        return this.anio;
    }

    public int actual() {
        return this.actual;
    }

    private long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    private int getAnio() {
        return anio;
    }

    private void setAnio(int anio) {
        this.anio = anio;
    }

    private int getActual() {
        return actual;
    }

    private void setActual(int actual) {
        this.actual = actual;
    }
}