package br.com.gft.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.gft.entities.Atividade;
import br.com.gft.entities.DiaDeEvento;
import br.com.gft.entities.Evento;
import br.com.gft.entities.PontuacaoPorGrupo;
import br.com.gft.entities.StatusAtividade;
import br.com.gft.entities.StatusPresenca;
import br.com.gft.services.DiaDeEventoService;
import br.com.gft.services.EventoService;
import br.com.gft.services.PontuacaoPorGrupoService;
import br.com.gft.services.RankingService;
import br.com.gft.services.StatusAtividadeService;
import br.com.gft.services.StatusPresencaService;

@Controller
@RequestMapping("evento/ranking")
public class RankingController {

	@Autowired
	EventoService eventoService;
	@Autowired
	PontuacaoPorGrupoService pontuacaoPorGrupoService;
	@Autowired
	StatusAtividadeService statusAtividadeService;
	@Autowired
	StatusPresencaService statusPresencaService;
	@Autowired
	DiaDeEventoService diaDeEventoService;
	@Autowired
	RankingService rankingService;

	@RequestMapping
	private ModelAndView mostrarRanking(@RequestParam Long id) {
		ModelAndView mv;

		try {
			mv = new ModelAndView("area-acesso-adm/evento/ranking/mostrar-ranking.html");
			Evento evento = eventoService.obterEvento(id);
			mv.addObject("evento", evento);
			mv.addObject("lista", rankingService.ordemDaPontuacaoPorGrupo(evento.getRanking()));
		} catch (Exception e) {
			mv = new ModelAndView("/evento");
			mv.addObject("mensagem", e.getMessage());
		}

		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, path = "atualizarPontuacaoGrupo")
	private ModelAndView atualizarPontuacaoDoGrupo(@RequestParam Long idPontuacaoPorGrupo) {
		ModelAndView mv;

		try {
			mv = new ModelAndView("area-acesso-adm/evento/ranking/listar-marcacoes-de-pontucao.html");
			PontuacaoPorGrupo pontuacaoPorGrupo = pontuacaoPorGrupoService.obterPontuacaoPorGrupo(idPontuacaoPorGrupo);
			mv.addObject("pontuacaoPorGrupo", pontuacaoPorGrupo);

		} catch (Exception e) {
			mv = new ModelAndView("/evento");
			mv.addObject("mensagem", e.getMessage());
		}

		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, path = "atualizarPontuacaoPresenca")
	public ModelAndView verificarPresenca(@RequestParam Long idDiaDeEvento, Long idPontuacaoPorGrupo) {
		ModelAndView mv = new ModelAndView("area-acesso-adm/evento/ranking/pagina-de-marcacao-de-presenca.html");

		try {
			PontuacaoPorGrupo pontuacaoPorGrupo = pontuacaoPorGrupoService.obterPontuacaoPorGrupo(idPontuacaoPorGrupo);
			DiaDeEvento diaDeEvento = diaDeEventoService.obterDiaDeEvento(idDiaDeEvento);
			List<StatusPresenca> listaDeStatusPresenca = statusPresencaService
					.listarStatusPresencaPorPontuacaoPorGrupoEPorDiaDeEvento(pontuacaoPorGrupo, diaDeEvento);
			pontuacaoPorGrupo.setListaStatusPresenca(listaDeStatusPresenca);
			if (listaDeStatusPresenca.isEmpty()) {
				mv.addObject("mensagem", "Lista de Presenca vazia!");
				mv.addObject("cor", "danger");
			}
			mv.addObject("lista", listaDeStatusPresenca);
			mv.addObject("diaDeEvento", diaDeEvento);
			mv.addObject("pontuacaoPorGrupo", pontuacaoPorGrupo);
		} catch (Exception e) {
			mv.addObject("mensagem", e.getMessage());
			mv.addObject("cor", "danger");
		}

		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, path = "statusPresenca/salvar")
	public ModelAndView salvarStatusPresenca(PontuacaoPorGrupo pontuacaoPorGrupo, RedirectAttributes ra) {

		ModelAndView mv;

		List<StatusPresenca> listaStatusPresenca = pontuacaoPorGrupo.getListaStatusPresenca();

		DiaDeEvento diaDeEvento = statusPresencaService.pegarDiaDeEventoDeStatusPresenca(listaStatusPresenca);

		if (statusPresencaService.verificarSeAlgumStatusPresencaEstaPresenteEAusente(listaStatusPresenca)) {

			mv = new ModelAndView("redirect:/evento/ranking/atualizarPontuacaoPresenca?" + "idDiaDeEvento="
					+ diaDeEvento.getId() + "&" + "idPontuacaoPorGrupo="
					+ pontuacaoPorGrupo.getId());
			ra.addFlashAttribute("mensagem", "Não é possivel o participante estar ausente e presente!");
			ra.addFlashAttribute("cor", "danger");

		} else {
			boolean atrasado = false;
			
			mv = new ModelAndView("redirect:/evento/ranking/atualizarPontuacaoGrupo?&" 
					+ "idPontuacaoPorGrupo=" + pontuacaoPorGrupo.getId());
					ra.addFlashAttribute("mensagem", "Atualização feita com sucesso!");
					ra.addFlashAttribute("cor", "success");

			for (StatusPresenca statusPresenca : listaStatusPresenca) {
				atrasado = statusPresenca.isAtrasado();

				if (atrasado) {
					statusPresenca.setPresente(true);
					statusPresenca.isPresente();
				}

				if (statusPresenca.getParticipanteEvento().getId() != null) {
					statusPresencaService.salvarStatusPresenca(statusPresenca);

				}
			}
			
			try{
				pontuacaoPorGrupoService.atualizarPontuacaoFinal(pontuacaoPorGrupo.getId());
			} catch(Exception e) {
				ra.addFlashAttribute("mensagem", "Não foi possível atualizar pontuação final!");
				ra.addFlashAttribute("cor", "danger");
			}

		}

		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, path = "listarAtividadesDoDia")
	public ModelAndView verificarAtividade(@RequestParam Long idDiaDeEvento, Long idPontuacaoPorGrupo) {
		ModelAndView mv = new ModelAndView("area-acesso-adm/evento/ranking/listar-atividades-do-dia.html");
		try {
			PontuacaoPorGrupo pontuacaoPorGrupo = pontuacaoPorGrupoService.obterPontuacaoPorGrupo(idPontuacaoPorGrupo);
			DiaDeEvento diaDeEvento = diaDeEventoService.obterDiaDeEvento(idDiaDeEvento);
			StatusPresenca statusPresenca = statusPresencaService
					.listarStatusPresencaPorPontuacaoPorGrupoEPorDiaDeEvento(pontuacaoPorGrupo, diaDeEvento).get(0);
			mv.addObject("statusPresenca", statusPresenca);
			mv.addObject("pontuacaoPorGrupo", pontuacaoPorGrupo);
		} catch (Exception e) {
			mv.addObject("mensagem", e.getMessage());
			mv.addObject("cor", "danger");
		}
		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, path = "atualizarPontuacaoAtividades")
	public ModelAndView atualizarAtividadeDoDia(@RequestParam Long idDiaDeEvento, Long idStatusAtividade,
			Long idPontuacaoPorGrupo) {
		ModelAndView mv = new ModelAndView("area-acesso-adm/evento/ranking/pagina-de-marcacao-de-atividades.html");
		try {
			StatusAtividade statusAtividade = statusAtividadeService.obterStatusAtividade(idStatusAtividade);
			DiaDeEvento diaDeEvento = diaDeEventoService.obterDiaDeEvento(idDiaDeEvento);
			Atividade atividade = statusAtividade.getAtividade();
			PontuacaoPorGrupo pontuacaoPorGrupo = pontuacaoPorGrupoService.obterPontuacaoPorGrupo(idPontuacaoPorGrupo);

			List<StatusAtividade> listaStatusAtividade = statusAtividadeService
					.listarStatusAtividadePorDiaDeEventoEPorAtividadeEPorPontuacaoPorGrupo(diaDeEvento, atividade,
							pontuacaoPorGrupo);
			List<StatusPresenca> listaStatusPresenca = statusPresencaService
					.obterListaStatusPresencaPorListaStatusAtividade(listaStatusAtividade);

			pontuacaoPorGrupo.setListaStatusPresenca(listaStatusPresenca);

			if (!listaStatusPresenca.isEmpty()) {
				mv.addObject("pontuacaoPorGrupo", pontuacaoPorGrupo);
				mv.addObject("listaSA", listaStatusAtividade);
				mv.addObject("listaSP", pontuacaoPorGrupo.getListaStatusPresenca());
				mv.addObject("atividade", atividade);
			} else {
				mv.addObject("mensagem", "Nenhuma participante para atualizar atividade");
				mv.addObject("cor", "danger");
			}
		} catch (Exception e) {
			mv.addObject("mensagem", e.getMessage());
			mv.addObject("cor", "danger");
		}
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, path = "statusAtividade/salvar")
	public ModelAndView salvarStatusAtividade(PontuacaoPorGrupo pontuacaoPorGrupo, RedirectAttributes ra) {
		ModelAndView mv = new ModelAndView("area-acesso-adm/evento/ranking/pagina-de-marcacao-de-atividades.html");
		
		List<StatusPresenca> listaStatusPresenca = pontuacaoPorGrupo.getListaStatusPresenca();
		
		DiaDeEvento diaDeEvento = statusPresencaService.pegarDiaDeEventoDeStatusPresenca(listaStatusPresenca);
		Long idStatusAtividade = statusAtividadeService.pegarStatusAtividadeDeUmaLista(listaStatusPresenca).getId();
		
		if(statusAtividadeService.verificarSeAlgumStatusAtividadeEstaEntregueENaoEntregue(listaStatusPresenca)) {
			
			mv = new ModelAndView("redirect:/evento/ranking/atualizarPontuacaoAtividades?" 
					+ "idDiaDeEvento=" + diaDeEvento.getId() + "&" 
					+ "idStatusAtividade=" +  idStatusAtividade + "&" 
					+ "idPontuacaoPorGrupo=" + pontuacaoPorGrupo.getId());
			ra.addFlashAttribute("mensagem", "Não é possivel o participante ter entregue e não entregue a atividade!");
			ra.addFlashAttribute("cor", "danger");
			
		} else {
			mv = new ModelAndView("redirect:/evento/ranking/listarAtividadesDoDia?"
					+ "idDiaDeEvento=" + diaDeEvento.getId() + "&"
					+ "idPontuacaoPorGrupo=" + pontuacaoPorGrupo.getId());
					
			boolean entregueAtrasado = false;
			
			for (StatusPresenca statusPresenca : listaStatusPresenca) {
				for (StatusAtividade statusAtividade : statusPresenca.getListaStatusAtividade()) {
					entregueAtrasado = statusAtividade.isEntregueAtrasado();
					
					if(entregueAtrasado) {
						statusAtividade.setEntregue(true);
					}
					
					if (statusAtividade.getStatusPresenca() != null) {
						statusAtividadeService.salvarStatusAtividade(statusAtividade);
					}
				}
			}
			ra.addFlashAttribute("mensagem", "Atualização feita com sucesso!");
			ra.addFlashAttribute("cor", "success");
			
			try{
				pontuacaoPorGrupoService.atualizarPontuacaoFinal(pontuacaoPorGrupo.getId());
			} catch(Exception e) {
				ra.addFlashAttribute("mensagem", "Não foi possível atualizar pontuação final!");
				ra.addFlashAttribute("cor", "danger");
			}
		}
		
		
		return mv;
	}

}
