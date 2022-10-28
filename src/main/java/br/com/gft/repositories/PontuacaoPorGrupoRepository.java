package br.com.gft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.entities.PontuacaoPorGrupo;

@Repository
public interface PontuacaoPorGrupoRepository extends JpaRepository<PontuacaoPorGrupo, Long>{

}
