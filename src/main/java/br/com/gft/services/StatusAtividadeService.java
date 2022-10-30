package br.com.gft.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.entities.Atividade;
import br.com.gft.entities.DiaDeEvento;
import br.com.gft.entities.ParticipanteEvento;
import br.com.gft.entities.PontuacaoPorGrupo;
import br.com.gft.entities.StatusAtividade;
import br.com.gft.repositories.ParticipanteEventoRepository;
import br.com.gft.repositories.PontuacaoPorGrupoRepository;
import br.com.gft.repositories.StatusAtividadeRepository;
import br.com.gft.repositories.StatusPresencaRepository;

@Service
public class StatusAtividadeService {

	@Autowired
	ParticipanteEventoRepository participanteEventoRepository;
	@Autowired
	StatusAtividadeRepository statusAtividadeRepository;
	@Autowired
	StatusPresencaRepository statusPresencaRepository;
	@Autowired
	PontuacaoPorGrupoRepository pontuacaoPorGrupoRepository;

	public void verificarAtividadeEntregue(Long id) {

		Optional<StatusAtividade> status = statusAtividadeRepository.findById(id);
		Optional<ParticipanteEvento> participante = participanteEventoRepository.findById(id);

		if (!participanteEventoRepository.findById(id).isEmpty()) {

			if (status.get().isEntregue()) {
				participante.get()
						.setPontuacaoAtividadeDoEvento(participante.get().getPontuacaoAtividadeDoEvento() + 5);

			}

			if (status.get().isEntregueAtrasado()) {
				participante.get()
						.setPontuacaoAtividadeDoEvento(participante.get().getPontuacaoAtividadeDoEvento() + 3);

			}
			if (status.get().isNaoEntregue()) {
				participante.get().setPontuacaoAtividadeDoEvento(participante.get().getPontuacaoAtividadeDoEvento());

			}

		}
	}

	public StatusAtividade obterStatusAtividade(Long id) throws Exception {
		Optional<StatusAtividade> statusAtividade = statusAtividadeRepository.findById(id);
		if (statusAtividade.isEmpty())
			throw new Exception("Status Presença não encontrado");
		else
			return statusAtividade.get();
	}

	public List<StatusAtividade> 
	listarStatusAtividadePorDiaDeEventoEPorAtividadeEPorPontuacaoPorGrupo(DiaDeEvento diaDeEvento, Atividade atividade, PontuacaoPorGrupo pontuacaoPorGrupo) {
		
		List<StatusAtividade> listaStatusAtividadePorAtividade = statusAtividadeRepository.findByAtividade(atividade);
		List<StatusAtividade> listaStatusAtividadeFinal = new ArrayList<>();
		
		DiaDeEvento diaDeEventoAtual = null;
		PontuacaoPorGrupo pontuacaoPorGrupoAtual = null;
		
		for(StatusAtividade statusAtividade :listaStatusAtividadePorAtividade) {
			diaDeEventoAtual = statusAtividade.getStatusPresenca().getDiaDeEvento();
			pontuacaoPorGrupoAtual = statusAtividade.getParticipanteEvento().getGrupo().getPontuacaoPorGrupo();
			if(diaDeEventoAtual.equals(diaDeEvento) && pontuacaoPorGrupoAtual.equals(pontuacaoPorGrupo)) {
				listaStatusAtividadeFinal.add(statusAtividade);
			}
		}
		return listaStatusAtividadeFinal;
		
	}

	public void salvarStatusAtividade(StatusAtividade statusAtividade) {
		try {
			StatusAtividade statusAtividade2 = obterStatusAtividade(statusAtividade.getId());
			statusAtividade2.setEntregue(statusAtividade.isEntregue());
			statusAtividade2.setEntregueAtrasado(statusAtividade.isEntregueAtrasado());
			statusAtividade2.setNaoEntregue(statusAtividade.isNaoEntregue());
			statusAtividadeRepository.save(statusAtividade2);
		} catch (Exception e) {
		}

	}

}
