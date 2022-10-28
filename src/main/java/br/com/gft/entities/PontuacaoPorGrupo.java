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
	private Integer pontuacaoBonusAtividade;
	private Integer pontuacaoBonusPresenca;
	private Integer pontuacaoFinal;
	

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

	public int getPontuacaoBonusAtividade() {
		return pontuacaoBonusAtividade;
	}

	public void setPontuacaoBonusAtividade(Integer pontuacaoBonusAtividade) {
		this.pontuacaoBonusAtividade = pontuacaoBonusAtividade;
	}

	public Integer getPontuacaoBonusPresenca() {
		return pontuacaoBonusPresenca;
	}

	public void setPontuacaoBonusPresenca(Integer pontuacaoBonusPresenca) {
		this.pontuacaoBonusPresenca = pontuacaoBonusPresenca;
	}

	public Integer getPontuacaoFinal() {
		return pontuacaoFinal;
	}

	public void setPontuacaoFinal(Integer pontuacaoFinal) {
		this.pontuacaoFinal = pontuacaoFinal;
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
	
	


}

