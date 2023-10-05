package ar.unrn.tp.main;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.jpa.servicios.ClienteServiceJPA;
import ar.unrn.tp.jpa.servicios.DescuentoServiceJPA;
import ar.unrn.tp.jpa.servicios.ProductoServiceJPA;
import ar.unrn.tp.jpa.servicios.VentaServiceJPA;
import ar.unrn.tp.modelo.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Main {
   /* private static final String UNIT_NAME = "jpa-pgsql";
    private static EntityManagerFactory emf;
    private static ClienteService clienteService;
    private static DescuentoService descuentoService;
    private static ProductoService productoService;
    private static VentaService ventaService;

    public static void main(String[] args) {

        emf = Persistence.createEntityManagerFactory(UNIT_NAME);

        clienteService = new ClienteServiceJPA(emf);
        descuentoService = new DescuentoServiceJPA(emf);
        productoService = new ProductoServiceJPA(emf);
        ventaService = new VentaServiceJPA(emf);

        inTransactionExecute((em) -> {
            Categoria categoria = new Categoria("Ropa deportiva");
            em.persist(categoria);
        });

        clienteService.crearCliente("Simon", "Preuss", "39870345", "simon@gmail.com");
        clienteService.agregarTarjeta(1L, "7777", "Visa", 15000);

        productoService.crearProducto("777", "Remera", 15000, "Adudas", 1L);

        descuentoService.crearDescuento("Adudas", LocalDate.now().minusDays(2), LocalDate.now().plusDays(2), 0.5);
        descuentoService.crearDescuentoSobreTotal("Visa", LocalDate.now().minusDays(2), LocalDate.now().plusDays(2), 0.8);

        List<Long> productos = new ArrayList<>();
        productos.add(1L);

        ventaService.realizarVenta(1L, productos, 1L);
        ventaService.realizarVenta(1L, productos, 1L);
    }

    public static void inTransactionExecute(Consumer<EntityManager> bloqueDeCodigo) {
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
    }*/
}