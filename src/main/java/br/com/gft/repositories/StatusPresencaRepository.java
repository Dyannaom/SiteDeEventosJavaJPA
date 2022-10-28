package br.com.gft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.entities.StatusPresenca;

@Repository
public interface StatusPresencaRepository extends JpaRepository<StatusPresenca, Long>{

}
