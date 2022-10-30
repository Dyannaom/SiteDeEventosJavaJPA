package br.com.gft.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.entities.Atividade;
import br.com.gft.entities.StatusAtividade;

@Repository
public interface StatusAtividadeRepository extends JpaRepository<StatusAtividade, Long>{
	List<StatusAtividade> findByAtividade(Atividade atividade);
}
