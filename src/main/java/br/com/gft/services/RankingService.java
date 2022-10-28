package br.com.gft.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.entities.Grupo;
import br.com.gft.entities.PontuacaoPorGrupo;
import br.com.gft.entities.Ranking;
import br.com.gft.repositories.PontuacaoPorGrupoRepository;
import br.com.gft.repositories.RankingRepository;

@Service
public class RankingService {

	@Autowired
	RankingRepository rankingRepository;
	@Autowired
	PontuacaoPorGrupoRepository pontuacaoPorGrupoRepository;

	public List<PontuacaoPorGrupo> ordemDaPontuacaoPorGrupo(Long id) {

		Optional<Ranking> ranking = rankingRepository.findById(id);

		Collections.reverse(ranking.get().getPontuacaoPorGrupo());

		return ranking.get().getPontuacaoPorGrupo();

	}

	public Grupo mostrarGrupoVencedor(Long id) {

		Optional<Ranking> ranking = rankingRepository.findById(id);

		ranking.get().setGrupoVencedor(ordemDaPontuacaoPorGrupo(id).get(0).getGrupo());

		return rankingRepository.findById(id).get().getGrupoVencedor();
		
	}

}
