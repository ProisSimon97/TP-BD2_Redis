package ar.unrn.tp;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.jpa.servicios.ClienteServiceJPA;
import ar.unrn.tp.jpa.servicios.ProductoServiceJPA;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.promocion.Promocion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	private static final String UNIT_NAME = "jpa-pgsql";
	private static EntityManagerFactory emf;

	@Autowired
	ClienteService clienteService;

	@Autowired
	DescuentoService descuentoService;

	@Autowired
	ProductoService productoService;

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext c) {
		return args -> {

			emf = Persistence.createEntityManagerFactory(UNIT_NAME);

			inTransactionExecute((em) -> {
				Categoria categoria = new Categoria("Ropa deportiva");
				em.persist(categoria);
			});

			productoService.crearProducto("777", "Remera", 20000, "Adudas", 1L);
			productoService.crearProducto("656", "Pantalon", 20000, "Adudas", 1L);

			clienteService.crearCliente("Simon", "Preuss", "39870345", "simon@gmail.com");
			clienteService.agregarTarjeta(1L, "7777", "Visa", 15000000);

			descuentoService.crearDescuento("Adudas", LocalDate.now().minusDays(2), LocalDate.now().plusDays(2), 0.5);
			descuentoService.crearDescuentoSobreTotal("Visa", LocalDate.now().minusDays(2), LocalDate.now().plusDays(2), 0.5);

			descuentoService.obtenerPromocionesVigentes(LocalDate.now());

		};
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
	}
}
