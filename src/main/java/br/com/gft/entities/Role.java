package br.com.gft.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import br.com.gft.enums.RoleName;

@Entity
@Table(name = "TB_ROLE")
public class Role implements GrantedAuthority, Serializable{
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, unique = true)
	private RoleName roleName;

	@ManyToMany
	private List<Usuario> usuarios;
	
	
	@Override
	public String getAuthority() {
		
		return this.roleName.toString();
	}



	



	



	public RoleName getRoleName() {
		return roleName;
	}

	public void setRoleName(RoleName roleName) {
		this.roleName = roleName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	

	
	

}
