package br.com.rodrigo.screnmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisidio;
    private Double avalicao;
    private LocalDate dataLancamento;

    public Episodio(Integer numeroTemporada, DadoEpisodio dadosEpisodio) {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodio.titulo();
        this.numeroEpisidio = dadosEpisodio.numeroEpisodio();

        try{
            this.avalicao = Double.valueOf(dadosEpisodio.avaliacao());
        } catch (NumberFormatException e){ //Se não houver avaliação, deixa como zero
            this.avalicao = 0.0;
        }

        try{
            this.dataLancamento = LocalDate.parse(dadosEpisodio.dataLancamento());
        }catch (DateTimeParseException e){
            this.dataLancamento = null;
        }

    }

    public Integer getTemporada() {
        return temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getNumeroEpisidio() {
        return numeroEpisidio;
    }

    public Double getAvalicao() {
        return avalicao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public String toString(){
        return "Temporada: "+getTemporada()+"\nNúmero episódio: "+getNumeroEpisidio()+"\nTitulo: "+getTitulo()+"\nAvaliação: "+getAvalicao()+"\nDataLancamento: "+getDataLancamento()+"\n";
    }
}
