package ar.unrn.tp.web;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.web.dto.TarjetaDto;
import ar.unrn.tp.modelo.Tarjeta;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<TarjetaDto>> obtenerTarjetasDeCliente(@NotNull Long id) {
        List<Tarjeta> tarjetas = clienteService.listarTarjetas(id);

        List<TarjetaDto> response = tarjetas.stream()
                .map(TarjetaDto::fromDomain)
                .toList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
