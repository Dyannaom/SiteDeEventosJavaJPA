package br.com.gft.aagp.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "grupos")
public class Grupo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Nome não pode ser vazio")
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "qtd_pessoas")
	private int quantidadeDePessoas;
	
	@Column(name = "status")
	private Boolean isAtivo;
	
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
	public Boolean getisAtivo() {
		return isAtivo;
	}
	public void setIsAtivo(Boolean isAtivo) {
		this.isAtivo = isAtivo;
	}
	
	

}
