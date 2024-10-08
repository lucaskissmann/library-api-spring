
# API de Gerenciamento de Bibliotecas

Esta API foi desenvolvida para o gerenciamento de bibliotecas e tem como principal objetivo o exercício de boas práticas utilizando padrões de projeto, princípios SOLID e conceitos de Clean Code, além do desenvolvimento de testes unitários e automatizados utilizando JUnit e Mockito.

## Inicialização do Projeto

- **Java:** 17 instalado.
- **Banco de Dados:** Altere as informações de conexão no arquivo `application.properties`.

## Sobre o Projeto

Este sistema é um gerenciamento de bibliotecas, separado em 4 módulos:
- **Autor**
- **Livro**
- **Locatário**
- **Aluguel**

Os relacionamentos do JPA foram trabalhados de modo que todos os módulos interajam. Por exemplo, para cadastrar um livro é necessário ter um autor; para cadastrar um aluguel é necessário um locatário e um livro. Existem diversas opções de listagem, incluindo filtros. Todos os módulos possuem um CRUD completo, exceto o módulo de aluguel que não possui um método DELETE, mas sim um PUT que registra o retorno do aluguel, mantendo um histórico de locações.

Todos os módulos têm ao menos uma classe de validação. As regras de negócio foram extensivamente trabalhadas para treinamento. Assim, existem validações para:
- E-mail único
- CPF
- ISBN dos livros
- Validação para remoção de um autor (somente se não houver livros associados)
- Validação para remoção de um locatário
- Validação para remoção de um livro (somente se todas as condições forem atendidas, como não haver aluguel pendente e o livro não estar alugado)

## Boas Práticas e Padrões de Projeto

O objetivo deste projeto é explorar padrões de projeto e boas práticas com SOLID e Clean Code. Foram utilizados os seguintes padrões:

### Padrões de Criação
- **Singleton:** Para criar uma instância de `ApplicationContext`.

### Padrões Comportamentais
- **Strategy:** Para separar as validações em classes próprias.
- **Chain of Responsibility:** Para invocar todas essas validações.
- **Specification:** Para criar filtros nos endpoints de busca.

Além disso, foi criada uma interface que serve como um wrapper para que as validações sejam compatíveis tanto com DTOs de criação quanto de atualização. Tomei um grande cuidado com a nomenclatura tanto de rotas (seguindo o padrão REST) quanto de métodos e classes, utilizando nomes descritivos. Apliquei alguns padrões do SOLID, como o **S** (Single Responsibility) e o **D** (Dependency Inversion).

## Testes

Realizei testes unitários e de integração utilizando JUnit e Mockito. Criei classes Stubs para reutilização de código nos testes, além de scripts SQL para executar antes e depois dos testes de integração. Busquei atingir 90% de cobertura de testes (coverage ratio) na aplicação para garantir seu bom funcionamento. Compreendo que testes são uma parte fundamental de uma aplicação.
