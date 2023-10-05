package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.modelo.promocion.PromocionCompra;
import ar.unrn.tp.modelo.promocion.PromocionProducto;
import ar.unrn.tp.modelo.Tarjeta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.function.Consumer;


public class DescuentoServiceTest {

    private static final String UNIT_NAME = "jpa-derby-embedded";
    private static DescuentoService descuentoService;
    private EntityManagerFactory emf;
    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory(UNIT_NAME);

        descuentoService = new DescuentoServiceJPA(emf);
    }

    @Test
    public void Should_BeTrue_When_AProductDiscountIsCreated() {

        LocalDate fechaInicio = LocalDate.now().minusDays(2);
        LocalDate fechaFin = LocalDate.now().plusDays(2);
        Marca marca = new Marca("Acme");

        descuentoService.crearDescuento("Acme", fechaInicio, fechaFin, 0.5);

        inTransactionExecute((em) -> {

            TypedQuery<PromocionProducto> query = em.createQuery("SELECT p FROM PromocionProducto p WHERE p.descuento = :descuento", PromocionProducto.class);
            query.setParameter("descuento", 0.5);
            PromocionProducto promocionProducto =  query.getSingleResult();

            Assertions.assertTrue(promocionProducto.esFecha(fechaInicio, fechaFin));
            Assertions.assertTrue(promocionProducto.esMarca(marca.getTipo()));
            Assertions.assertTrue(promocionProducto.esDescuento(0.5));
        });
    }

    @Test
    public void Should_BeTrue_When_ASaleDiscountIsCreated() {

        LocalDate fechaInicio = LocalDate.now().minusDays(2);
        LocalDate fechaFin = LocalDate.now().plusDays(2);
        Tarjeta tarjeta = new Tarjeta(150000, "Ivisa", "759958992654");

        inTransactionExecute((em) -> {
            em.persist(tarjeta);
        });

        descuentoService.crearDescuentoSobreTotal("Ivisa", fechaInicio, fechaFin, 0.8);

        inTransactionExecute((em) -> {

            TypedQuery<PromocionCompra> query = em.createQuery("SELECT p FROM PromocionCompra p WHERE p.descuento = :descuento", PromocionCompra.class);
            query.setParameter("descuento", 0.8);
            PromocionCompra promocionCompra =  query.getSingleResult();

            Assertions.assertTrue(promocionCompra.esFecha(fechaInicio, fechaFin));
            Assertions.assertTrue(promocionCompra.esTarjeta(tarjeta));
            Assertions.assertTrue(promocionCompra.esDescuento(0.8));
        });
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
    }
}
