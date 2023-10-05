package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.modelo.promocion.Promocion;
import ar.unrn.tp.modelo.promocion.PromocionCompra;
import ar.unrn.tp.modelo.promocion.PromocionProducto;
import ar.unrn.tp.modelo.Tarjeta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

@Service
public class DescuentoServiceJPA implements DescuentoService {

    private EntityManagerFactory emf;

    public DescuentoServiceJPA(EntityManagerFactory emf) {
        this.emf = emf;
    }
    @Override
    public void crearDescuentoSobreTotal(String marcaTarjeta, LocalDate fechaDesde, LocalDate fechaHasta, double porcentaje) {
        inTransactionExecute((em) -> {
            TypedQuery<Tarjeta> query = em.createQuery("SELECT t FROM Tarjeta t WHERE t.nombre = :marcaTarjeta", Tarjeta.class);
            query.setParameter("marcaTarjeta", marcaTarjeta);
            Tarjeta tarjeta = query.getSingleResult();

            if (tarjeta == null) {
                throw new RuntimeException("No se encontró la tarjeta con la marca: " + marcaTarjeta);
            }

            PromocionCompra promocionCompra = new PromocionCompra(fechaDesde, fechaHasta, porcentaje, tarjeta);
            em.persist(promocionCompra);
        });
    }

    @Override
    public void crearDescuento(String marcaProducto, LocalDate fechaDesde, LocalDate fechaHasta, double porcentaje) {
        inTransactionExecute((em) -> {
            Marca marca = new Marca(marcaProducto);
            PromocionProducto promocionProducto = new PromocionProducto(fechaDesde, fechaHasta, porcentaje, marca);

            em.persist(promocionProducto);
        });
    }

    @Override
    public List<Promocion> obtenerPromocionesVigentes(LocalDate fecha) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            TypedQuery<Promocion> q = em.createQuery("SELECT p FROM Promocion p WHERE :fecha BETWEEN p.fechaInicio AND p.fechaFin", Promocion.class);
            q.setParameter("fecha", fecha);
            return q.getResultList();

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
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
}
