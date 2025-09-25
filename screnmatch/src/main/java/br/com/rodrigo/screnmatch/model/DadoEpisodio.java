package br.com.rodrigo.screnmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) //Ignorar os dados que não precisamos na aplicação
public record DadoEpisodio(@JsonAlias("Title")String titulo,
                           @JsonAlias("Episode") Integer numeroEpisidio,
                           @JsonAlias("imdbRating") String avalicao,
                           @JsonAlias("Released") String dataLancamento) {
}

