# Estudos sobre springboot3

## O que é Spring Boot?

Spring Boot é um projeto da Spring que visa facilitar o processo de configuração e publicação de aplicações que utilizam o framework Spring. Ele é baseado no conceito de convenção sobre configuração, ou seja, ele define um conjunto de configurações padrão que são utilizadas na maioria dos projetos, mas que podem ser alteradas de acordo com a necessidade do desenvolvedor.

## Dicas

### 1. Por que não deve usar `@Autowired`?

O uso do `@Autowired` é uma prática comum em projetos Spring, mas ele pode causar problemas de 
manutenção e testes. Isso porque ele cria uma dependência direta entre a classe que está sendo 
injetada e a classe que está fazendo a injeção, o que pode dificultar a manutenção do código e a 
realização de testes unitários. Com isso deve usar construtor para injetar as dependências assim 
a imutabilidade da classe é garantida e facilita a realização de testes unitários.

### 2. Qual a diferença entre `@Component`, `@Service`, `@Repository` e `@Controller`?

- `@Component`: é uma anotação genérica que pode ser utilizada em qualquer classe que seja um componente Spring. Ela é utilizada para indicar que a classe é um componente Spring e deve ser gerenciada pelo container do Spring.
- `@Service`: é uma anotação que indica que a classe é um serviço Spring. Ela é utilizada para indicar que a classe é um serviço e deve ser gerenciada pelo container do Spring.
- `@Repository`: é uma anotação que indica que a classe é um repositório Spring. Ela é utilizada para indicar que a classe é um repositório e deve ser gerenciada pelo container do Spring.
- `@Controller`: é uma anotação que indica que a classe é um controlador Spring. Ela é utilizada para indicar que a classe é um controlador e deve ser gerenciada pelo container do Spring.
- `@RestController`: é uma anotação que combina as funcionalidades das anotações `@Controller` e `@ResponseBody`. Ela é utilizada para indicar que a classe é um controlador Spring que retorna objetos no formato JSON.
- `@Configuration`: é uma anotação que indica que a classe é uma classe de configuração Spring. Ela é utilizada para indicar que a classe contém métodos que configuram o container do Spring.
- `@Bean`: é uma anotação que indica que o método retorna um objeto que deve ser gerenciado pelo container do Spring. Ela é utilizada em métodos de configuração para indicar que o objeto retornado pelo método deve ser gerenciado pelo container do Spring.

### 3. Ao usar um `@Configuration` e `@Bean`?

- `@Configuration`: O SpringBoot vai procurar um metado que contem a anotação `@Bean`, pelo nome 
  que foi definido no método, e vai instanciar o objeto e colocar no contexto do Spring.
- Caso tenho 2 `@Bean` usando mesmo objeto, voce pode usar `@Primary` para indicar qual é o principal.