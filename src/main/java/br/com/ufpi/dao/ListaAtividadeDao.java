package br.com.ufpi.dao;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.ufpi.model.ListaAtividade;

/**
 * 
 * @author Paulo Sergio
 *
 */
@Stateless
public class ListaAtividadeDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;

	public ListaAtividadeDao() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int buscarListaPorNome(String nome) {
		String hql = "Select count(l.id) from ListaAtividade l where l.nome = :nome";
		TypedQuery<Long> query = em.createQuery(hql, Long.class);
		query.setParameter("nome", nome);
		return query.getSingleResult().intValue();
	}

	public void adicionar(ListaAtividade lista) {
		em.persist(lista);
	}

	public void atualizar(ListaAtividade lista) {
		em.merge(lista);
	}
}
