package br.com.gft.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.entities.Atividade;
import br.com.gft.entities.DiaDeEvento;
import br.com.gft.entities.ParticipanteEvento;
import br.com.gft.entities.PontuacaoPorGrupo;
import br.com.gft.entities.StatusAtividade;
import br.com.gft.entities.StatusPresenca;
import br.com.gft.repositories.ParticipanteEventoRepository;
import br.com.gft.repositories.StatusAtividadeRepository;
import br.com.gft.repositories.StatusPresencaRepository;



@Service
public class ParticipanteEventoService {
	
	@Autowired
	ParticipanteEventoRepository participanteEventoRepository;
	@Autowired
	StatusPresencaRepository statusPresencaRepository;
	@Autowired
	StatusAtividadeRepository statusAtividadeRepository;

	
	public void salvarParticipante(ParticipanteEvento participante) {
		participanteEventoRepository.save(participante);
		
		List<DiaDeEvento> listaDeDiaEventos = participante.getGrupo().getEvento().getListaDeDias();
		

		for(DiaDeEvento diaDeEvento: listaDeDiaEventos) {
			StatusPresenca statusPresenca = new StatusPresenca();
			statusPresenca.setParticipanteEvento(participante);
			PontuacaoPorGrupo pontuacaoPorGrupo = participante.getGrupo().getPontuacaoPorGrupo();
			statusPresenca.setPontuacaoPorGrupo(pontuacaoPorGrupo);
			statusPresenca.setDiaDeEvento(diaDeEvento);
			statusPresencaRepository.save(statusPresenca);
			
			for(Atividade atividade: diaDeEvento.getAtividadesDoDia()) {
				StatusAtividade statusAtividade = new StatusAtividade();
				statusAtividade.setParticipanteEvento(participante);
				statusAtividade.setStatusPresenca(statusPresenca);
				statusAtividade.setAtividade(atividade);
				statusAtividadeRepository.save(statusAtividade);
			}
		}
		
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
		ParticipanteEvento participanteAux;
		
		if(participante.isEmpty()) {
			throw new Exception("Participante não Cadastrado");
		}	
		participanteAux  =  participante.get();
		
		if(participanteAux.getIsAtivo() == false) {
			participanteAux.setIsAtivo(true);	
			participanteEventoRepository.save(participanteAux);
		}else {
		participanteAux.setIsAtivo(false);	
		participanteEventoRepository.save(participanteAux);
			}		
	}
	
	}
	