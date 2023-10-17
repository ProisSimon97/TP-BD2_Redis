package ar.unrn.tp.web.dto;

import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.ProductoVendido;

import ar.unrn.tp.modelo.Venta;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VentaDto {
    private Long id;
    private LocalDate fecha;
    private Cliente cliente;
    private List<ProductoVendido> productosComprados;
    private double montoTotal;
    private String numeroUnico;

    public static VentaDto fromDomain(Venta venta) {
        return VentaDto.builder()
                .id(venta.getId())
                .fecha(venta.getFecha())
                .cliente(venta.getCliente())
                .productosComprados(venta.getProductosComprados())
                .montoTotal(venta.getMontoTotal())
                .numeroUnico(venta.getNumeroUnico())
                .build();
    }
}
