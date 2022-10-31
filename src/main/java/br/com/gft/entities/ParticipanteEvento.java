package br.com.gft.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
	private String quatroLetras;
	@ManyToOne
	private Grupo grupo;
	private Boolean isAtivo;
	private Integer pontuacaoPresenca;
	private Integer pontuacaoAtividadeDoEvento;
	
	public ParticipanteEvento() {
		this.pontuacaoPresenca = 0;
		this.pontuacaoAtividadeDoEvento = 0;
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
	public Integer getPontuacaoPresenca() {
		return pontuacaoPresenca;
	}
	public void setPontuacaoPresenca(Integer pontuacaoPresenca) {
		this.pontuacaoPresenca = pontuacaoPresenca;
	}
	public Integer getPontuacaoAtividadeDoEvento() {
		return pontuacaoAtividadeDoEvento;
	}
	public void setPontuacaoAtividadeDoEvento(Integer pontuacaoAtividadeDoEvento) {
		this.pontuacaoAtividadeDoEvento = pontuacaoAtividadeDoEvento;
	}
	
		
}
