package br.com.gft.aagp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.aagp.entities.ParticipanteEvento;
import br.com.gft.aagp.repositories.ParticipanteEventoRepository;



@Service
public class ParticipanteEventoService {
	
	@Autowired
	ParticipanteEventoRepository participanteEventoRepository;

	
	public void salvarParticipante(ParticipanteEvento participante) {
		participanteEventoRepository.save(participante);
	}
	
	public List<ParticipanteEvento> listarParticipantesDoEvento() {
		
		return participanteEventoRepository.findAll();
	}
	
	public List<ParticipanteEvento> listarParticipantesPorQuatroLetrasOuNome(String quatroLetras, String nome) {
		
		if(nome!= null || quatroLetras!=null) {
		return participanteEventoRepository.findByQuatroLetrasContainsAndNomeContains(quatroLetras, nome);
		}
		return listarParticipantesDoEvento();
		}		
	
	
	public List<ParticipanteEvento> listarParticipantesPorNome(String nome){
		return participanteEventoRepository.findByNome(nome);
	}
	
	
	public ParticipanteEvento obterParticipante(Long id) throws Exception{
		
		Optional<ParticipanteEvento> participante = participanteEventoRepository.findById(id);
		
		if(participante.isEmpty()) {
			throw new Exception("Participante não Cadastrado");
		}
		
		return participante.get();
		
	}
	
		
	public void desativarParticipante(Long id) throws Exception {
		
		Optional<ParticipanteEvento> participante = participanteEventoRepository.findById(id);
		
		if(participante.isEmpty()) {
			throw new Exception("Participante não Cadastrado");
		}
		
		participante.get().setIsAtivo(false);
				
			}		
		
	
	}
	