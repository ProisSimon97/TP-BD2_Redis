package ar.unrn.tp.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductoRequest {
    private Long id;
    private String codigo;
    private String descripcion;
    private double precio;
    private Long categoria;
    private Long version;
    private String marca;
}
