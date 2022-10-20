package br.com.gft.aagp.repositories;

import org.springframework.stereotype.Repository;
import br.com.gft.aagp.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long>{

}
