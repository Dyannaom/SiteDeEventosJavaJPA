package br.com.gft.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.entities.DiaDeEvento;
import br.com.gft.entities.ParticipanteEvento;
import br.com.gft.entities.PontuacaoPorGrupo;
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

	public StatusPresenca obterStatusPresenca(Long id) throws Exception{
		Optional<StatusPresenca> statusPresenca = statusPresencaRepository.findById(id);
		if(statusPresenca.isEmpty())
			throw new Exception("Status Presença não encontrado");
		else
			return statusPresenca.get();

	}
	
	public List<StatusPresenca> listarStatusPresencaPorPontuacaoPorGrupoEPorDiaDeEvento(PontuacaoPorGrupo pontuacaoPorGrupo, DiaDeEvento diaDeEvento){
		return statusPresencaRepository.findByPontuacaoPorGrupoAndDiaDeEvento(pontuacaoPorGrupo, diaDeEvento);
	}
	
	public void salvarStatusPresenca(StatusPresenca statusPresenca) {
		try {
			StatusPresenca statusPresenca2 =  obterStatusPresenca(statusPresenca.getId());
			statusPresenca2.setAtrasado(statusPresenca.isAtrasado());
			statusPresenca2.setAusente(statusPresenca.isAusente());
			statusPresenca2.setPresente(statusPresenca.isPresente());
			statusPresencaRepository.save(statusPresenca2);
		}catch(Exception e) {
			
		}
	}

}
