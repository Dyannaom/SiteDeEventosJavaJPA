package br.com.gft.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.entities.DiaDeEvento;
import br.com.gft.entities.ParticipanteEvento;
import br.com.gft.entities.PontuacaoPorGrupo;
import br.com.gft.entities.StatusAtividade;
import br.com.gft.entities.StatusPresenca;
import br.com.gft.repositories.ParticipanteEventoRepository;
import br.com.gft.repositories.StatusPresencaRepository;

@Service
public class StatusPresencaService {

	@Autowired
	ParticipanteEventoRepository participanteEventoRepository;
	@Autowired
	StatusPresencaRepository statusPresencaRepository;
	@Autowired
	StatusAtividadeService statusAtividadeService;

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

	public StatusPresenca obterStatusPresenca(Long id) throws Exception {
		Optional<StatusPresenca> statusPresenca = statusPresencaRepository.findById(id);
		if (statusPresenca.isEmpty())
			throw new Exception("Status Presença não encontrado");
		else
			return statusPresenca.get();

	}

	public List<StatusPresenca> listarStatusPresencaPorPontuacaoPorGrupoEPorDiaDeEvento(
			PontuacaoPorGrupo pontuacaoPorGrupo, DiaDeEvento diaDeEvento) {
		return statusPresencaRepository.findByPontuacaoPorGrupoAndDiaDeEvento(pontuacaoPorGrupo, diaDeEvento);
	}

	public void salvarStatusPresenca(StatusPresenca statusPresenca) {
		try {
			StatusPresenca statusPresenca2 = obterStatusPresenca(statusPresenca.getId());
			statusPresenca2.setAtrasado(statusPresenca.isAtrasado());
			statusPresenca2.setAusente(statusPresenca.isAusente());
			statusPresenca2.setPresente(statusPresenca.isPresente());
			statusPresencaRepository.save(statusPresenca2);
		} catch (Exception e) {

		}
	}
	public void saveStatusPresenca(StatusPresenca statusPresenca) {
		statusPresencaRepository.save(statusPresenca);
	}
	

	public List<StatusPresenca> obterListaStatusPresencaPorListaStatusAtividade(
			List<StatusAtividade> listarStatusAtividadeGeral) {

		List<StatusAtividade> listaStatusAtividade = null;
		List<StatusPresenca> listaStatusPresenca = new ArrayList<>();
		for (StatusAtividade statusAtividade : listarStatusAtividadeGeral) {
			listaStatusAtividade = new ArrayList<>();
			listaStatusAtividade.add(statusAtividade);
			StatusPresenca statusPresenca = statusAtividade.getStatusPresenca();
			statusPresenca.setListaStatusAtividade(listaStatusAtividade);
			listaStatusPresenca.add(statusPresenca);
		}

		return listaStatusPresenca;
	}

	public boolean verificarSeAlgumStatusPresencaEstaPresenteEAusente(List<StatusPresenca> listaStausPresenca) {
		boolean presente = false;
		boolean atrasado = false;
		boolean ausente = false;
		
		boolean resultado = false;

		for (StatusPresenca statusPresenca : listaStausPresenca) {
			presente = statusPresenca.isPresente();
			atrasado = statusPresenca.isAtrasado();
			ausente = statusPresenca.isAusente();

			if (atrasado) {
				statusPresenca.setPresente(true);
				presente = statusPresenca.isPresente();
			}

			if (presente && ausente) {
				resultado = true;
			} else {
				if(!resultado)
					resultado = false;
			}
		}
		
		return resultado;
	}
	
	public DiaDeEvento pegarDiaDeEventoDeStatusPresenca(List<StatusPresenca> listaStausPresenca) {
		DiaDeEvento diaDeEvento = null;
		for(StatusPresenca statusPresenca: listaStausPresenca) {
			diaDeEvento = statusPresenca.getDiaDeEvento();
		}
		return diaDeEvento;
	}
}
