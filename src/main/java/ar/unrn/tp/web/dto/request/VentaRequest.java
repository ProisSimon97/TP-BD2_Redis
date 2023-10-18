package ar.unrn.tp.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class VentaRequest {
    private Long idCliente;
    private List<Long> productos;
    private Long idTarjeta;
}
