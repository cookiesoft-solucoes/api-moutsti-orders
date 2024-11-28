# API de Processamento de Orders

Este projeto é uma API REST desenvolvida com **Spring Boot**, que utiliza **RabbitMQ** para troca de mensagens e **PostgreSQL** como banco de dados. O objetivo principal da API é gerenciar e processar pedidos (**Orders**) de forma eficiente e escalável.

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação principal.
- **Spring Boot 3.x**: Framework para criação de aplicações Java modernas.
    - **Spring Web**: Para desenvolvimento de endpoints REST.
    - **Spring Data JPA**: Para acesso e gerenciamento do banco de dados.
    - **Spring AMQP**: Para integração com RabbitMQ.
    - **Spring Security** (opcional): Para autenticação e autorização (se necessário).
- **PostgreSQL**: Banco de dados relacional.
- **RabbitMQ**: Mensageria para processamento assíncrono de pedidos.
- **Docker**: Para gerenciamento de contêineres.
- **Maven**: Ferramenta para gerenciamento de dependências e build.

## Funcionalidades

- **Cadastro de Pedidos (Orders)**:
    - Criação de pedidos com validação dos dados.
    - Atualização e remoção de pedidos.
- **Processamento de Pedidos**:
    - Integração com **RabbitMQ** para envio e consumo de mensagens.
    - Sistema de filas para pedidos a serem processados.
- **Consulta de Pedidos**:
    - Listagem de pedidos.
    - Consulta por status (pendente, processado, etc.).


## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas:

- **Java 17**
- **Maven**
- **Docker e Docker Compose**

## Como Rodar a Aplicação

### 1. Configurar o Ambiente
Utilize o arquivo `docker-compose.yml` para subir as dependências (RabbitMQ e PostgreSQL):

```bash
docker-compose up -d


