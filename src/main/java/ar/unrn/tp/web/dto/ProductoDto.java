package ar.unrn.tp.web.dto;

import ar.unrn.tp.modelo.Producto;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductoDto {

    private Long id;
    private String codigo;
    private String descripcion;
    private String categoria;
    private double precio;
    private String marca;
    private Long version;

    public static ProductoDto fromDomain(Producto producto) {
        return ProductoDto.builder()
                .id(producto.id())
                .codigo(producto.codigo())
                .descripcion(producto.descripcion())
                .categoria(producto.categoria().tipo())
                .precio(producto.getPrecio())
                .marca(producto.getMarca().getNombre())
                .version(producto.getVersion())
                .build();
    }
}
