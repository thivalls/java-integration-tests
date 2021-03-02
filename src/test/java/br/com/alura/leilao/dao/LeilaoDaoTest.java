package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class LeilaoDaoTest {
    private LeilaoDao leilaoDao;
    private EntityManager em;

    @BeforeEach
    void beforEach() {
        this.em = JPAUtil.getEntityManager();
        this.leilaoDao = new LeilaoDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    void afterEach() {
        em.getTransaction().rollback();
    }

    @Test
    void deveCriarUmLeilao() {
        Usuario fulano = createUserToTest("fulano", "fulano@gmail.com", "password");
        Leilao leilao = createLeilaoToTest(
                "Baú Tesouro Pirata ano de 1900",
                new BigDecimal(1000),
                LocalDate.now(),
                fulano
        );
        this.leilaoDao.salvar(leilao);
        Leilao saved = this.leilaoDao.buscarPorId(leilao.getId());
        Assertions.assertNotNull(saved);
        Assertions.assertEquals("Baú Tesouro Pirata ano de 1900", saved.getNome());
        Assertions.assertEquals(new BigDecimal(1000), saved.getValorInicial());
        Assertions.assertEquals(LocalDate.now(), saved.getDataAbertura());
        Assertions.assertEquals(fulano.getId(), saved.getUsuario().getId());
    }

    @Test
    void deveAtualizarUmLeilao() {
        Usuario fulano = createUserToTest("fulano", "fulano@gmail.com", "password");
        Leilao leilao = createLeilaoToTest("Baú Tesouro Pirata ano de 1900", new BigDecimal(1000), LocalDate.now(), fulano);
        this.leilaoDao.salvar(leilao);
        leilao.setNome("Nome atualizado");
        this.leilaoDao.salvar(leilao);
        Assertions.assertEquals("Nome atualizado", leilao.getNome());
    }

    @Test
    void deveRetornarTodosLeiloes() {
        Usuario fulano = createUserToTest("fulano", "fulano@gmail.com", "password");
        createLeilaoToTest("Baú Tesouro Pirata ano de 1900 1", new BigDecimal(1000), LocalDate.now(), fulano);
        createLeilaoToTest("Baú Tesouro Pirata ano de 1900 2", new BigDecimal(1000), LocalDate.now(), fulano);
        createLeilaoToTest("Baú Tesouro Pirata ano de 1900 3", new BigDecimal(1000), LocalDate.now(), fulano);

        List<Leilao> leilaos = this.leilaoDao.buscarTodos();
        Assertions.assertEquals(3, leilaos.size());
    }

    private Leilao createLeilaoToTest(String name, BigDecimal valorInicial, LocalDate data, Usuario usuario) {
        Leilao testLeilao = new Leilao(name, valorInicial, data, usuario);
        em.persist(testLeilao);
        return testLeilao;
    }

    private Usuario createUserToTest(String name, String email, String password) {
        Usuario testUser = new Usuario(name, email, password);
        em.persist(testUser);
        return testUser;
    }
}