package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.modelo.Producto;
import jakarta.persistence.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
public class ProductoServiceJPA implements ProductoService {

    private EntityManagerFactory emf;

    public ProductoServiceJPA(EntityManagerFactory emf) {
        this.emf = emf;
    }
    @Override
    public void crearProducto(String codigo, String descripcion, double precio, String marca, Long idCategoria) {
        inTransactionExecute((em) -> {
            Categoria categoria = em.find(Categoria.class, idCategoria);

            if(categoria == null) {
                throw new RuntimeException("La categoria solicitada no existe");
            }

            Marca marcaProducto = new Marca(marca);
            Producto producto = new Producto(codigo, descripcion, categoria, precio, marcaProducto);
            em.persist(producto);
        });
    }

    @Override
    public void modificarProducto(Long idProducto, String codigo, String descripcion, double precio, Long idCategoria, Long version, String marca) {
        inTransactionExecute((em) -> {
            Categoria categoria = em.find(Categoria.class, idCategoria);
            Producto producto = em.find(Producto.class, idProducto);

            if(producto == null) {
                throw new RuntimeException("El producto solicitado no existe");
            }

            if(categoria == null) {
                throw new RuntimeException("La categoria solicitada no existe");
            }

            Producto productoModificado = new Producto(codigo, descripcion, categoria, precio, new Marca(marca));

            productoModificado.id(idProducto);
            productoModificado.setVersion(version);

            em.merge(productoModificado);
        });
    }

    @Override
    public List listarProductos() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            TypedQuery<Producto> q = em.createQuery("select p from Producto p", Producto.class);
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

    @Override
    public Producto obtener(Long id) {
        final Producto[] producto = {null};

        inTransactionExecute((em) -> {
            producto[0] = em.find(Producto.class, id);

            if(producto[0] == null) {
                throw new RuntimeException("El producto solicitado no existe");
            }
        });
        return producto[0];
    }

    public void inTransactionExecute(Consumer<EntityManager> bloqueDeCodigo) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            bloqueDeCodigo.accept(em);

            tx.commit();
        } catch (OptimisticLockException e) {
            throw new RuntimeException("El producto ya fue modificado: Version incorrecta", e);

        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }
}
