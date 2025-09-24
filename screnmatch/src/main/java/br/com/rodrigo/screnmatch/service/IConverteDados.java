package br.com.rodrigo.screnmatch.service;

public interface IConverteDados {
    //tipo genérico de dados, a classe serve para especificar o tipo de retorno
    <T> T obterDados(String json, Class<T> classe);
}
