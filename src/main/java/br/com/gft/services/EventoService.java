package br.com.gft.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.entities.DiaDeEvento;
import br.com.gft.entities.Evento;
import br.com.gft.entities.Ranking;
import br.com.gft.repositories.DiaDeEventoRepository;
import br.com.gft.repositories.EventoRepository;
import br.com.gft.repositories.RankingRepository;

@Service
public class EventoService {

	@Autowired
	private EventoRepository eventoRepository;
	
	@Autowired
	private DiaDeEventoRepository diaDeEventoRepository;
	
	@Autowired
	private RankingRepository rankingRepository;
	

	public Evento salvarEvento(Evento evento) {
		Ranking ranking = new Ranking();
		ranking = rankingRepository.save(ranking);
		evento.setRanking(ranking);
		return eventoRepository.save(evento);
	}

	
	
	public List<Evento> listarEvento() {
		return eventoRepository.findAll();
	}
	
	

	public Evento obterEvento(Long id) throws Exception {
		Optional<Evento> evento = eventoRepository.findById(id);

		if (evento.isEmpty()) {
			throw new Exception("Evento não encontrado.");
		}
		return evento.get();
	}

	
	
	public void excluirEvento(Long id) {
		eventoRepository.deleteById(id);
	}
	
	public void criarListaDeDiasDeEvento(Evento evento) {
		
		LocalDate dataInicial = evento.getDataInicio();
		LocalDate dataFinal = evento.getDataFinal();
		LocalDate data = dataInicial.plusDays(-1);;
		
		
		do {
			data = data.plusDays(1);
			
			if(eFimDeSemana(data)) {
				if(evento.isIncluiFinalDeSemana()) {
					criarESalvarDiaDeEvento(data, evento);
				}
			} else {
				criarESalvarDiaDeEvento(data, evento);
			}
		} while(!data.equals(dataFinal));
		
	}
	
	private static boolean eFimDeSemana(LocalDate data) {
	    DayOfWeek d = data.getDayOfWeek();
	    return d == DayOfWeek.SATURDAY || d == DayOfWeek.SUNDAY;
	}
	
	private void criarESalvarDiaDeEvento(LocalDate data, Evento evento){	
		diaDeEventoRepository.save(new DiaDeEvento(data, evento));
	}



	public boolean primeiraDataEAntesDaSegundaData(LocalDate primeiraData, LocalDate segundaData) {
		if(primeiraData.isBefore(segundaData))
			return true;
		else
			return false;
	}
	
	
}
