package br.com.rodrigo.screnmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias; //Serve apenas para ler o Json
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// import com.fasterxml.jackson.annotation.JsonProperty; Gera um Json com o nome da propriedade JsonProperty("nomeAtributo")

//Alias utilizado para que os nomes "Title" e "titulo" sejam associados
@JsonIgnoreProperties(ignoreUnknown = true) //Ignorar os demais atributos não utilizados
public record DadoSerie(@JsonAlias("Title") String titulo,
                        @JsonAlias("totalSeasons") Integer totalTemporadas,
                        @JsonAlias("imdbRating")  String avaliacao) {
}
