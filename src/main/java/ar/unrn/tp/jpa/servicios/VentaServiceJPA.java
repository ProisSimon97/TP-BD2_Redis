package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.api.redis.CacheService;
import ar.unrn.tp.modelo.*;
import ar.unrn.tp.modelo.promocion.PromocionCompra;
import ar.unrn.tp.modelo.promocion.PromocionProducto;
import ar.unrn.tp.util.DateTypeAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class VentaServiceJPA implements VentaService {
    private EntityManagerFactory emf;

    private CacheService cacheService;
    private Gson gson;

    public VentaServiceJPA(EntityManagerFactory emf, CacheService cacheService) {
        this.emf = emf;
        this.cacheService = cacheService;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new DateTypeAdapter())
                .create();
    }

    @Override
    public void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta) {
       inTransactionExecute((em) -> {
           Cliente cliente = em.find(Cliente.class, idCliente);
           if (cliente == null)
               throw new RuntimeException("No existe el cliente solicitado");

           Tarjeta tarjeta = em.find(Tarjeta.class, idTarjeta);
           if (tarjeta == null)
               throw new RuntimeException("No existe la tarjeta solicitada");

           if (!cliente.miTarjeta(tarjeta))
               throw new RuntimeException("La tarjeta no coincide con el regristro de tarjetas del cliente");

           if(productos.isEmpty()) {
               throw new RuntimeException("No hay productos para esta lista");
           }

           TypedQuery<Producto> queryProductos = em.createQuery("select p from Producto p where p.id in :productos", Producto.class);
           queryProductos.setParameter("productos", productos);
           List<Producto> productosBd = queryProductos.getResultList();

           TypedQuery<PromocionProducto> queryPromocionesProducto = em.createQuery("select p from PromocionProducto p where :now between p.fechaInicio and p.fechaFin", PromocionProducto.class);
           queryPromocionesProducto.setParameter("now", LocalDate.now());
           List<PromocionProducto> promocionesProducto = queryPromocionesProducto.getResultList();

           TypedQuery<PromocionCompra> queryPromocionesCompra = em.createQuery("select p from PromocionCompra p where :now between p.fechaInicio and p.fechaFin", PromocionCompra.class);
           queryPromocionesCompra.setParameter("now", LocalDate.now());
           PromocionCompra promocionCompra = queryPromocionesCompra.getSingleResult();

           TypedQuery<NextNumber> queryNumber = em.createQuery("select n from NextNumber n where n.anio = :anio", NextNumber.class);
           queryNumber.setParameter("anio", LocalDate.now().getYear());
           queryNumber.setLockMode(LockModeType.PESSIMISTIC_WRITE);

           NextNumber nextNumber = null;

           try {
               nextNumber = queryNumber.getSingleResult();
               nextNumber.actual(nextNumber.recuperarSiguiente());

           } catch(Exception e) {
               nextNumber = new NextNumber(LocalDate.now().getYear(), 1);
           }

           Carrito carrito = new Carrito(cliente);
           carrito.agregarProductos(productosBd);

           Venta venta = carrito.realizarCompra(promocionesProducto, promocionCompra, tarjeta, nextNumber.numeroUnico());

           em.persist(nextNumber);
           em.persist(venta);

           String key = "venta:" + idCliente;

           cacheService.clear(key);
       });
    }

    @Override
    public float calcularMonto(List<Long> productos, Long idTarjeta) {
        EntityManager em = emf.createEntityManager();

        try {
            if(productos.isEmpty()) {
                throw new RuntimeException("No hay productos para esta lista");
            }

            Tarjeta tarjeta = em.find(Tarjeta.class, idTarjeta);

            if(tarjeta == null) {
                throw new RuntimeException("La tarjeta solicitada no existe");
            }

            TypedQuery<Producto> queryProductos = em.createQuery("select p from Producto p where p.id in :productos", Producto.class);
            queryProductos.setParameter("productos", productos);
            List<Producto> productosBd = queryProductos.getResultList();

            TypedQuery<PromocionProducto> queryPromocionesProducto = em.createQuery("select p from PromocionProducto p where :now between p.fechaInicio and p.fechaFin",
                    PromocionProducto.class);
            queryPromocionesProducto.setParameter("now", LocalDate.now());
            List<PromocionProducto> promocionesProducto = queryPromocionesProducto.getResultList();

            TypedQuery<PromocionCompra> queryPromocionesCompra = em.createQuery("select p from PromocionCompra p where :now between p.fechaInicio and p.fechaFin",
                    PromocionCompra.class);
            queryPromocionesCompra.setParameter("now", LocalDate.now());
            PromocionCompra promocionCompra = queryPromocionesCompra.getSingleResult();

            Carrito carrito = new Carrito();

            carrito.agregarProductos(productosBd);

            return (float) carrito.calcularMontoTotalConDescuento(promocionesProducto, promocionCompra, tarjeta);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public List ventas() {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Venta> ventas = em.createQuery("select v from Venta v", Venta.class);
            return ventas.getResultList();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public List<Venta> misCompras(Long idCliente) {
        String key = "venta:" + idCliente;
        List<Venta> ventas = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        String json = cacheService.retrieve(key);

        if(json != null) {
            try {
                ventas = mapper.readValue(json, new TypeReference<List<Venta>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        if(ventas == null) {
            EntityManager em = emf.createEntityManager();

            try {
                TypedQuery<Venta> query = em.createQuery(
                        "SELECT v FROM Venta v WHERE v.cliente.id = :idCliente ORDER BY v.fecha DESC",
                        Venta.class
                );

                query.setParameter("idCliente", idCliente);
                query.setMaxResults(3);

                ventas = query.getResultList();

                cacheService.store(key, mapper.writeValueAsString(ventas));

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (em != null && em.isOpen())
                    em.close();
            }
        }

        return ventas;
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
