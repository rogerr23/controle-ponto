# API de Controle de Ponto

API REST para controle de ponto de funcionários. O sistema permite cadastrar
funcionários e gestores, realizar autenticação e registrar o histórico diário
de operações de ponto.

> Projeto em desenvolvimento.

## Funcionalidades

- Cadastro e consulta de funcionários
- Cadastro e consulta de gestores
- Autenticação de funcionários e gestores
- Registro de operações de ponto
- Consulta do histórico completo
- Consulta do histórico por funcionário
- Consulta do histórico diário de um funcionário
- Registro de latitude e longitude da operação

As operações de ponto disponíveis são:

- Início do expediente
- Início do almoço
- Fim do almoço
- Fim do expediente

## Tecnologias

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Docker Compose
- Maven
- Lombok
- ModelMapper
- Bean Validation
- Swagger/OpenAPI

## Estrutura

```text
src/main/java/rogerr/com/controleponto
├── configurations  # Configurações da aplicação
├── controllers     # Endpoints da API
├── dtos            # Objetos de entrada e saída
├── entities        # Entidades do banco de dados
├── handlers        # Tratamento de exceções
├── repositories    # Acesso ao banco de dados
└── services        # Contratos e regras de negócio
    └── impl        # Implementações dos serviços
```

## Como executar

### Pré-requisitos

- Java 21
- Docker

### 1. Inicie o PostgreSQL

Na raiz do projeto, execute:

```bash
docker compose up -d
```

O Docker criará o banco com a seguinte configuração local:

```text
Host: localhost
Porta: 5432
Banco: bd_controle_ponto
Usuário: postgres
Senha: roger
```

Essas credenciais são destinadas somente ao ambiente local de desenvolvimento.

### 2. Inicie a aplicação

No macOS ou Linux:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

A API ficará disponível em:

```text
http://localhost:8081
```

## Documentação da API

Com a aplicação em execução, acesse o Swagger:

```text
http://localhost:8081/swagger-ui/index.html
```

O documento OpenAPI também pode ser consultado em:

```text
http://localhost:8081/v3/api-docs
```

## Parando o banco

Para parar o PostgreSQL sem apagar os dados:

```bash
docker compose down
```

Os dados são preservados no volume criado pelo Docker.
