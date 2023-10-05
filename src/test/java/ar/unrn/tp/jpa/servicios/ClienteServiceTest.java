package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Tarjeta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;

public class ClienteServiceTest {
    private static final String UNIT_NAME = "jpa-derby-embedded";
    private static ClienteService clienteService;
    private EntityManagerFactory emf;

    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory(UNIT_NAME);

        clienteService = new ClienteServiceJPA(emf);
        clienteService.crearCliente("Simon", "Preuss", "39870345", "simon@gmail.com");
    }

    @Test
    public void Should_BeTrue_When_AClientIsCreated() {
        inTransactionExecute((em) -> {
            Cliente cliente = em.find(Cliente.class, 1L);

            Assertions.assertTrue(cliente.esNombre("Simon"));
            Assertions.assertTrue(cliente.esApellido("Preuss"));
            Assertions.assertTrue(cliente.esDni("39870345"));
            Assertions.assertTrue(cliente.esEmail("simon@gmail.com"));

        });
    }

    @Test
    public void Should_BeTrue_When_AClientIsModified() {
        clienteService.modificarCliente(1L, "Saimon", "Pelotto", "39870344", "simonpreuss@gmail.com");

        inTransactionExecute(
                (em) -> {
                    Cliente cliente = em.find(Cliente.class, 1L);

                    Assertions.assertTrue(cliente.esNombre("Saimon"));
                    Assertions.assertTrue(cliente.esApellido("Pelotto"));
                    Assertions.assertTrue(cliente.esDni("39870344"));
                    Assertions.assertTrue(cliente.esEmail("simonpreuss@gmail.com"));
                }
        );
    }

    @Test
    public void Should_BeTrue_When_ACardIsAdded() {
        clienteService.agregarTarjeta(1L, "123456", "MemeCard", 150000);

        inTransactionExecute(
                (em) -> {
                    Cliente cliente = em.find(Cliente.class, 1L);
                    Assertions.assertTrue(cliente.misTarjetas() > 0);
                }
        );
    }

    @Test
    public void Should_BeTrue_When_ClientCardListIsNotEmpty() {
        clienteService.agregarTarjeta(1L, "123456", "MemeCard", 150000);

        inTransactionExecute(
                (em) -> {
                    List<Tarjeta> tarjetas = clienteService.listarTarjetas(1L);
                    Assertions.assertTrue(!tarjetas.isEmpty());
                }
        );
    }

    @Test
    public void Should_ThrowRuntimeException_When_ClientDontExist() {
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            clienteService.modificarCliente(5L,"Simo", "Preus", "39870345", "simon@gmail.com");
        });

        Assertions.assertTrue(exception.getMessage().contains("No existe el cliente solicitado"));
    }

    @Test
    public void Should_ThrowRuntimeException_When_ClientDniAlreadyExists() {
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            clienteService.crearCliente("Simon", "Preuss", "39870345", "simon@gmail.com");
        });

        Assertions.assertTrue(exception.getMessage().contains("Ya existe un usuario con ese dni"));
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
