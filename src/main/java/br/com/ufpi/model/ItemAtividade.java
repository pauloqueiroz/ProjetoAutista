package br.com.ufpi.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
/**
 * 
 * @author Paulo Sergio
 *
 */
@Entity
public class ItemAtividade implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int ordem;
	
	@ManyToOne
	private ListaAtividade lista;
    
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="atividade_id")
	private Atividade atividade;
	
	private transient boolean deletar;
	
	private transient int ordemEdicao;

	public ItemAtividade() {
	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public ListaAtividade getLista() {
		return lista;
	}

	public void setLista(ListaAtividade lista) {
		this.lista = lista;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}
	
	public boolean isDeletar() {
		return deletar;
	}

	public void setDeletar(boolean deletar) {
		this.deletar = deletar;
	}
	
	public String getOrdemFormatada() {
		return (this.ordem+1)+"º";
	}

	public int getOrdemEdicao() {
		return ordemEdicao;
	}

	public void setOrdemEdicao(int ordemEdicao) {
		this.ordemEdicao = ordemEdicao;
	}

}