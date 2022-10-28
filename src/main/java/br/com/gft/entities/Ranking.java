package br.com.gft.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Ranking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToMany(mappedBy = "ranking")
	private List<PontuacaoPorGrupo> pontuacaoPorGrupo;
	@OneToOne
	private Grupo grupoVencedor;
	
	@OneToOne(mappedBy = "ranking")
	private Evento evento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<PontuacaoPorGrupo> getPontuacaoPorGrupo() {
		return pontuacaoPorGrupo;
	}

	public void setPontuacaoPorGrupo(List<PontuacaoPorGrupo> pontuacaoPorGrupo) {
		this.pontuacaoPorGrupo = pontuacaoPorGrupo;
	}

	public Grupo getGrupoVencedor() {
		return grupoVencedor;
	}

	public void setGrupoVencedor(Grupo grupoVencedor) {
		this.grupoVencedor = grupoVencedor;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	
}

