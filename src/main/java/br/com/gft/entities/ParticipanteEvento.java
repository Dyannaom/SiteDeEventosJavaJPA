package br.com.gft.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class ParticipanteEvento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message = "Nome não pode ser branco")
	private String nome;
	private String nivel;
	@Email(message = "@gft.com")
	@NotEmpty(message = "Nome não pode ser branco")
	private String email;
	@Size(min = 4, max = 4, message = "Deve apenas 4 letras")
	@NotEmpty(message = "Nome não pode ser branco")
	@Column(unique = true)
	private String quatroLetras;
	@OneToMany	
	private List<Evento> listaDeEventosQueEstaParticipando;
	
	@ManyToOne
	private Grupo grupo;
	
	private Boolean isAtivo;
	
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
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQuatroLetras() {
		return quatroLetras;
	}
	public void setQuatroLetras(String quatroLetras) {
		this.quatroLetras = quatroLetras;
	}
	public List<Evento> getListaDeEventosQueEstaParticipando() {
		return listaDeEventosQueEstaParticipando;
	}
	public void setListaDeEventosQueEstaParticipando(List<Evento> listaDeEventosQueEstaParticipando) {
		this.listaDeEventosQueEstaParticipando = listaDeEventosQueEstaParticipando;
	}	
	public Boolean getIsAtivo() {
		return isAtivo;
	}
	public void setIsAtivo(Boolean isAtivo) {
		this.isAtivo = isAtivo;
	}
	
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
		
}
