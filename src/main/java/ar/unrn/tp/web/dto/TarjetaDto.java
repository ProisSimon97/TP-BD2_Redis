package ar.unrn.tp.web.dto;

import ar.unrn.tp.modelo.Tarjeta;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TarjetaDto {

    private Long id;
    private String numeroTarjeta;
    private String nombre;
    private boolean activa;
    private double fondosDisponibles;

    public static TarjetaDto fromDomain(Tarjeta tarjeta) {
        return TarjetaDto.builder()
                .id(tarjeta.id())
                .numeroTarjeta(tarjeta.numero())
                .nombre(tarjeta.nombre())
                .activa(tarjeta.estaActiva())
                .fondosDisponibles(tarjeta.fondos())
                .build();
    }
}
