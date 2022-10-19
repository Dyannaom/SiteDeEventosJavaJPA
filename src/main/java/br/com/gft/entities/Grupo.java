package br.com.gft.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Grupo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private int quantidadeDePessoas;
	@OneToMany
	private List<ParticipanteEvento> listaDeParticipantesDoGrupo;
	
	
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
	
	

}