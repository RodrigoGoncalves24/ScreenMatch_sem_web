package br.com.rodrigo.screnmatch;

import br.com.rodrigo.screnmatch.model.DadoSerie;
import br.com.rodrigo.screnmatch.service.ConsumoApi;
import br.com.rodrigo.screnmatch.service.ConverteDado;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScrenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScrenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoAPI = new ConsumoApi();
		var json = consumoAPI.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=8f7a2039");
		//System.out.println(json);
		//json = consumoAPI.obterDados("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);
		ConverteDado conversor = new ConverteDado();
		DadoSerie dados = conversor.obterDados(json, DadoSerie.class);
		System.out.println(dados);

	}
}
