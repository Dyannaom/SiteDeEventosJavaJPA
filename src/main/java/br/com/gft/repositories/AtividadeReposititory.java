package br.com.gft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.entities.Atividade;

@Repository
public interface AtividadeReposititory extends JpaRepository<Atividade, Long>{
	
}
