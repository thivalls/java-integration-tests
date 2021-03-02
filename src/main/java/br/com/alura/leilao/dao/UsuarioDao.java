package br.com.alura.leilao.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.alura.leilao.model.Usuario;

import java.util.List;

@Repository
public class UsuarioDao {


	private EntityManager em;

	@Autowired
	UsuarioDao(EntityManager em) {
		this.em = em;
	}

	public List<Usuario> all() {
		Query $query = em.createQuery("select u from Usuario u");
		return $query.getResultList();
	}

	public Usuario buscarPorUsername(String username) {
		return em.createQuery("SELECT u FROM Usuario u WHERE u.nome = :username", Usuario.class)
				.setParameter("username", username)
				.getSingleResult();
	}

	public void deletar(Usuario usuario) {
		em.remove(usuario);
	}

}
