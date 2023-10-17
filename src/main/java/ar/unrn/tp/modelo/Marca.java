package ar.unrn.tp.modelo;

import jakarta.persistence.*;

@Embeddable
public class Marca {
    private String nombre;

    protected Marca() { }

    public Marca(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}