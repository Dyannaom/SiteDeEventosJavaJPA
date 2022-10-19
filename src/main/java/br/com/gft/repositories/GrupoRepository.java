package br.com.gft.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.entities.Grupo;
import br.com.gft.entities.ParticipanteEvento;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long>{
	
	List<Grupo> findByNomeContainsAndQuantidadeDePessoasContains(String nome, int quantidadeDePessoas);

//	List<ParticipanteEvento> findByNomeContainsAndQuatroLetrasContains(String nome, String quatroLetras);


}
