package ar.unrn.tp.web;

import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.VentaSimple;
import ar.unrn.tp.web.dto.request.VentaRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ar.unrn.tp.web.message.ResponseMessages.VENTA_REGISTRADA;

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
    public ResponseEntity<String> crearVenta(@RequestBody VentaRequest request) {
        ventaService.realizarVenta(request.getIdCliente(), request.getProductos(), request.getIdTarjeta());
        return new ResponseEntity<>(VENTA_REGISTRADA, HttpStatus.OK);
    }

    @GetMapping({"/mis-compras"})
    public ResponseEntity<List<VentaSimple>> misCompras(@NotNull @RequestParam Long idCliente) {
        List<VentaSimple> result = ventaService.misCompras(idCliente);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
