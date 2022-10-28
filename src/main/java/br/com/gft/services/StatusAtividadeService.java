package br.com.gft.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.entities.ParticipanteEvento;
import br.com.gft.entities.StatusAtividade;
import br.com.gft.repositories.ParticipanteEventoRepository;
import br.com.gft.repositories.PontuacaoPorGrupoRepository;
import br.com.gft.repositories.StatusAtividadeRepository;

@Service
public class StatusAtividadeService {

	@Autowired
	ParticipanteEventoRepository participanteEventoRepository;
	@Autowired
	StatusAtividadeRepository statusAtividadeRepository;
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
	
	public StatusAtividade obterStatusAtividade(Long id) throws Exception{
		Optional<StatusAtividade> statusAtividade = statusAtividadeRepository.findById(id);
		if(statusAtividade.isEmpty())
			throw new Exception("Status Presença não encontrado");
		else
			return statusAtividade.get();
	}

}

