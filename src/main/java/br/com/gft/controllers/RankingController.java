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
import br.com.gft.entities.Evento;
import br.com.gft.entities.PontuacaoPorGrupo;
import br.com.gft.entities.StatusAtividade;
import br.com.gft.entities.StatusPresenca;
import br.com.gft.services.EventoService;
import br.com.gft.services.PontuacaoPorGrupoService;
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

	@RequestMapping
	private ModelAndView mostrarRanking(@RequestParam Long id) {
		ModelAndView mv;

		try {
			mv = new ModelAndView("area-acesso-adm/evento/ranking/mostrar-ranking.html");
			Evento evento = eventoService.obterEvento(id);
			mv.addObject("evento", evento);
			mv.addObject("ranking", evento.getRanking());
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
	public ModelAndView verificarPresenca(@RequestParam Long idStatusPresenca) {
		ModelAndView mv = new ModelAndView("area-acesso-adm/evento/ranking/pagina-de-marcacao-de-presenca.html");
		try {
			StatusPresenca statusPresenca = statusPresencaService.obterStatusPresenca(idStatusPresenca);
			List<StatusPresenca> listaDeStatusPresenca 
				= statusPresencaService.listarStatusPresencaPorPontuacaoPorGrupoEPorDiaDeEvento(statusPresenca.getPontuacaoPorGrupo(), statusPresenca.getDiaDeEvento());
			if(listaDeStatusPresenca.isEmpty()) {
				mv.addObject("mensagem", "Lista de Presenca vazia!");
				mv.addObject("cor", "danger");
			}
			mv.addObject("lista", listaDeStatusPresenca);
			mv.addObject("statusPresenca", statusPresenca);
			mv.addObject("pontuacaoPorGrupo", statusPresenca.getPontuacaoPorGrupo());
		} catch (Exception e) {
			mv.addObject("mensagem", e.getMessage());
			mv.addObject("cor", "danger");
		}
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "statusPresenca/salvar")
	public ModelAndView salvarStatusPresenca(PontuacaoPorGrupo pontuacaoPorGrupo, RedirectAttributes ra){
		ModelAndView mv = new ModelAndView("area-acesso-adm/evento/ranking/pagina-de-marcacao-de-presenca.html");
		
		for(StatusPresenca statusPresenca: pontuacaoPorGrupo.getListaStatusPresenca()) {
			statusPresencaService.salvarStatusPresenca(statusPresenca);
		}
		return mv;
	}
	

	
	@RequestMapping(method = RequestMethod.GET, path = "listarAtividadesDoDia")
	public ModelAndView verificarAtividade(@RequestParam Long idStatusPresenca) {
		ModelAndView mv = new ModelAndView("area-acesso-adm/evento/ranking/listar-atividades-do-dia.html");
		try {
			StatusPresenca statusPresenca = statusPresencaService.obterStatusPresenca(idStatusPresenca);
			mv.addObject("statusPresenca", statusPresenca);
		} catch (Exception e) {
			mv.addObject("mensagem", e.getMessage());
			mv.addObject("cor", "danger");
		}
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "atualizarPontuacaoAtividades")
	public ModelAndView atualizarAtividadeDoDia(@RequestParam Long idStatusPresenca) {
		ModelAndView mv = new ModelAndView("area-acesso-adm/evento/ranking/pagina-de-marcacao-de-atividades.html");
		try {
			StatusPresenca statusPresenca = statusPresencaService.obterStatusPresenca(idStatusPresenca);
			Atividade atividade = statusPresenca.getListaStatusAtividade().get(0).getAtividade();
			List<StatusAtividade> listaStatusAtividade = statusAtividadeService.obterStatusAtividadePorAtividade(atividade);
			if(!listaStatusAtividade.isEmpty()) {
				mv.addObject("statusPresenca", statusPresenca);
				mv.addObject("listaStatusAtividade", listaStatusAtividade);
				mv.addObject("atividadeNome", listaStatusAtividade.get(0).getAtividade().getNome());
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
	
	@RequestMapping(method = RequestMethod.POST, path="statusAtividade/salvar")
	public ModelAndView salvarStatusAtividade(StatusPresenca statusPresenca) {
		
		ModelAndView mv = new ModelAndView("area-acesso-adm/evento/ranking/pagina-de-marcacao-de-atividades.html");
		
		for(StatusAtividade statusAtividade: statusPresenca.getListaStatusAtividade()){
			statusAtividadeService.salvarStatusAtividade(statusAtividade);
		}
		return mv;
	}
	
	
	
	
	

}
