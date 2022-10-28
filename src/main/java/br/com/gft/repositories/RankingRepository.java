package br.com.gft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.entities.Ranking;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long>{

}
