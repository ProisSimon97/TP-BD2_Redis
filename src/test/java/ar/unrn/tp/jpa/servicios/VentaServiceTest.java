package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class VentaServiceTest {

    /*private static final String UNIT_NAME = "jpa-derby-embedded";
    private static VentaService ventaService;
    private static ClienteService clienteService;
    private static DescuentoService descuentoService;
    private static ProductoService productoService;

    private EntityManagerFactory emf;

    @BeforeEach
    public void setUp() {

        emf = Persistence.createEntityManagerFactory(UNIT_NAME);

        ventaService = new VentaServiceJPA(emf);
        clienteService = new ClienteServiceJPA(emf);
        productoService = new ProductoServiceJPA(emf);
        descuentoService = new DescuentoServiceJPA(emf);

        inTransactionExecute((em) -> {
            Categoria categoria = new Categoria("Ropa deportiva");
            em.persist(categoria);
        });

        clienteService.crearCliente("Simon", "Preuss", "39870345", "simon@gmail.com");

        productoService.crearProducto("777", "Remera Crossfit", 10000, "Adudas", 1L);
        productoService.crearProducto("555", "Pantalon corto", 10000, "Adudas", 1L);

        clienteService.agregarTarjeta(1L, "34595465465454", "Visa", 150000);

        descuentoService.crearDescuentoSobreTotal("Visa", LocalDate.now().minusDays(2), LocalDate.now().plusDays(2), 0.5);
        descuentoService.crearDescuento("Adudas", LocalDate.now().minusDays(2), LocalDate.now().plusDays(2), 0.5);
    }

    @Test
    public void Should_BeTrue_When_ASaleIsMade() {
        List<Producto> productos = productoService.listarProductos();

        List<Long> idsProductos = new ArrayList<>();

        productos.forEach(p -> {
            idsProductos.add(p.id());
        });

        ventaService.realizarVenta(1L, idsProductos, 1L);

        inTransactionExecute((em) -> {
            Venta venta = em.find(Venta.class, 1L);
            Assertions.assertNotNull(venta);
        });
    }

    @Test
    public void Should_ThrowRuntimeException_When_ASaleIsMadeWithoutAValidClient() {
        List<Producto> productos = productoService.listarProductos();

        List<Long> idsProductos = new ArrayList<>();

        productos.forEach(p -> {
            idsProductos.add(p.id());
        });

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            ventaService.realizarVenta(65L, idsProductos, 5L);
        });

        Assertions.assertTrue(exception.getMessage().contains("No existe el cliente solicitado"));
    }

    @Test
    public void Should_ThrowRuntimeException_When_ASaleIsMadeWithoutAValidCard() {
        List<Producto> productos = productoService.listarProductos();

        List<Long> idsProductos = new ArrayList<>();

        productos.forEach(p -> {
            idsProductos.add(p.id());
        });

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            ventaService.realizarVenta(1L, idsProductos, 15L);
        });

        Assertions.assertTrue(exception.getMessage().contains("No existe la tarjeta solicitada"));
    }

    @Test
    public void Should_ThrowRuntimeException_When_ASaleIsMadeWithoutAProductList() {
        List<Long> idsProductos = new ArrayList<>();

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            ventaService.realizarVenta(1L, idsProductos, 1L);
        });

        Assertions.assertTrue(exception.getMessage().contains("No hay productos para esta lista"));
    }

    @Test
    public void Should_BeTrue_When_TryCalculateAmount() {
        List<Producto> productos = productoService.listarProductos();

        List<Long> idsProductos = new ArrayList<>();

        productos.forEach(p -> {
            idsProductos.add(p.id());
        });

        Assertions.assertEquals(ventaService.calcularMonto(idsProductos, 1L), 5000);
    }

    @Test
    public void Should_ThrowRuntimeException_When_TryCalculateAmountWithoutAProductList() {
        List<Long> idsProductos = new ArrayList<>();

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            ventaService.calcularMonto(idsProductos, 1L);
        });

        Assertions.assertTrue(exception.getMessage().contains("No hay productos para esta lista"));
    }

    @Test
    public void Should_ThrowRuntimeException_When_TryCalculateAmountWithoutAValidCard() {
        List<Producto> productos = productoService.listarProductos();

        List<Long> idsProductos = new ArrayList<>();

        productos.forEach(p -> {
            idsProductos.add(p.id());
        });

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            ventaService.calcularMonto(idsProductos, 15L);
        });

        Assertions.assertTrue(exception.getMessage().contains("La tarjeta solicitada no existe"));
    }

    @Test
    public void Should_BeTrue_When_SalesListIsNotEmpty() {
        List<Producto> productos = productoService.listarProductos();

        List<Long> idsProductos = new ArrayList<>();

        productos.forEach(p -> {
            idsProductos.add(p.id());
        });

        ventaService.realizarVenta(1L, idsProductos, 1L);

        List<Venta> ventas = ventaService.ventas();

        Assertions.assertTrue(!ventas.isEmpty());
    }

    public void inTransactionExecute(Consumer<EntityManager> bloqueDeCodigo) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            bloqueDeCodigo.accept(em);

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        emf.close();
    }*/
}
