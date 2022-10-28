package br.com.gft.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.entities.DiaDeEvento;
import br.com.gft.entities.Evento;
import br.com.gft.repositories.DiaDeEventoRepository;

@Service
public class DiaDeEventoService {
	
	@Autowired
	DiaDeEventoRepository diaDeEventoRepository;
	
	public List<DiaDeEvento> listarDiasPorEvento(Evento evento){
		return diaDeEventoRepository.findByEvento(evento);
	}
	
	public DiaDeEvento obterDiaDeEvento(Long id){
		Optional<DiaDeEvento> diaDeEvento = diaDeEventoRepository.findById(id);
		return diaDeEvento.get();
	}

}
