package br.com.gft.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.entities.Grupo;
import br.com.gft.entities.ParticipanteEvento;
import br.com.gft.entities.PontuacaoPorGrupo;
import br.com.gft.repositories.GrupoRepository;
import br.com.gft.repositories.ParticipanteEventoRepository;
import br.com.gft.repositories.PontuacaoPorGrupoRepository;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private ParticipanteEventoRepository participanteEventoRepository;
	
	@Autowired
	private PontuacaoPorGrupoRepository pontuacaoPorGrupoRepository;

	public Grupo salvarGrupo(Grupo grupo) {
		grupo = grupoRepository.save(grupo);
		PontuacaoPorGrupo pontuacao = new PontuacaoPorGrupo();
		pontuacao.setGrupo(grupo);
		pontuacao.setRanking(grupo.getEvento().getRanking());
		pontuacaoPorGrupoRepository.save(pontuacao);
		return grupo;
	}

	public List<Grupo> listarGrupos(String nome, Integer quantidadeDePessoas) {

		if (nome != null || quantidadeDePessoas <=0)
			return listarGruposPorNomeEQuantidadeDePessoas(nome, quantidadeDePessoas);

		return listarTodosGrupos();
	}

	public List<Grupo> listarGruposPorNomeEQuantidadeDePessoas(String nome, Integer quantidadeDePessoas) {
		return grupoRepository.findByNomeContainsAndQuantidadeDePessoasContains(nome, quantidadeDePessoas);
	}

	public List<Grupo> listarTodosGrupos() {
		return grupoRepository.findAll();
	}

	public List<ParticipanteEvento> listarParticipantesdoGrupo(String nome, String quatroLetras) throws Exception {

		if (nome != null || quatroLetras != null)
		
			return participanteEventoRepository.findByQuatroLetrasContainsAndNomeContains(quatroLetras, nome);

			throw new Exception("Participante não encontrado");
			

	}
	
	public void SomarQuantidadeParticipantesNoGrupo(Grupo grupo) {
		int quantidadeParticipantes = participanteEventoRepository.findByGrupo(grupo).size();
		
		try{
//			Grupo grupo = obterGrupo(id);
			grupo.setQuantidadeDePessoas(quantidadeParticipantes);
			grupoRepository.save(grupo);
		}catch(Exception e) {
			
		}
	}

	public Grupo obterGrupo(Long id) throws Exception {
		Optional<Grupo> grupo = grupoRepository.findById(id);
		if (grupo.isEmpty()) {
			throw new Exception("Grupo não encontrado");
		}
		return grupo.get();
	}
	
	public void excluirGrupo(Long id) throws Exception {
		Optional<Grupo> grupo = grupoRepository.findById(id);
		if(grupo.isEmpty())
			throw new Exception("Grupo não encontrado");
		else
			grupoRepository.delete(grupo.get());
	}
}
