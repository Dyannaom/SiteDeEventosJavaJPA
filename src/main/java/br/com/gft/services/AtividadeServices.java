package br.com.gft.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.entities.Atividade;
import br.com.gft.repositories.AtividadeReposititory;

@Service
public class AtividadeServices {
	
	@Autowired
	AtividadeReposititory atividadeRepository;
	
	public Atividade saveAtividade(Atividade atividade) {
		return atividadeRepository.save(atividade);
	}
	
	public Atividade getAtividade(Long id) throws Exception{
		Optional<Atividade> atividade = atividadeRepository.findById(id);
		
		if(atividade.isEmpty()) {
			throw new Exception("Atividade não encontrada");
		}
		
		return atividade.get();
	}
	
	public void deleteAtividade(Long id) throws Exception{
		Optional<Atividade> atividade = atividadeRepository.findById(id);
		
		if(atividade.isEmpty()) {
			throw new Exception("Atividade não foi encontrada");
		}
		atividadeRepository.deleteById(id);
	}
	
	public List<Atividade> listAtividade(){
		return atividadeRepository.findAll();
	}
	
	
}
