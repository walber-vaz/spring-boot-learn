package dev.w2k.animeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*
 * @ComponentScan(basePackages = "dev.w2k.test")
 *
 * Para fazer o scan de pacotes diferentes do pacote onde a classe principal está
 * Isso vai sobrescrever o scan padrão feito no pacote onde a classe principal está
 * Caso queira adicionar mais de um pacote, basta adicionar mais um pacote no array
 * Exemplo:
 * @ComponentScan(basePackages = {"dev.w2k.test", "dev.w2k.animeservice"})
 **/
public class AnimeServiceApplication {

  public static void main(String[] args) {
    /*
     * Esse codigo é responsavel por iniciar a aplicação Spring Boot
     * e imprimir no console todos os beans que foram inicializados
     *
     * var applicationContext = SpringApplication.run(AnimeServiceApplication.class, args);
     * Arrays.stream(applicationContext.getBeanDefinitionNames())
     *     .sorted()
     *     .forEach(System.out::println);
     **/
    SpringApplication.run(AnimeServiceApplication.class, args);
  }

}