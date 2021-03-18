# api-start-wars

Este projeto é uma API Rest que fornece endpoins para a criação, remoção e consultas de planetas, a API também realiza requisições a uma API externa https://swapi.dev/

Algumas das tecnologias utilizadas nesse projeto são:

* Docker-compose: Foi utilizado para criação do container com banco de dados MySql e configurações;
* Swagger: Utilizada para realizar a documentação da API e realizar testes;
* MapStruct: Utilizado para realizar o mapeamento entre o DTO e a entidade Planet;
* WebCliente: Utilizado para realizar a comunicação com a API do Star Wars;
* MockWebServer: Utilizado para mockar as requisições externas para os testes automatizados;

Passo a passo para execução do projeto;

1 - Para iniciar esse projeto caso você já possua docker em sua máquina basta executar o docker-compose através do comando:
$ docker-compose up
Assim um container com banco de dados MySql já configurado para aplicação será criado.

2 - Iniciar o projeto através do Maven:
$ ./mvnw spring-boot:run

3 - Caso queira executar os testes basta executar o seguinte comando:
$ mvn test

Para verificar os endpoints da API e seus parametros utilize o Swagger através da URL: http://localhost:8080/swagger-ui/#/