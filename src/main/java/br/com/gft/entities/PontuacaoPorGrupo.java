package br.com.gft.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class PontuacaoPorGrupo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	private Grupo grupo;
	@ManyToOne
	private Ranking ranking;
	@OneToMany(mappedBy="pontuacaoPorGrupo")
	private List<StatusPresenca> listaStatusPresenca;
	private int pontuacaoBonusAtividade;
	private int pontuacaoBonusPresenca;
	private int pontuacaoFinal;
	
	public PontuacaoPorGrupo() {
		super();
		this.pontuacaoBonusAtividade = 0;
		this.pontuacaoBonusPresenca = 0;
		this.pontuacaoFinal = 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Ranking getRanking() {
		return ranking;
	}

	public void setRanking(Ranking ranking) {
		this.ranking = ranking;
	}

	public List<StatusPresenca> getListaStatusPresenca() {
		return listaStatusPresenca;
	}

	public void setListaStatusPresenca(List<StatusPresenca> listaStatusPresenca) {
		this.listaStatusPresenca = listaStatusPresenca;
	}

	public int getPontuacaoBonusAtividade() {
		return pontuacaoBonusAtividade;
	}

	public void setPontuacaoBonusAtividade(int pontuacaoBonusAtividade) {
		this.pontuacaoBonusAtividade = pontuacaoBonusAtividade;
	}

	public int getPontuacaoBonusPresenca() {
		return pontuacaoBonusPresenca;
	}

	public void setPontuacaoBonusPresenca(int pontuacaoBonusPresenca) {
		this.pontuacaoBonusPresenca = pontuacaoBonusPresenca;
	}

	public int getPontuacaoFinal() {
		return pontuacaoFinal;
	}

	public void setPontuacaoFinal(int pontuacaoFinal) {
		this.pontuacaoFinal = pontuacaoFinal;
	}

}

