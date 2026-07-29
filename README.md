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
- Angular 20
- Leaflet
- OpenStreetMap

## Frontend com mapa

O projeto Angular está na pasta `frontend`. A tela consulta os registros da API,
exibe cada operação no mapa e mantém um histórico filtrável por funcionário,
operação e data.

Existem dois acessos:

- **Funcionário:** registra início do expediente, início/fim do almoço e fim do
  expediente usando a localização atual; também consulta somente o próprio
  histórico no mapa.
- **Gestor:** consulta o histórico e a localização de todos os funcionários.
  Também cadastra novos funcionários com nome, e-mail e senha inicial para que
  eles possam acessar o próprio painel.

Na tela de login também há um modo de demonstração para testar os dois painéis
sem precisar cadastrar usuários ou iniciar a API.

### Arquitetura do frontend

O Angular está organizado por responsabilidade:

```text
frontend/src/app
├── core
│   ├── constants    # Enum e apresentação das operações
│   ├── data         # Dados exclusivos do modo de demonstração
│   ├── guards       # Proteção das rotas por perfil
│   ├── models       # Contratos tipados da aplicação e da API
│   ├── services     # Autenticação, funcionários e histórico
│   └── utils        # Conversão dos dados da API para a interface
├── features
│   ├── auth         # Página de login
│   ├── funcionario  # Jornada e histórico pessoal
│   └── gestor       # Equipe, cadastro e histórico geral
├── shared
│   └── components   # Cabeçalho, resumo, mapa e histórico reutilizáveis
├── app.routes.ts    # Rotas carregadas sob demanda
└── app.ts           # Shell mínimo da aplicação
```

As rotas `/funcionario` e `/gestor` são protegidas pelo perfil da sessão. As
páginas são carregadas sob demanda e o acesso à API fica isolado dos componentes
visuais.

Com a API iniciada na porta `8081`, execute:

```bash
cd frontend
npm install
npm start
```

Abra `http://localhost:4200`. O servidor Angular encaminha automaticamente as
requisições `/api` para o backend. Caso a API esteja indisponível, a tela usa
registros de demonstração para facilitar o desenvolvimento visual.

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
