package br.com.gft.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gft.entities.DiaDeEvento;
import br.com.gft.entities.Evento;

public interface DiaDeEventoRepository extends JpaRepository<DiaDeEvento, Long>{
	
	List<DiaDeEvento> findByEvento(Evento evento);

}
