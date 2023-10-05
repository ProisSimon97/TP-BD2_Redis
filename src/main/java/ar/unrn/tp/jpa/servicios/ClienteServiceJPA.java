package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Tarjeta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
public class ClienteServiceJPA implements ClienteService {
    private EntityManagerFactory emf;

    public ClienteServiceJPA(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void crearCliente(String nombre, String apellido, String dni, String email) {
        inTransactionExecute((em) -> {
            TypedQuery<Cliente> q = em.createQuery("SELECT c FROM Cliente c WHERE c.dni = :dni", Cliente.class);
            q.setParameter("dni", dni);
            List<Cliente> clientes = q.getResultList();

            if (!clientes.isEmpty())
                throw new RuntimeException("Ya existe un usuario con ese dni");

            Cliente cliente = new Cliente(nombre, apellido, dni, email);
            em.persist(cliente);
        });
    }

    @Override
    public void modificarCliente(Long idCliente, String nombre, String apellido, String dni, String email) {
        inTransactionExecute((em) -> {
            Cliente cliente = em.find(Cliente.class, idCliente);

            if(cliente == null) {
                throw new RuntimeException("No existe el cliente solicitado");
            }

            cliente.nombre(nombre);
            cliente.apellido(apellido);
            cliente.dni(dni);
            cliente.email(email);
        });
    }

    @Override
    public void agregarTarjeta(Long idCliente, String nro, String nombre, double fondosDisponibles) {
        inTransactionExecute((em) -> {
            Cliente cliente = em.find(Cliente.class, idCliente);

            if(cliente == null) {
                throw new RuntimeException("No existe el cliente solicitado");
            }

            Tarjeta tarjeta = new Tarjeta(fondosDisponibles, nombre, nro);
            cliente.agregarTarjeta(tarjeta);
        });
    }

    @Override
    public List listarTarjetas(Long idCliente) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            Cliente cliente = em.find(Cliente.class, idCliente);

            if(cliente == null) {
                throw new RuntimeException("No existe el cliente solicitado");
            }

            return cliente.tarjetas();
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
