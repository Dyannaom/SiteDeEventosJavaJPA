package br.com.gft.aagp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.aagp.entities.ParticipanteEvento;



@Repository
public interface ParticipanteEventoRepository extends JpaRepository<ParticipanteEvento, Long> {
	
	List<ParticipanteEvento> findByQuatroLetrasContainsAndNomeContains(String quatroLetras, String nome);
	List<ParticipanteEvento> findByNome(String nome);
	

}