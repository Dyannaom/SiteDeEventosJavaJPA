package br.com.gft.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Evento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataInicio;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataFinal;
	@OneToMany(mappedBy = "evento")
	private List<Grupo> listaDeGruposDoEvento;
	@OneToMany(mappedBy = "evento")
	private List<DiaDeEvento> listaDeDias;
	private boolean incluiFinalDeSemana;
	
	@OneToOne
	private Ranking ranking;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<Grupo> getListaDeGruposDoEvento() {
		return listaDeGruposDoEvento;
	}

	public void setListaDeGruposDoEvento(List<Grupo> listaDeGruposDoEvento) {
		this.listaDeGruposDoEvento = listaDeGruposDoEvento;
	}

	public List<DiaDeEvento> getListaDeDias() {
		return listaDeDias;
	}

	public void setListaDeDias(List<DiaDeEvento> listaDeDias) {
		this.listaDeDias = listaDeDias;
	}

	public boolean isIncluiFinalDeSemana() {
		return incluiFinalDeSemana;
	}

	public void setIncluiFinalDeSemana(boolean incluiFinalDeSemana) {
		this.incluiFinalDeSemana = incluiFinalDeSemana;
	}

	public Ranking getRanking() {
		return ranking;
	}

	public void setRanking(Ranking ranking) {
		this.ranking = ranking;
	} 

	
	

}
