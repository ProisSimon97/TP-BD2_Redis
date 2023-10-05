package ar.unrn.tp.web;

import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.web.dto.ProductoDto;
import ar.unrn.tp.modelo.Producto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/producto")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDto>> findAll() {
        List<Producto> productos = productoService.listarProductos();

        List<ProductoDto> response = productos.stream()
                .map(ProductoDto::fromDomain)
                .toList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> update(@NotNull Long idProducto, String codigo, String descripcion, double precio, Long idCategoria, Long version, String marca) {
        productoService.modificarProducto(idProducto, codigo, descripcion, precio, idCategoria, version, marca);
        return new ResponseEntity<>("Producto modificado con exito!", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> find(Long id) {
        Producto producto = productoService.obtener(id);
        ProductoDto response = ProductoDto.fromDomain(producto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
