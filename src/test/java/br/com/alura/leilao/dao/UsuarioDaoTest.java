package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

class UsuarioDaoTest {
    private UsuarioDao userDao;
    private EntityManager em;

    @BeforeEach
    public void beforEach() {
        this.em = JPAUtil.getEntityManager();
        this.userDao = new UsuarioDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    public void afterEach() {
        em.getTransaction().rollback();
    }

    @Test
    void deveEncontrarUmUsuarioPeloNome() {
        createUserToTest("Fulano", "fulano@gmail.com", "$2a$10$8MeF8YTUTv22DVthkhOs3.WGT4W1Wp1xRXcRxTM12MgDzRviDpw7i");
        Usuario fulano = this.userDao.buscarPorUsername("Fulano");
        Assertions.assertNotNull(fulano);
    }

    @Test
    void naoDeveEncontrarUmUsuarioPeloNome() {
        Assertions.assertThrows(NoResultException.class, () -> this.userDao.buscarPorUsername("Fulano"));
    }

    @Test
    void naoRetornarTodosUsuariosCadastrados() {
        createUserToTest("fulano", "fulano@gmail.com", "password");
        createUserToTest("ciclano", "ciclano@gmail.com", "password");
        createUserToTest("beltrano", "beltrano@gmail.com", "password");

        List<Usuario> users = this.userDao.all();
        Assertions.assertEquals(3, users.size());
    }

    private void createUserToTest(String name, String email, String password) {
        Usuario testUser = new Usuario(name, email, password);
        em.persist(testUser);
    }
}