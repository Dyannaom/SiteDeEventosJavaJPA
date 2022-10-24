package br.com.gft.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.entities.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long>{
	List<Grupo> findByNomeContainsAndQuantidadeDePessoasContains(String nome, Integer quantidadeDePessoas);
}
