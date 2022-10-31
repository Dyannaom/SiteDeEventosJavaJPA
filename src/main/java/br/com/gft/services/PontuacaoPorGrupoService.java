package br.com.gft.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.entities.Evento;
import br.com.gft.entities.Grupo;
import br.com.gft.entities.ParticipanteEvento;
import br.com.gft.entities.PontuacaoPorGrupo;
import br.com.gft.entities.StatusAtividade;
import br.com.gft.entities.StatusPresenca;
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
	@Autowired
	StatusPresencaService statusPresencaService;
	@Autowired
	StatusAtividadeService statusAtividadeService;

	private void conferirBonusPresenca(Long idPontuacaoGrupo) throws Exception{

		PontuacaoPorGrupo pontuacaoPorGrupo = obterPontuacaoPorGrupo(idPontuacaoGrupo);
		Grupo grupo = pontuacaoPorGrupo.getGrupo();
		Evento evento= pontuacaoPorGrupo.getRanking().getEvento();

		int soma = 0;
		int bonusAuxiliar = evento.getListaDeDias().size()
				* grupo.getListaDeParticipantesDoGrupo().size() * 10;

		for (ParticipanteEvento participanteEvento1 : grupo.getListaDeParticipantesDoGrupo()) {
			soma += participanteEvento1.getPontuacaoPresenca();
		}

		if (bonusAuxiliar == evento.getListaDeDias().size() * soma) {
			pontuacaoPorGrupo.setPontuacaoBonusPresenca(5);
		}else {
			pontuacaoPorGrupo.setPontuacaoBonusPresenca(0);
		}

		salvarPontuacaoPorGrupo(pontuacaoPorGrupo);
	}

	private void conferirBonusAtividade(Long idPontuacaoGrupo) throws Exception{

		PontuacaoPorGrupo pontuacaoPorGrupo = obterPontuacaoPorGrupo(idPontuacaoGrupo);
		Grupo grupo = pontuacaoPorGrupo.getGrupo();
		Evento evento= pontuacaoPorGrupo.getRanking().getEvento();

		int soma = 0;
		int bonusAuxiliar = evento.getListaDeDias().size()
				* grupo.getListaDeParticipantesDoGrupo().size() * (5 * 2);

		for (ParticipanteEvento participanteEvento1 : grupo.getListaDeParticipantesDoGrupo()) {
			soma += participanteEvento1.getPontuacaoAtividadeDoEvento();
		}
		if (bonusAuxiliar == evento.getListaDeDias().size() * soma) {
			pontuacaoPorGrupo.setPontuacaoBonusAtividade(3);
		}else {
			pontuacaoPorGrupo.setPontuacaoBonusAtividade(0);
		}
		salvarPontuacaoPorGrupo(pontuacaoPorGrupo);

	}

	private void atualizarPontuacaoPresencaParticipante(Long idPontuacaoPorGrupo) throws Exception{
		PontuacaoPorGrupo pontuacaoPorGrupo = obterPontuacaoPorGrupo(idPontuacaoPorGrupo);
		List<ParticipanteEvento> listaDeParticipantes = pontuacaoPorGrupo.getGrupo().getListaDeParticipantesDoGrupo();
		
		for(ParticipanteEvento participante :listaDeParticipantes) {
			for (StatusPresenca statusPresenca : participante.getListaStatusPresenca()) {
				participante.setPontuacaoPresenca(0);

				if(statusPresenca.isPresente()) {
					participante.setPontuacaoPresenca(participante.getPontuacaoPresenca()+10);
				}
				if(statusPresenca.isAtrasado()) {
					participante.setPontuacaoPresenca(participante.getPontuacaoPresenca()-2);
				}
				
				participanteEventoRepository.save(participante);	
			}
		}
		
		
	}
	
	private void atualizarPontuacaoEntregaDeAtividadeParticipante(Long idPontuacaoPorGrupo) throws Exception{
		
		PontuacaoPorGrupo pontuacaoPorGrupo = obterPontuacaoPorGrupo(idPontuacaoPorGrupo);
		List<ParticipanteEvento> listaDeParticipantes = pontuacaoPorGrupo.getGrupo().getListaDeParticipantesDoGrupo();
		
		for(ParticipanteEvento participante :listaDeParticipantes) {
			for (StatusPresenca statusPresenca : participante.getListaStatusPresenca()) {
				participante.setPontuacaoAtividadeDoEvento(0);
				for (StatusAtividade statusAtividade : statusPresenca.getListaStatusAtividade()) {
					if(statusAtividade.isEntregue()) {
						participante.setPontuacaoAtividadeDoEvento(participante.getPontuacaoAtividadeDoEvento()+5);
					}
					if(statusAtividade.isEntregueAtrasado()) {
						participante.setPontuacaoAtividadeDoEvento(participante.getPontuacaoAtividadeDoEvento()-2);
					}
					
					participanteEventoRepository.save(participante);
				}
			}
		}
		
		
	}

	public void atualizarPontuacaoFinal(Long idPontuacaoPorGrupo) throws Exception{
		
		PontuacaoPorGrupo pontuacaoPorGrupo = obterPontuacaoPorGrupo(idPontuacaoPorGrupo);
		Grupo grupo = pontuacaoPorGrupo.getGrupo();
		
		atualizarPontuacaoPresencaParticipante(pontuacaoPorGrupo.getId());
		atualizarPontuacaoEntregaDeAtividadeParticipante(pontuacaoPorGrupo.getId());
		conferirBonusPresenca(pontuacaoPorGrupo.getId());
		conferirBonusAtividade(pontuacaoPorGrupo.getId());

		int somaPresenca = 0;
		int somaAtividade = 0;

		for (ParticipanteEvento participanteEvento1 : grupo.getListaDeParticipantesDoGrupo()) {
			somaPresenca += participanteEvento1.getPontuacaoPresenca();
		}
		for (ParticipanteEvento participanteEvento1 : grupo.getListaDeParticipantesDoGrupo()) {
			somaAtividade += participanteEvento1.getPontuacaoAtividadeDoEvento();
		}

		pontuacaoPorGrupo.setPontuacaoFinal(pontuacaoPorGrupo.getPontuacaoBonusAtividade()
				+ pontuacaoPorGrupo.getPontuacaoBonusPresenca() + somaPresenca + somaAtividade);
		salvarPontuacaoPorGrupo(pontuacaoPorGrupo);

	}

	public PontuacaoPorGrupo obterPontuacaoPorGrupo(Long id) throws Exception {
		Optional<PontuacaoPorGrupo> pontuacao = pontuacaoPorGrupoRepository.findById(id);
		if (pontuacao.isEmpty())
			throw new Exception("Não Existe a Pontuacao desse Grupo no Ranking");
		else
			return pontuacao.get();
	}

	public PontuacaoPorGrupo salvarPontuacaoPorGrupo(PontuacaoPorGrupo pontuacaoPorGrupo) {
		return this.pontuacaoPorGrupoRepository.save(pontuacaoPorGrupo);
	}

}
