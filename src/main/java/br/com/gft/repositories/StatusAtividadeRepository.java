package br.com.gft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.entities.StatusAtividade;

@Repository
public interface StatusAtividadeRepository extends JpaRepository<StatusAtividade, Long>{

}
