package ar.unrn.tp.modelo;

import jakarta.persistence.*;

@Embeddable
public class Marca {
    private String nombre;

    protected Marca() { }

    public Marca(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return nombre;
    }
}