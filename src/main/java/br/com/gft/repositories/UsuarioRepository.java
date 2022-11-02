package br.com.gft.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gft.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID>{
	
	@Query("select u from Usuario u where u.email like :email")
	Usuario findByEmail(@Param("email") String email);
	
	
	Optional<Usuario> findByQuatroLetras(String quatroLetras);

}
