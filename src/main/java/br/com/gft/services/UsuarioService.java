package br.com.gft.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.entities.Usuario;
import br.com.gft.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario salvarUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	public List<Usuario> listarUsuarios() {
		return usuarioRepository.findAll();
	}

	public Usuario obterUsuario(Long id) throws Exception {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isEmpty())
			throw new Exception("Usuario não encontrado!");
		else
			return usuario.get();
	}

	public void deletarUsuario(Long id) throws Exception {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isEmpty())
			throw new Exception("Usuario não encontrado!");
		else
			usuarioRepository.delete(usuario.get());
	}

	public Usuario obterUsuarioPelasQuatroLetras(String quatroLetras) throws Exception {
		Optional<Usuario> usuario = usuarioRepository.findByQuatroLetras(quatroLetras);
		if (usuario.isEmpty())
			throw new Exception("Usuario não encontrado!");
		else
			return usuario.get();
	}

	public Optional<Usuario> verificarSeUsuarioComEssasQuatroLetrasJaExiste(String quatroLetras){
		return usuarioRepository.findByQuatroLetras(quatroLetras);
	}

	public Optional<Usuario> verificarSeUsuarioComEsseEmailJaExiste(String email){
		return usuarioRepository.findByEmail(email);

	}

	public void desativarUsuario(Long id) throws Exception {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isEmpty())
			throw new Exception("Usuario não encontrado!");
		else
			usuario.get().setStatus(false);
	}
	
	public void ativarUsuario(Long id) throws Exception{
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isEmpty())
			throw new Exception("Usuario não encontrado!");
		else
			usuario.get().setStatus(true);
	}

}
