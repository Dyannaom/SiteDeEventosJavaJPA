package br.com.gft.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Atividade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty (message = " Não pode ser em branco ")
	private String nome;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataInicio;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataFinal;
	
	
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
}
