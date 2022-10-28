package br.com.gft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.gft.entities.Evento;
import br.com.gft.entities.PontuacaoPorGrupo;
import br.com.gft.services.EventoService;
import br.com.gft.services.PontuacaoPorGrupoService;

@Controller
@RequestMapping("evento/ranking")
public class RankingController {
	
	@Autowired
	EventoService eventoService;
	@Autowired
	PontuacaoPorGrupoService pontuacaoPorGrupoService;
	
	@RequestMapping
	private ModelAndView mostrarRanking(@RequestParam Long id){
		ModelAndView mv;
		
		try {
			mv = new ModelAndView("area-acesso-adm/evento/ranking/mostrar-ranking.html");
			Evento evento = eventoService.obterEvento(id);
			mv.addObject("evento", evento);
			mv.addObject("ranking", evento.getRanking());
		}catch (Exception e) {
			mv = new ModelAndView("/evento");
			mv.addObject("mensagem", e.getMessage());
		}
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.GET, path="atualizarPontuacaoGrupo")
	private ModelAndView atualizarPontuacaoDoGrupo(@RequestParam Long idPontuacaoPorGrupo){
		ModelAndView mv;
		
		try {
			mv = new ModelAndView("area-acesso-adm/evento/ranking/listar-marcacoes-de-pontucao.html");
			PontuacaoPorGrupo pontuacaoPorGrupo = pontuacaoPorGrupoService.obterPontuacaoPorGrupo(idPontuacaoPorGrupo);
			mv.addObject("pontuacaoPorGrupo", pontuacaoPorGrupo);
			
		}catch (Exception e) {
			mv = new ModelAndView("/evento");
			mv.addObject("mensagem", e.getMessage());
		}
		
		return mv;
	}
}
