package br.com.gft.aagp.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ParticipanteEvento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String nivel;
	private String email;
	private String quatroLetras;
	@OneToMany	
	private List<Evento> listaDeEventosQueEstaParticipando;
	
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
	
	
	
	
	

	
	
	
}
