package br.com.gft.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class StatusAtividade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private ParticipanteEvento participanteEvento;
	@OneToOne
	private Atividade atividade;
	@ManyToOne
	private StatusPresenca statusPresenca;
	private boolean entregue;
	private boolean entregueAtrasado;
	private boolean naoEntregue;
	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isNaoEntregue() {
		return naoEntregue;
	}

	public void setNaoEntregue(boolean naoEntregue) {
		this.naoEntregue = naoEntregue;
	}

	public ParticipanteEvento getParticipanteEvento() {
		return participanteEvento;
	}
	public void setParticipanteEvento(ParticipanteEvento participanteEvento) {
		this.participanteEvento = participanteEvento;
	}
	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public boolean isEntregue() {
		return entregue;
	}

	public void setEntregue(boolean entregue) {
		this.entregue = entregue;
	}

	public boolean isEntregueAtrasado() {
		return entregueAtrasado;
	}

	public void setEntregueAtrasado(boolean entregueAtrasado) {
		this.entregueAtrasado = entregueAtrasado;
	}

	public StatusPresenca getStatusPresenca() {
		return statusPresenca;
	}

	public void setStatusPresenca(StatusPresenca statusPresenca) {
		this.statusPresenca = statusPresenca;
	}
	
}
