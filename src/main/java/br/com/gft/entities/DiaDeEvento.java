package br.com.gft.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class DiaDeEvento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate data;
	@OneToMany(mappedBy = "diaDeEvento")
	private List<Atividade> atividadesDoDia;
	@ManyToOne
	private Evento evento;
	@OneToMany(mappedBy = "diaDeEvento")
	private List<StatusPresenca> listaDePresenca;

	public DiaDeEvento() {
	}

	public DiaDeEvento(LocalDate data, Evento evento) {
		this.data = data;
		this.evento = evento;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public List<Atividade> getAtividadesDoDia() {
		return atividadesDoDia;
	}
	public void setAtividadesDoDia(List<Atividade> atividadesDoDia) {
		this.atividadesDoDia = atividadesDoDia;
	}
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}

}
