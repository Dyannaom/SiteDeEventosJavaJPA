package br.com.gft.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.entities.Grupo;
import br.com.gft.entities.ParticipanteEvento;
import br.com.gft.repositories.GrupoRepository;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;
	

	public Grupo salvarGrupo(Grupo grupo) {
		return grupoRepository.save(grupo);
	}

	public List<Grupo> listarGrupos(String nome, int quantidadeDePessoas) {

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

//	public List<ParticipanteEvento> listarParticipantesdoGrupo(String nome, String quatroLetras) {
//
//		if (nome != null || quatroLetras != null)
//		
//			return grupoRepository.findByNomeContainsAndQuatroLetrasContains(nome, quatroLetras);
//
//		return ParticipanteEvento.cadastrarParticipante();
//
//	}

	public Grupo obterGrupo(Long id) throws Exception {

		Optional<Grupo> grupo = grupoRepository.findById(id);

		if (grupo.isEmpty()) {
			throw new Exception("Grupo não encontrado");
		}

		return grupo.get();
	}

	public void desativarGrupo(Long id) throws Exception {
		Optional<Grupo> grupo = grupoRepository.findById(id);

		if (grupo.isEmpty()) {
			throw new Exception("Grupo não encontrado");
		}

		grupo.get().setIsAtivo(false);;
	}

}
