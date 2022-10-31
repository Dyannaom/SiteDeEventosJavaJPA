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
	private int quantidadeDePessoas;
	@OneToMany(mappedBy = "grupo")
	private List<ParticipanteEvento> listaDeParticipantesDoGrupo;
	@ManyToOne
	private Evento evento;
	@OneToOne(mappedBy= "grupo")
	private PontuacaoPorGrupo pontuacaoPorGrupo;
	
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
	public int getQuantidadeDePessoas() {
		return quantidadeDePessoas;
	}
	public void setQuantidadeDePessoas(int quantidadeDePessoas) {
		this.quantidadeDePessoas = quantidadeDePessoas;
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
