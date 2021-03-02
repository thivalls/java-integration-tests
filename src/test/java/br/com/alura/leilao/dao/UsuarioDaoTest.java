package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

class UsuarioDaoTest {
    private UsuarioDao userDao;

    @Test
    void deveEncontrarUmUsuarioPeloNome() {
        EntityManager em = JPAUtil.getEntityManager();
        Usuario testUser = new Usuario("Fulano", "fulano@gmail.com", "$2a$10$8MeF8YTUTv22DVthkhOs3.WGT4W1Wp1xRXcRxTM12MgDzRviDpw7i");
        em.getTransaction().begin();
        em.persist(testUser);
        em.getTransaction().commit();
        this.userDao = new UsuarioDao(em);
        Usuario fulano = this.userDao.buscarPorUsername("Fulano");
        Assertions.assertNotNull(fulano);
    }

    @Test
    void naoDeveEncontrarUmUsuarioPeloNome() {
        EntityManager em = JPAUtil.getEntityManager();
        this.userDao = new UsuarioDao(em);
        Assertions.assertThrows(NoResultException.class, () -> this.userDao.buscarPorUsername("Fulano"));
    }
}