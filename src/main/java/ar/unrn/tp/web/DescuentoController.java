package ar.unrn.tp.web;

import ar.unrn.tp.web.dto.PromocionDto;
import ar.unrn.tp.jpa.servicios.DescuentoServiceJPA;
import ar.unrn.tp.modelo.promocion.Promocion;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/descuento")
public class DescuentoController {

    private final DescuentoServiceJPA descuentoService;

    @GetMapping
    public ResponseEntity<List<PromocionDto>> findAll(@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha) {
        List<Promocion> promociones = descuentoService.obtenerPromocionesVigentes(fecha);

        List<PromocionDto> response = promociones.stream()
                .map(PromocionDto::fromDomain)
                .toList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
