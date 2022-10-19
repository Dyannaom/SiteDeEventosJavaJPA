package br.com.gft.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.gft.entities.Usuario;
import br.com.gft.services.UsuarioService;

@Controller
@RequestMapping("usuario")
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@RequestMapping
	public ModelAndView loginUsuario() {
		ModelAndView mv = new ModelAndView("usuarios/form/login.html");
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, path="entrar")
	public ModelAndView entrarComoAdiministrador(Usuario usuario, RedirectAttributes ra) {
		ModelAndView mv;
		usuarioService.salvarUsuario(usuario);
		mv = new ModelAndView("redirect:/usuario/get?&id=" + usuario.getId());
		ra.addFlashAttribute("mensagem", "Usuario salvo!");
		ra.addFlashAttribute("usuario", "usuario");
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.GET, path="cadastrar")
	public ModelAndView cadastrarUsuario() {
		ModelAndView mv = new ModelAndView("usuarios/form/cadastro.html");
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.GET, path="editar")
	public ModelAndView editarUsuario(@RequestParam(required=false) Long id) {
		ModelAndView mv = new ModelAndView("usuarios/form/cadastro.html");
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, path="salvar")
	public ModelAndView salvarUsuarios(Usuario usuario, RedirectAttributes ra) {
		ModelAndView mv;
		usuarioService.salvarUsuario(usuario);
		mv = new ModelAndView("redirect:/usuario/get?&id=" + usuario.getId());
		ra.addFlashAttribute("mensagem", "Usuario salvo!");
		ra.addFlashAttribute("usuario", "usuario");
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.GET, path="deletar")
	public ModelAndView deletarUsuario(@RequestParam Long id) {
		ModelAndView mv = new ModelAndView();
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.GET, path="get")
	public ModelAndView pegarUsuario(@RequestParam Long id) {
		ModelAndView mv = new ModelAndView("usuarios/perfil.html");
		try {
			mv.addObject("usuario", usuarioService.obterUsuario(id));
		} catch (Exception e) {
			mv.addObject("mensagem", e.getMessage());
		}
		return mv;
	}

}
