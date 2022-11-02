package br.com.gft.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails, Serializable {
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotEmpty(message = "Nome não pode ser vazio")
	private String nome;
	
	@Email(message = "@gft.com" )
	@NotEmpty(message = "Nome não pode ser vazio")
	@Column(nullable = false, unique = true)
	private String email;
	
	@NotEmpty(message = "É necessario inserir as suas 4 letras")
	@Size(min=4, max = 4, message = "Devem ser 4 letras")
	@Column(nullable = false, unique = true)
	private String quatroLetras;
	
	@Column(name = "senha", nullable = false)
	private String senha;
	
	@Column(name = "status", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean status;
	
	@ManyToMany
	@JoinTable(name = "TB_USUARIO_ROLES",
				joinColumns = @JoinColumn(name="usuario_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;
	
		
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}	
	
	@Override
	public String getPassword() {	
		return this.senha;
	}
	
	@Override
	public String getUsername() {
		return this.quatroLetras;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
	

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
