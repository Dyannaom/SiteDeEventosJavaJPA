package br.com.gft.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class StatusPresenca {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private ParticipanteEvento participanteEvento;
	@ManyToOne
	private DiaDeEvento diaDeEvento;
	@ManyToOne
	private PontuacaoPorGrupo pontuacaoPorGrupo;
	@OneToMany(mappedBy = "statusPresenca")
	private List<StatusAtividade> listaStatusAtividade;
	private boolean presente;
	private boolean atrasado;
	private boolean ausente;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ParticipanteEvento getParticipanteEvento() {
		return participanteEvento;
	}
	public void setParticipanteEvento(ParticipanteEvento participanteEvento) {
		this.participanteEvento = participanteEvento;
	}
	public DiaDeEvento getDiaDeEvento() {
		return diaDeEvento;
	}
	public void setDiaDeEvento(DiaDeEvento diaDeEvento) {
		this.diaDeEvento = diaDeEvento;
	}
	public boolean isPresente() {
		return presente;
	}
	public void setPresente(boolean presente) {
		this.presente = presente;
	}
	public boolean isAtrasado() {
		return atrasado;
	}
	public void setAtrasado(boolean atrasado) {
		this.atrasado = atrasado;
	}
	public boolean isAusente() {
		return ausente;
	}
	public void setAusente(boolean ausente) {
		this.ausente = ausente;
	}
	public PontuacaoPorGrupo getPontuacaoPorGrupo() {
		return pontuacaoPorGrupo;
	}
	public void setPontuacaoPorGrupo(PontuacaoPorGrupo pontuacaoPorGrupo) {
		this.pontuacaoPorGrupo = pontuacaoPorGrupo;
	}
	public List<StatusAtividade> getListaStatusAtividade() {
		return listaStatusAtividade;
	}
	public void setListaStatusAtividade(List<StatusAtividade> listaStatusAtividade) {
		this.listaStatusAtividade = listaStatusAtividade;
	}
	
	
	
}
