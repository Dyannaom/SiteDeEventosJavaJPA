package br.com.gft.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.entities.ParticipanteEvento;
import br.com.gft.entities.StatusPresenca;
import br.com.gft.repositories.ParticipanteEventoRepository;
import br.com.gft.repositories.StatusPresencaRepository;

@Service
public class StatusPresencaService {

	@Autowired
	ParticipanteEventoRepository participanteEventoRepository;
	@Autowired
	StatusPresencaRepository statusPresencaRepository;

	public void verificarPresenca(Long id) {

		Optional<ParticipanteEvento> participante = participanteEventoRepository.findById(id);
		Optional<StatusPresenca> status = statusPresencaRepository.findById(id);

		if (!participanteEventoRepository.findById(id).isEmpty()) {

			if (status.get().isPresente()) {
				participante.get().setPontuacaoPresenca(participante.get().getPontuacaoPresenca() + 10);
			}
			if (status.get().isAtrasado()) {
				participante.get().setPontuacaoPresenca(participante.get().getPontuacaoPresenca() + 8);
			}
			if (status.get().isAusente()) {
				participante.get().setPontuacaoPresenca(participante.get().getPontuacaoPresenca());
			}

		}

	}

}
