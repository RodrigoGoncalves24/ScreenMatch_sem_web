package br.com.rodrigo.screnmatch.principal;

import br.com.rodrigo.screnmatch.model.DadoEpisodio;
import br.com.rodrigo.screnmatch.model.DadoSerie;
import br.com.rodrigo.screnmatch.model.DadoTemporada;
import br.com.rodrigo.screnmatch.model.Episodio;
import br.com.rodrigo.screnmatch.service.ConsumoApi;
import br.com.rodrigo.screnmatch.service.ConverteDado;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner in = new Scanner(System.in);

    //final com nome maiusculo e não pode ser modificada
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=8f7a2039";

    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDado conversor = new ConverteDado();

    public void exibeMenu(){
        System.out.println("Digite o nome de uma série: ");
        var nomeSerie = in.nextLine();
        var json = consumo.obterDados( ENDERECO+nomeSerie.replace(" ", "+")+API_KEY);

        DadoSerie dados = conversor.obterDados(json, DadoSerie.class);
        System.out.println(dados);


        //Obtendo dados da temporada
		DadoTemporada dadoTemporada;
		List<DadoTemporada> temporadas = new ArrayList<>();
		for (int i = 1; i <= dados.totalTemporadas() ; i++) {
			var jsonTemporadas = consumo.obterDados(ENDERECO+nomeSerie.replace(" ", "+")+"&season="+i+API_KEY);
			dadoTemporada = conversor.obterDados(jsonTemporadas, DadoTemporada.class);
			temporadas.add(dadoTemporada);
		}

        for (int i = 0; i < dados.totalTemporadas() ; i++) {
            List<DadoEpisodio> episodioTemporada = temporadas.get(i).episodios();
            for (int j = 0; j < episodioTemporada.size(); j++) {
                System.out.println(episodioTemporada.get(j).titulo());
            }
        }

        //Para cada temporada (t), eu pego um episodio dela e, para cada episodio (e), imprime o titulo
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));    //temporadas.forEach(System.out::println); É O MESMO QUE ...forEach(t -> t.System.out.println(t));

        List<DadoEpisodio> melhoresEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()) //Tranforma em um fluxo de dados de episodios
                .collect(Collectors.toList());
               // .toList(); //Devolve uma lista imutável, não pode adicionar coisas novas


        //Top 5 melhores episodios
        System.out.println("\nTop 5 episodios");
        melhoresEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("n/a"))
                .sorted(Comparator.comparing(DadoEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numeroTemporada(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("A partir de que ano você deseja ver os episódios? ");
        int ano = in.nextInt();
        in.nextLine(); //Limpar o buffer

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //formatar a data para Brasileira

        episodios.stream().filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: "+e.getTemporada() +
                        " Episodio: "+ e.getTitulo()+
                        " Data laçamento: "+e.getDataLancamento().format(formatador)
                ));

    }
}
