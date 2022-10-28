package br.com.gft.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Grupo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message = "Nome não pode ser vazio")
	private String nome;
	private Integer quantidadeDePessoas;
	@OneToMany(mappedBy = "grupo")
	private List<ParticipanteEvento> listaDeParticipantesDoGrupo;
	@ManyToOne
	private Evento evento;
	@OneToOne(mappedBy= "grupo")
	private PontuacaoPorGrupo pontuacaoPorGrupo;
	
	public Grupo() {
		this.quantidadeDePessoas = 0;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getQuantidadeDePessoas() {
		return quantidadeDePessoas;
	}
	public void setQuantidadeDePessoas(Integer quantidadeParticipantes) {
		this.quantidadeDePessoas = quantidadeParticipantes;
	}
	public List<ParticipanteEvento> getListaDeParticipantesDoGrupo() {
		return listaDeParticipantesDoGrupo;
	}
	public void setListaDeParticipantesDoGrupo(List<ParticipanteEvento> listaDeParticipantesDoGrupo) {
		this.listaDeParticipantesDoGrupo = listaDeParticipantesDoGrupo;
	}
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public PontuacaoPorGrupo getPontuacaoPorGrupo() {
		return pontuacaoPorGrupo;
	}

	public void setPontuacaoPorGrupo(PontuacaoPorGrupo pontuacaoPorGrupo) {
		this.pontuacaoPorGrupo = pontuacaoPorGrupo;
	}
	
}
