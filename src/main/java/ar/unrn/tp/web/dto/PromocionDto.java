package ar.unrn.tp.web.dto;

import ar.unrn.tp.modelo.promocion.Promocion;
import ar.unrn.tp.modelo.promocion.PromocionCompra;
import ar.unrn.tp.modelo.promocion.PromocionProducto;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PromocionDto {

    private Long id;
    protected LocalDate fechaInicio;
    protected LocalDate fechaFin;
    protected double descuento;
    private String marca;
    private String tarjeta;

    public static PromocionDto fromDomain(Promocion promocion) {
        PromocionDto dto = new PromocionDto();

        if (promocion instanceof PromocionProducto) {
            PromocionProducto producto = (PromocionProducto) promocion;

            dto = PromocionDto.builder()
                    .id(producto.id())
                    .fechaInicio(producto.fechaInicio())
                    .fechaFin(producto.fechaFin())
                    .descuento(producto.descuento())
                    .marca(producto.marca())
                    .build();

        } else if (promocion instanceof PromocionCompra) {
            PromocionCompra compra = (PromocionCompra) promocion;

            dto = PromocionDto.builder()
                    .id(compra.id())
                    .fechaInicio(compra.fechaInicio())
                    .fechaFin(compra.fechaFin())
                    .descuento(compra.descuento())
                    .tarjeta(compra.tarjeta())
                    .build();
        }

        return dto;
    }
}
