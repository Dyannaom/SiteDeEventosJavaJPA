package br.com.gft.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.entities.DiaDeEvento;
import br.com.gft.entities.PontuacaoPorGrupo;
import br.com.gft.entities.StatusPresenca;

@Repository
public interface StatusPresencaRepository extends JpaRepository<StatusPresenca, Long>{
	List<StatusPresenca> findByPontuacaoPorGrupoAndDiaDeEvento(PontuacaoPorGrupo pontuacaoPorGrupo, DiaDeEvento diaDeEvento);
}
