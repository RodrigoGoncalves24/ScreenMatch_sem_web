package br.com.rodrigo.screnmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

//Mapeando os dados para nossa aplicação, assim podemos utilizar esses dados dentro do programa
@JsonIgnoreProperties(ignoreUnknown = true) //Ignorar os dados que não precisamos na aplicação
public record DadoTemporada(@JsonAlias("Season") Integer numeroTemporada,
                            @JsonAlias("Episodes") List<DadoEpisodio> episodios) {
}
