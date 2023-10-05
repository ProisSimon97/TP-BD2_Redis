package ar.unrn.tp.web;

import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.Venta;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/venta")
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<Float> calcularMonto(@RequestParam(name = "productos") List<Long> productos, Long idTarjeta) {
        Float monto = ventaService.calcularMonto(productos, idTarjeta);

        return new ResponseEntity<>(monto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> crearVenta(@NotNull Long idCliente, @RequestParam(name = "productos") List<Long> productos, @NotNull Long idTarjeta) {
        ventaService.realizarVenta(idCliente, productos, idTarjeta);

        return new ResponseEntity<>("Venta registrada con exito!", HttpStatus.OK);
    }

    @GetMapping({"/idCliente"})
    public ResponseEntity<List<Venta>> misCompras(@NotNull @RequestParam Long idCliente) throws JsonProcessingException {
        List<Venta> result = ventaService.misCompras(idCliente);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
