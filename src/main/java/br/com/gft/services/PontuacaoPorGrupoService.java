package br.com.gft.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.entities.Evento;
import br.com.gft.entities.Grupo;
import br.com.gft.entities.ParticipanteEvento;
import br.com.gft.entities.PontuacaoPorGrupo;
import br.com.gft.repositories.EventoRepository;
import br.com.gft.repositories.GrupoRepository;
import br.com.gft.repositories.ParticipanteEventoRepository;
import br.com.gft.repositories.PontuacaoPorGrupoRepository;
import br.com.gft.repositories.RankingRepository;

@Service
public class PontuacaoPorGrupoService {

	@Autowired
	EventoRepository eventoRepository;
	@Autowired
	ParticipanteEventoRepository participanteEventoRepository;
	@Autowired
	PontuacaoPorGrupoRepository pontuacaoPorGrupoRepository;
	@Autowired
	GrupoRepository grupoRepository;
	@Autowired
	RankingRepository rankingRepository;

	/*
	 * public Integer conferirBonusPresenca(Long id) {
	 * 
	 * Optional<Evento> evento = eventoRepository.findById(id); Optional<Grupo>
	 * grupo = grupoRepository.findById(id); Optional<PontuacaoPorGrupo>
	 * pontuacaoPorGrupo = pontuacaoPorGrupoRepository.findById(id);
	 * 
	 * Integer soma = 0; Integer bonusAuxiliar =
	 * evento.get().getDiaDeEvento().size()
	 * grupo.get().getListaDeParticipantesDoGrupo().size() * 10;
	 * 
	 * for (ParticipanteEvento participanteEvento1 :
	 * grupo.get().getListaDeParticipantesDoGrupo()) { soma +=
	 * participanteEvento1.getPontuacaoPresenca(); }
	 * 
	 * if (bonusAuxiliar.equals(evento.get().getDiaDeEvento().size() * soma)) {
	 * pontuacaoPorGrupo.get().setPontuacaoBonusPresenca(5); }
	 * 
	 * return pontuacaoPorGrupo.get().getPontuacaoBonusPresenca();
	 * 
	 * }
	 * 
	 * public Integer conferirBonusAtividade(Long id) {
	 * 
	 * Optional<Evento> evento = eventoRepository.findById(id); Optional<Grupo>
	 * grupo = grupoRepository.findById(id); Optional<PontuacaoPorGrupo>
	 * pontuacaoPorGrupo = pontuacaoPorGrupoRepository.findById(id);
	 * 
	 * Integer soma = 0; Integer bonusAuxiliar =
	 * evento.get().getDiaDeEvento().size()
	 * grupo.get().getListaDeParticipantesDoGrupo().size() * (5 * 2);
	 * 
	 * for (ParticipanteEvento participanteEvento1 :
	 * grupo.get().getListaDeParticipantesDoGrupo()) { soma +=
	 * participanteEvento1.getPontuacaoAtividadeDoEvento(); } if
	 * (bonusAuxiliar.equals(evento.get().getDiaDeEvento().size() * soma)) {
	 * pontuacaoPorGrupo.get().setPontuacaoBonusAtividade(3); }
	 * 
	 * return pontuacaoPorGrupo.get().getPontuacaoBonusAtividade();
	 * 
	 * }
	 */

	public Integer pontuacaoFinal(Long id) {

		Optional<Evento> evento = eventoRepository.findById(id);
		Optional<Grupo> grupo = grupoRepository.findById(id);
		Optional<PontuacaoPorGrupo> pontuacaoPorGrupo = pontuacaoPorGrupoRepository.findById(id);

		Integer somaPresenca = 0;
		Integer somaAtividade = 0;

		for (ParticipanteEvento participanteEvento1 : grupo.get().getListaDeParticipantesDoGrupo()) {
			somaPresenca += participanteEvento1.getPontuacaoPresenca();
		}
		for (ParticipanteEvento participanteEvento1 : grupo.get().getListaDeParticipantesDoGrupo()) {
			somaAtividade += participanteEvento1.getPontuacaoAtividadeDoEvento();
		}

		pontuacaoPorGrupo.get().setPontuacaoFinal(pontuacaoPorGrupo.get().getPontuacaoBonusAtividade()
				+ pontuacaoPorGrupo.get().getPontuacaoBonusPresenca() + somaPresenca + somaAtividade);

		return pontuacaoPorGrupo.get().getPontuacaoFinal();

	}

	public PontuacaoPorGrupo obterPontuacaoPorGrupo(Long id) throws Exception {
		Optional<PontuacaoPorGrupo> pontuacao = pontuacaoPorGrupoRepository.findById(id);
		if(pontuacao.isEmpty())
			throw new Exception("Não Existe a Pontuacao desse Grupo no Ranking");
		else
			return pontuacao.get();
	}
	
	public PontuacaoPorGrupo salvarPontuacaoPorGrupo(PontuacaoPorGrupo pontuacaoPorGrupo) {
		return this.pontuacaoPorGrupoRepository.save(pontuacaoPorGrupo);
	}

}
