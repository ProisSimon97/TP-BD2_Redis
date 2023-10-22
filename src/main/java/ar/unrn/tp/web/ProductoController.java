package ar.unrn.tp.web;

import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.web.dto.ProductoDto;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.web.dto.request.ProductoRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ar.unrn.tp.web.message.ResponseMessages.PRODUCTO_MODIFICADO;

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
    public ResponseEntity<String> update(@RequestBody ProductoRequest request) {
        productoService.modificarProducto(request.getId(), request.getCodigo(), request.getDescripcion(), request.getPrecio(),
                request.getCategoria(), request.getVersion(), request.getMarca());
        return new ResponseEntity<>(PRODUCTO_MODIFICADO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> find(@PathVariable Long id) {
        Producto producto = productoService.obtener(id);
        ProductoDto response = ProductoDto.fromDomain(producto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
