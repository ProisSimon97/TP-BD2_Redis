package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.CategoriaService;
import ar.unrn.tp.modelo.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceJPA implements CategoriaService {

    private final EntityManagerFactory emf;

    public CategoriaServiceJPA(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Categoria> listarCategorias() {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Categoria> q = em.createQuery("SELECT c FROM Categoria c", Categoria.class);
            return q.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }
}
