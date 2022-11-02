package br.com.gft.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public List<PontuacaoPorGrupo> ordemDaPontuacaoPorGrupo(Ranking ranking) {

		Collections.reverse(ranking.getPontuacaoPorGrupo());

		return ranking.getPontuacaoPorGrupo();

	}


}
