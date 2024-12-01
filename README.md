
# API Moutsti Orders

## Descrição do Projeto

A **API Moutsti Orders** é uma aplicação backend desenvolvida em **Spring Boot**, projetada para gerenciar pedidos e categorias de produtos em um sistema de comércio eletrônico. Este projeto foi desenvolvido com foco em boas práticas de engenharia de software, integração contínua e uso de ferramentas modernas de desenvolvimento.

### Gerenciamento do Projeto com Jira

O gerenciamento de tarefas foi realizado utilizando o **Jira**, onde foram definidas as seguintes etapas:

- **Backlog**: Estruturamos o backlog com todas as tarefas, histórias de usuário e bugs.
- **Sprints**: Criamos sprints de duas semanas para planejar e acompanhar as entregas do projeto.
- **Acompanhamento**: Monitoramos o progresso do projeto por meio de relatórios do Jira, garantindo entregas consistentes.

---

## Configuração do Ambiente de Desenvolvimento

O ambiente de desenvolvimento foi configurado utilizando **Docker Compose** para simplificar a gestão de dependências. O arquivo `docker-compose.yaml` configura os seguintes serviços:

### Serviços Configurados

#### **PostgreSQL**
Banco de dados utilizado pela API para armazenamento de dados.
- **Imagem**: `postgres:9.6`
- **Configuração**:
  - Usuário: `user_moutsti`
  - Senha: `moutsti2024`
  - Banco: `db_api_moutsti_orders`
- Porta exposta: `5432`

#### **RabbitMQ**
Mensageria para troca de mensagens entre os serviços.
- **Imagem**: `rabbitmq:3-management`
- **Configuração**:
  - Usuário: `moutsti`
  - Senha: `moutsti2024`
- Portas:
  - AMQP: `5672`
  - Painel de gerenciamento: `15672`

#### **Redis**
Cache utilizado para otimizar operações e gerenciar armazenamento.
- **Imagem**: `redis:latest`
- Porta: `6379`

#### **Redis Insight**
Interface para visualização e gerenciamento do Redis.
- **Imagem**: `redis/redisinsight:latest`
- Porta: `5540`

### Arquivo `docker-compose.yaml`

```yaml
services:
  postgres:
    image: postgres:9.6
    container_name: moutsti_database
    restart: always
    environment:
      POSTGRES_USER: user_moutsti
      POSTGRES_PASSWORD: moutsti2024
      POSTGRES_DB: db_api_moutsti_orders
    ports:
      - "5432:5432"
    volumes:
      - database_moutsti_data:/var/lib/postgresql/data
    networks:
      - moutsti-network

  rabbitmq:
    image: rabbitmq:3-management
    container_name: moutsti_rabbitmq
    restart: always
    ports:
      - "5672:5672"
      - "15672:15672"
    environment:
      RABBITMQ_DEFAULT_USER: moutsti
      RABBITMQ_DEFAULT_PASS: moutsti2024
    networks:
      - moutsti-network

  redis:
    image: redis:latest
    container_name: redis
    command: [ "redis-server", "--appendonly", "yes" ]
    ports:
      - "6379:6379"
    volumes:
      - redis_data:/data
    networks:
      - moutsti-network

  redis-insight:
    image: redis/redisinsight:latest
    container_name: redis-insight
    ports:
      - "5540:5540"
    networks:
      - moutsti-network
    depends_on:
      - redis

volumes:
  database_moutsti_data:
  redis_data:
    driver: local

networks:
  moutsti-network:
    driver: bridge
```

---

## Integração Contínua e Entrega Contínua (CI/CD)

Utilizamos o **GitHub Actions** para implementar pipelines de CI/CD com os seguintes arquivos de configuração:

### Pipeline de Testes e Análise de Qualidade (`ci_pipeline.yml`)

```yaml
name: CI Pipeline API Moutsti Orders

on:
  pull_request:
    branches:
      - develop
      - main

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v2
      - name: Set up JDK 17
        uses: actions/setup-java@v2
        with:
          distribution: 'adopt'
          java-version: '17'
      - name: Run Maven tests
        run: mvn test

  sonarcloud:
    runs-on: ubuntu-latest
    needs: test
    steps:
      - name: Checkout code
        uses: actions/checkout@v2
      - name: Set up JDK 17
        uses: actions/setup-java@v2
        with:
          distribution: 'adopt'
          java-version: '17'
      - name: SonarQube Scan
        env:
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
        run: |
          mvn clean verify
          mvn sonar:sonar             -Dsonar.projectKey=cookiesoft-solucoes-em-ti-ltda_api-moutsti-orders             -Dsonar.organization=cookiesoft-solucoes-em-ti-ltda             -Dsonar.host.url=https://sonarcloud.io             -Dsonar.branch.name=${{ github.event.pull_request.base.ref }}             -Dsonar.java.binaries=target/classes             -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml             -Dsonar.junit.reportPaths=target/surefire-reports
```

---

### Pipeline de Build e Deploy da Imagem Docker (`build_image_pipeline.yml`)

```yaml
name: Run Test and Build Image API Moutsti Orders

on:
  push:
    branches:
      - main
      - develop

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Generate TIMESTAMP
        id: generate-timestamp
        run: echo "timestamp=$(date +%Y%m%d%H%M%S)" >> $GITHUB_OUTPUT
      - name: Checkout code
        uses: actions/checkout@v2
      - name: Set up JDK 17
        uses: actions/setup-java@v1
        with:
          java-version: '17'
      - name: Build with Maven
        run: mvn clean package
      - name: Log in to DigitalOcean Container Registry
        run: echo ${{ secrets.DOCKER_TOKEN }} | docker login registry.digitalocean.com -u DOCR --password-stdin
      - name: Build the Docker image
        env:
          TIMESTAMP: ${{ steps.generate-timestamp.outputs.timestamp }}
          BRANCH_NAME: ${{ github.ref_name }}
        run: |
          IMAGE_TAG=registry.digitalocean.com/repository-api-moutsti-orders/api-orders:${BRANCH_NAME}-${TIMESTAMP}
          docker build . --tag $IMAGE_TAG
      - name: Push Docker image to DigitalOcean
        env:
          TIMESTAMP: ${{ steps.generate-timestamp.outputs.timestamp }}
          BRANCH_NAME: ${{ github.ref_name }}
        run: |
          IMAGE_TAG=registry.digitalocean.com/repository-api-moutsti-orders/api-orders:${BRANCH_NAME}-${TIMESTAMP}
          docker push $IMAGE_TAG
```

---

## Configuração de Infraestrutura com Terraform

A infraestrutura é provisionada na **DigitalOcean** utilizando **Terraform** com a seguinte arquitetura:

### Estrutura

- **2 Droplets**: Para configurar um cluster de banco de dados PostgreSQL.
- **1 Cluster Kubernetes**: Para implantação dos serviços.
- **1 Droplet**: Para hospedar o RabbitMQ e o Redis.

---

## Executando o Projeto Localmente

1. Clone o repositório:
   ```bash
   git clone https://github.com/alysonrodrigo/api-moutsti-orders.git
   ```
2. Suba os serviços com Docker Compose:
   ```bash
   docker-compose up -d
   ```
3. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```
---

## Publicação de Artigos no Medium
Para documentar todo o processo de desenvolvimento da API Moutsti Orders, serão publicados 3 artigos no Medium. Cada artigo abordará aspectos específicos do projeto, oferecendo um guia detalhado para desenvolvedores e entusiastas:

1. Planejamento de Atividades com Jira:
   - Estruturação do backlog de tarefas.
   - Criação de sprints e organização do fluxo de trabalho.
   - Monitoramento de progresso e uso de relatórios para entregas ágeis.
   

2. Fluxo de Desenvolvimento Local e Práticas de DevOps:
   - Configuração do ambiente de desenvolvimento local com Docker Compose
   - Implementação de CI/CD utilizando GitHub Actions para entrega rápida e contínua.
   - Monitoramento e qualidade do código com SonarCloud.


3. Infraestrutura como Código com Terraform:
  - Provisionamento da infraestrutura na DigitalOcean.
  - Configuração de Droplets para banco de dados e mensageria.
  - Configuração de clusters Kubernetes para escalabilidade e alta disponibilidade.

Esses artigos têm como objetivo compartilhar as lições aprendidas, boas práticas adotadas e desafios superados durante o desenvolvimento da API.

### Segue os Links dos artigos que estão em elaboração

1. [Planejamento de Atividades com Jira](https://medium.com/@aci.alyson/desafio-api-moutsti-planejamento-de-atividades-com-jira-organizando-o-sucesso-do-projeto-b7ddda485893)
2. [Fluxo de Desenvolvimento Local e Práticas de DevOps](https://medium.com/@aci.alyson/desafio-api-moutsti-fluxo-de-desenvolvimento-local-e-pr%C3%A1ticas-de-devops-do-c%C3%B3digo-%C3%A0-produ%C3%A7%C3%A3o-25557fc31775)
3. [Infraestrutura como Código com Terraform](https://medium.com/@aci.alyson/desafio-api-moutsti-infraestrutura-como-c%C3%B3digo-com-terraform-automatizando-a-base-do-projeto-5c3a8311c2fe)

---

## Contribuições

Sinta-se à vontade para abrir **issues** e enviar **pull requests** para melhorias ou correções.

---

