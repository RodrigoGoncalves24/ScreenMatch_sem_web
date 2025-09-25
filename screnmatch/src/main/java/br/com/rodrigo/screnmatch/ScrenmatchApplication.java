package br.com.rodrigo.screnmatch;

import br.com.rodrigo.screnmatch.model.DadoEpisodio;
import br.com.rodrigo.screnmatch.model.DadoSerie;
import br.com.rodrigo.screnmatch.model.DadoTemporada;
import br.com.rodrigo.screnmatch.service.ConsumoApi;
import br.com.rodrigo.screnmatch.service.ConverteDado;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ScrenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScrenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner in = new Scanner(System.in);
		var consumoAPI = new ConsumoApi();
		System.out.print("Informe o nome da série: ");
		String nomeSerie = in.nextLine();
		nomeSerie = nomeSerie.toLowerCase().replaceAll(" ", "&");
		var json = consumoAPI.obterDados("https://www.omdbapi.com/?t="+nomeSerie+"s&apikey=8f7a2039");

		System.out.println(json);

		//Obter dados de uma série
		ConverteDado conversor = new ConverteDado();
		DadoSerie dados = conversor.obterDados(json, DadoSerie.class);
		System.out.println(dados);

		//Obtendo os dados do novo endereço para séries
		var jsonEpisodio = consumoAPI.obterDados("https://www.omdbapi.com/?t="+nomeSerie+"&season=1&episode=2&apikey=8f7a2039");
		DadoEpisodio dadoEpisodio = conversor.obterDados(jsonEpisodio, DadoEpisodio.class);
		System.out.println(dadoEpisodio);

		//Obtendo dados da temporada
		DadoTemporada dadoTemporada;
		List<DadoTemporada> temporadas = new ArrayList<>();
		for (int i = 1; i <= dados.totalTemporadas() ; i++) {
			var jsonTemporadas = consumoAPI.obterDados("https://www.omdbapi.com/?t="+nomeSerie+"&season="+i+"&apikey=8f7a2039");
			dadoTemporada = conversor.obterDados(jsonTemporadas, DadoTemporada.class);
			temporadas.add(dadoTemporada);
		}

		temporadas.forEach(System.out::println);


	}
}
