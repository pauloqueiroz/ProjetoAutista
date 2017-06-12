package br.com.ufpi.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import br.com.ufpi.model.Estudante;

/**
 * 
 * @author Paulo Sergio
 *
 */
@Stateless
public class EstudanteDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;

	public void adicionar(Estudante aluno) {
		em.persist(aluno);
	}

	public void atualizar(Estudante aluno) {
		em.merge(aluno);
	}
	
	public Estudante buscarPorId(Long id){
		TypedQuery<Estudante> query = em.createQuery("Select e from Estudante e where e.id = :idEstudante", Estudante.class);
		query.setParameter("idEstudante", id);
		Estudante estudante = null;
		try{
			estudante = query.getSingleResult();
		}catch(Exception e){
			System.out.println("Estudante não encontrado.");
			e.printStackTrace();
		}
		return estudante; 
	}
	
	public List<Estudante> listarTodos(){
		List<Estudante> estudantes = new ArrayList<>();
		estudantes = em.createQuery("Select e from Estudante e", Estudante.class).getResultList();
		return estudantes;	 
	}

	public List<Estudante> listarEstudantes(String nomeEstudante, int first, int pageSize,
			List<SortMeta> multiSortMeta) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<Estudante> estudanteQuery = criteriaBuilder.createQuery(Estudante.class);
		Root<Estudante> estudanteRoot = estudanteQuery.from(Estudante.class);

		if (multiSortMeta != null) {
			for (SortMeta sortMeta : multiSortMeta) {
				if (sortMeta.getSortOrder() == SortOrder.ASCENDING) {
					if (sortMeta.getSortField().contains(".")) {
						String[] campos = sortMeta.getSortField().split("\\.");
						estudanteQuery.orderBy(criteriaBuilder.asc(estudanteRoot.get(campos[0]).get(campos[1])));
					} else {
						estudanteQuery.orderBy(criteriaBuilder.asc(estudanteRoot.get(sortMeta.getSortField())));
					}
				} else if (sortMeta.getSortOrder() == SortOrder.DESCENDING) {
					if (sortMeta.getSortField().contains(".")) {
						String[] campos = sortMeta.getSortField().split("\\.");
						estudanteQuery.orderBy(criteriaBuilder.desc(estudanteRoot.get(campos[0]).get(campos[1])));
					} else {
						estudanteQuery.orderBy(criteriaBuilder.desc(estudanteRoot.get(sortMeta.getSortField())));
					}
				}
			}
		}
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (nomeEstudante != null && !nomeEstudante.equals("")) {
			Predicate nomePredicate = criteriaBuilder.like(
					criteriaBuilder.lower(
							estudanteRoot.<String> get("nome")),
					"%" + nomeEstudante.toLowerCase() + "%");
			predicates.add(nomePredicate);
		}
		if (!CollectionUtils.isEmpty(predicates))
			estudanteQuery.where(predicates.toArray(new Predicate[] {}));
		TypedQuery<Estudante> query = em.createQuery(estudanteQuery);
		query.setFirstResult(first);
		query.setMaxResults(pageSize);

		List<Estudante> lista = query.getResultList();

		return lista;
	}

	public int contarEstudantes() {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Long> estudanteQuery = criteriaBuilder.createQuery(Long.class);
		Root<Estudante> estudanteRoot = estudanteQuery.from(Estudante.class);
		TypedQuery<Long> query = em.createQuery(estudanteQuery.select(criteriaBuilder.count(estudanteRoot)));

		return query.getSingleResult().intValue();
	}

	public List<Estudante> buscarEstudante(String nome) {
		TypedQuery<Estudante> query = em.createQuery("Select e from Estudante e where upper(e.nome) like:nome", Estudante.class);
		query.setParameter("nome", "%"+nome.toUpperCase()+"%");
		List<Estudante> estudantes = query.getResultList();
		return estudantes;
	}
	
	public int contarEstudantes(String nome) {
		String hql = "Select count(e.id) from Estudante e";
		
		if(!StringUtils.isEmpty(nome))
			hql += " where upper(e.nome) like:nome";
		
		TypedQuery<Long> query = em.createQuery(hql, Long.class);
		if(!StringUtils.isEmpty(nome))
			query.setParameter("nome", "%"+nome.toUpperCase()+"%");
		
		Long count = query.getSingleResult();
		return count.intValue();
	}

}
