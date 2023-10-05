package ar.unrn.tp.modelo;

import jakarta.persistence.*;

@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String tipo;

    protected Categoria() { }

    public Categoria(String tipo) {
        this.tipo = tipo;
    }

    public String tipo() {
        return this.tipo;
    }

    private Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    private String getTipo() {
        return tipo;
    }

    private void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Categoria categoria = (Categoria) o;

        return tipo != null ? tipo.equals(categoria.tipo) : categoria.tipo == null;
    }

    @Override
    public int hashCode() {
        return tipo != null ? tipo.hashCode() : 0;
    }
}
