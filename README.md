# 🛒 Sistema de Vendas — API REST com Spring Boot

> **Atividade de Fixação — Desenvolvimento de API RESTful**
> CRUD completo com Spring Boot, Spring Data JPA e arquitetura em camadas.

---

## 📋 Sumário

- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Arquitetura do Projeto](#-arquitetura-do-projeto)
- [Estrutura de Pacotes](#-estrutura-de-pacotes)
- [Entidades e Modelos de Dados](#-entidades-e-modelos-de-dados)
- [Camada DTO (Data Transfer Object)](#-camada-dto-data-transfer-object)
- [Camada Mapper](#-camada-mapper)
- [Camada Repository](#-camada-repository)
- [Camada Service](#-camada-service)
- [Camada Controller (Endpoints)](#-camada-controller-endpoints)
- [Referência Completa da API](#-referência-completa-da-api)
- [Como Executar](#-como-executar)
- [Dependências Maven](#-dependências-maven)

---

## 📖 Sobre o Projeto

Este projeto foi desenvolvido como atividade de fixação com o objetivo de consolidar os conceitos de desenvolvimento de APIs RESTful utilizando o ecossistema **Spring Boot**. O tema escolhido foi um **Sistema de Vendas**, composto por 5 entidades que representam o domínio de um e-commerce ou loja: `Produto`, `Cliente`, `Categoria`, `Marca` e `Setor`.

Cada entidade possui um **CRUD completo** (Create, Read, Update, Delete) exposto via endpoints HTTP, seguindo as boas práticas de design de APIs REST e a separação de responsabilidades por camadas.

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Finalidade |
|---|---|---|
| **Java** | 17+ | Linguagem principal |
| **Spring Boot** | 3.x | Framework principal da aplicação |
| **Spring Web** | — | Criação dos endpoints REST (`@RestController`) |
| **Spring Data JPA** | — | Abstração do acesso ao banco de dados |
| **Hibernate** | — | Implementação JPA (ORM) |
| **Lombok** | — | Redução de boilerplate (`@Data`, `@RequiredArgsConstructor`) |
| **H2 / MySQL** | — | Banco de dados (em memória para testes ou relacional) |
| **Maven** | — | Gerenciador de dependências e build |

---

## 🏛️ Arquitetura do Projeto

O projeto segue a **Arquitetura em Camadas** (Layered Architecture), onde cada camada tem uma responsabilidade única e bem definida. O fluxo de uma requisição percorre as camadas de cima para baixo:

```
Cliente HTTP (Postman / Browser)
         │
         ▼
┌─────────────────────────────────┐
│         CONTROLLER              │  ← Recebe a requisição HTTP, valida e delega
│   (@RestController)             │
└────────────────┬────────────────┘
                 │ chama
┌────────────────▼────────────────┐
│           SERVICE               │  ← Contém as regras de negócio, gerencia transações
│   (@Service + @Transactional)   │
└──────────┬──────────┬───────────┘
           │ usa      │ usa
┌──────────▼──┐  ┌────▼──────────┐
│   MAPPER    │  │  REPOSITORY   │  ← Mapper converte DTOs ↔ Entidades
│ (@Component)│  │ (JpaRepository│     Repository acessa o banco via JPA
└─────────────┘  └───────────────┘
                         │
                 ┌───────▼───────┐
                 │  BANCO DE     │
                 │    DADOS      │
                 └───────────────┘
```

### Fluxo de dados detalhado

```
POST /api/produtos  ──► ProdutoController
                              │ recebe ProdutoRequisicao (DTO)
                              ▼
                        ProdutoService
                              │ chama ProdutoMapper.paraEntidade(dto)
                              │ chama ProdutoRepository.save(entidade)
                              │ chama ProdutoMapper.paraResposta(entidade)
                              ▼
                        ProdutoResposta (DTO)  ──► 201 Created
```

---

## 📁 Estrutura de Pacotes

```
src/main/java/com/example/Atividade_Fixacao/
│
├── controller/                  ← Camada de apresentação (endpoints HTTP)
│   ├── CategoriaController.java
│   ├── ClienteController.java
│   ├── MarcaController.java
│   ├── ProdutoController.java
│   └── SetorController.java
│
├── service/                     ← Camada de negócio (regras e transações)
│   ├── CategoriaService.java
│   ├── ClienteService.java
│   ├── MarcaService.java
│   ├── ProdutoService.java
│   └── SetorService.java
│
├── repository/                  ← Camada de acesso a dados (Spring Data JPA)
│   ├── CategoriaRepository.java
│   ├── ClienteRepository.java
│   ├── MarcaRepository.java
│   ├── ProdutoRepository.java
│   └── SetorRepository.java
│
├── model/                       ← Entidades JPA (mapeamento objeto-relacional)
│   ├── Categoria.java
│   ├── Cliente.java
│   ├── Marca.java
│   ├── Produto.java
│   └── Setor.java
│
├── dto/                         ← Data Transfer Objects (Records Java)
│   ├── categoria/
│   │   ├── CategoriaRequisicao.java
│   │   └── CategoriaResposta.java
│   ├── cliente/
│   │   ├── ClienteRequisicao.java
│   │   └── ClienteResposta.java
│   ├── marca/
│   │   ├── MarcaRequisicao.java
│   │   └── MarcaResposta.java
│   ├── produto/
│   │   ├── ProdutoRequisicao.java
│   │   └── ProdutoResposta.java
│   └── setor/
│       ├── SetorRequisicao.java
│       └── SetorResposta.java
│
└── mapper/                      ← Conversores DTO ↔ Entidade
    ├── CategoriaMapper.java
    ├── ClienteMapper.java
    ├── MarcaMapper.java
    ├── ProdutoMapper.java
    └── SetorMapper.java
```

---

## 🗂️ Entidades e Modelos de Dados

As entidades são classes anotadas com `@Entity` do JPA, mapeadas diretamente para tabelas no banco de dados. Utilizam **Lombok** para eliminar boilerplate de getters, setters e construtores.

### Produto

```java
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Produto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private BigDecimal preco;
}
```

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | `Long` | Chave primária, gerada automaticamente (auto-increment) |
| `nome` | `String` | Nome do produto |
| `preco` | `BigDecimal` | Preço com precisão decimal (recomendado para valores monetários) |

---

### Cliente

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | `Long` | Chave primária, gerada automaticamente |
| `nome` | `String` | Nome completo do cliente |
| `email` | `String` | Endereço de e-mail |

---

### Categoria

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | `Long` | Chave primária, gerada automaticamente |
| `nome` | `String` | Nome da categoria (ex: Eletrônicos, Roupas) |
| `descricao` | `String` | Descrição detalhada da categoria |

---

### Marca

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | `Long` | Chave primária, gerada automaticamente |
| `nome` | `String` | Nome da marca (ex: Samsung, Nike) |
| `paisOrigem` | `String` | País de origem da marca |

---

### Setor

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | `Long` | Chave primária, gerada automaticamente |
| `sigla` | `String` | Sigla do setor (ex: TI, RH, VND) |
| `nome` | `String` | Nome completo do setor |

---

## 📦 Camada DTO (Data Transfer Object)

Os DTOs são implementados como **Java Records** (imutáveis por padrão), introduzidos no Java 16. Eles servem para desacoplar a representação da API da estrutura interna das entidades JPA, evitando expor campos sensíveis ou desnecessários.

Cada recurso possui dois Records:

- **`*Requisicao`** — representa o corpo da requisição (entrada). Não contém `id`.
- **`*Resposta`** — representa o corpo da resposta (saída). Contém `id` e os campos retornados.

### Exemplo — Produto

```java
// Entrada (Request Body)
public record ProdutoRequisicao(
        String nome,
        BigDecimal preco
) {}

// Saída (Response Body)
public record ProdutoResposta(
        Long id,
        String nome,
        BigDecimal preco
) {}
```

### Resumo dos DTOs por entidade

| Entidade | Campos de Requisição | Campos de Resposta |
|---|---|---|
| **Produto** | `nome`, `preco` | `id`, `nome`, `preco` |
| **Cliente** | `nome`, `email` | `id`, `nome`, `email` |
| **Categoria** | `nome`, `descricao` | `id`, `nome`, `descricao` |
| **Marca** | `nome`, `paisOrigem` | `id`, `nome`, `paisOrigem` |
| **Setor** | `sigla`, `nome` | `id`, `sigla`, `nome` |

---

## 🔄 Camada Mapper

Os **Mappers** são componentes Spring (`@Component`) responsáveis pela conversão bidirecional entre DTOs e Entidades. Essa separação mantém o SRP — a entidade não precisa conhecer os DTOs e vice-versa.

Cada Mapper possui dois métodos:

- **`paraEntidade(dto)`** — converte o DTO de requisição em uma entidade JPA (com `id = null`, pois ainda não foi persistida)
- **`paraResposta(entidade)`** — converte a entidade JPA persistida em um DTO de resposta

### Exemplo — MarcaMapper

```java
@Component
public class MarcaMapper {

    public Marca paraEntidade(MarcaRequisicao dto) {
        return new Marca(null, dto.nome(), dto.paisOrigem());
    }

    public MarcaResposta paraResposta(Marca e) {
        return new MarcaResposta(e.getId(), e.getNome(), e.getPaisOrigem());
    }
}
```

> **Por que `id = null` no `paraEntidade`?**
> Ao criar uma nova entidade, o `id` ainda não existe — ele será gerado pelo banco de dados após o `repository.save()`. Passar `null` intencionalmente garante que o JPA trate a operação como `INSERT` e não como `UPDATE`.

---

## 🗄️ Camada Repository

Os Repositories estendem `JpaRepository<Entidade, TipoDoId>`, herdando automaticamente todos os métodos de persistência do Spring Data JPA. Não é necessário escrever nenhuma query SQL para operações básicas.

```java
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // findAll(), findById(), save(), deleteById(), existsById()
    // — todos herdados automaticamente do JpaRepository
}
```

### Métodos herdados do JpaRepository utilizados no projeto

| Método | Operação SQL equivalente | Usado em |
|---|---|---|
| `save(entidade)` | `INSERT` ou `UPDATE` | `criarXxx()` e `atualizar()` |
| `findAll()` | `SELECT * FROM tabela` | `buscarTodos()` |
| `findById(id)` | `SELECT * WHERE id = ?` | `buscarPorId()` e `atualizar()` |
| `existsById(id)` | `SELECT COUNT(*) WHERE id = ?` | `deletar()` (validação) |
| `deleteById(id)` | `DELETE WHERE id = ?` | `deletar()` |

---

## ⚙️ Camada Service

Os Services são anotados com `@Service` e gerenciam as **transações de banco de dados** via `@Transactional`. Eles orquestram a interação entre Repository e Mapper, e centralizam o tratamento de erros de negócio.

### Decisões de design

- `@Transactional` em operações de escrita garante **atomicidade** — se algo falhar, o banco reverte automaticamente (rollback).
- `@Transactional(readOnly = true)` em consultas informa ao Hibernate que não haverá escrita, permitindo otimizações de performance (desativa o dirty checking).
- `orElseThrow(RuntimeException)` lança exceção quando um recurso não é encontrado, podendo retornar um `404` quando tratado por um `@ControllerAdvice`.

### Exemplo — ProdutoService

```java
@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;

    @Transactional
    public ProdutoResposta criarProduto(ProdutoRequisicao dto) {
        Produto entity = mapper.paraEntidade(dto);
        return mapper.paraResposta(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public ProdutoResposta buscarPorId(Long id) {
        Produto entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return mapper.paraResposta(entity);
    }

    @Transactional
    public ProdutoResposta atualizar(Long id, ProdutoRequisicao dto) {
        Produto entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        entity.setNome(dto.nome());
        entity.setPreco(dto.preco());
        return mapper.paraResposta(repository.save(entity));
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }
        repository.deleteById(id);
    }
}
```

---

## 🌐 Camada Controller (Endpoints)

Os Controllers são anotados com `@RestController` e definem os endpoints HTTP da API. Utilizam `@RequiredArgsConstructor` do Lombok para injeção de dependência via construtor.

- **`ResponseEntity`** — controle explícito sobre o status HTTP da resposta.
- **`@PathVariable`** — mapeia o `{id}` da URL para o parâmetro do método.
- **`@RequestBody`** — deserializa automaticamente o JSON da requisição para o DTO.

### Exemplo — ProdutoController

```java
@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    @PostMapping
    public ResponseEntity<ProdutoResposta> create(@RequestBody ProdutoRequisicao dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarProduto(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResposta>> findAll() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResposta> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResposta> update(@PathVariable Long id,
                                                   @RequestBody ProdutoRequisicao dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## 📡 Referência Completa da API

### 🛍️ Produtos — `/api/produtos`

| Método | Endpoint | Descrição | Status |
|---|---|---|---|
| `POST` | `/api/produtos` | Cria um novo produto | `201 Created` |
| `GET` | `/api/produtos` | Lista todos os produtos | `200 OK` |
| `GET` | `/api/produtos/{id}` | Busca produto por ID | `200 OK` |
| `PUT` | `/api/produtos/{id}` | Atualiza um produto | `200 OK` |
| `DELETE` | `/api/produtos/{id}` | Remove um produto | `204 No Content` |

**Request Body:** `{ "nome": "Notebook Gamer", "preco": 4599.90 }`

**Response Body:** `{ "id": 1, "nome": "Notebook Gamer", "preco": 4599.90 }`

---

### 👤 Clientes — `/api/clientes`

| Método | Endpoint | Descrição | Status |
|---|---|---|---|
| `POST` | `/api/clientes` | Cadastra um novo cliente | `201 Created` |
| `GET` | `/api/clientes` | Lista todos os clientes | `200 OK` |
| `GET` | `/api/clientes/{id}` | Busca cliente por ID | `200 OK` |
| `PUT` | `/api/clientes/{id}` | Atualiza um cliente | `200 OK` |
| `DELETE` | `/api/clientes/{id}` | Remove um cliente | `204 No Content` |

**Request Body:** `{ "nome": "João da Silva", "email": "joao@email.com" }`

**Response Body:** `{ "id": 1, "nome": "João da Silva", "email": "joao@email.com" }`

---

### 🏷️ Categorias — `/api/categorias`

| Método | Endpoint | Descrição | Status |
|---|---|---|---|
| `POST` | `/api/categorias` | Cria uma nova categoria | `201 Created` |
| `GET` | `/api/categorias` | Lista todas as categorias | `200 OK` |
| `GET` | `/api/categorias/{id}` | Busca categoria por ID | `200 OK` |
| `PUT` | `/api/categorias/{id}` | Atualiza uma categoria | `200 OK` |
| `DELETE` | `/api/categorias/{id}` | Remove uma categoria | `204 No Content` |

**Request Body:** `{ "nome": "Eletrônicos", "descricao": "Produtos eletrônicos em geral" }`

**Response Body:** `{ "id": 1, "nome": "Eletrônicos", "descricao": "Produtos eletrônicos em geral" }`

---

### ® Marcas — `/api/marcas`

| Método | Endpoint | Descrição | Status |
|---|---|---|---|
| `POST` | `/api/marcas` | Cadastra uma nova marca | `201 Created` |
| `GET` | `/api/marcas` | Lista todas as marcas | `200 OK` |
| `GET` | `/api/marcas/{id}` | Busca marca por ID | `200 OK` |
| `PUT` | `/api/marcas/{id}` | Atualiza uma marca | `200 OK` |
| `DELETE` | `/api/marcas/{id}` | Remove uma marca | `204 No Content` |

**Request Body:** `{ "nome": "Samsung", "paisOrigem": "Coreia do Sul" }`

**Response Body:** `{ "id": 1, "nome": "Samsung", "paisOrigem": "Coreia do Sul" }`

---

### 🏢 Setores — `/api/setores`

| Método | Endpoint | Descrição | Status |
|---|---|---|---|
| `POST` | `/api/setores` | Cria um novo setor | `201 Created` |
| `GET` | `/api/setores` | Lista todos os setores | `200 OK` |
| `GET` | `/api/setores/{id}` | Busca setor por ID | `200 OK` |
| `PUT` | `/api/setores/{id}` | Atualiza um setor | `200 OK` |
| `DELETE` | `/api/setores/{id}` | Remove um setor | `204 No Content` |

**Request Body:** `{ "sigla": "VND", "nome": "Vendas" }`

**Response Body:** `{ "id": 1, "sigla": "VND", "nome": "Vendas" }`

---

## ▶️ Como Executar

### Pré-requisitos

- Java 17+
- Maven 3.8+
- IDE recomendada: IntelliJ IDEA

### Passos

```bash
# 1. Clone o repositório
git clone https://github.com/seu-usuario/Atividade_Fixacao.git
cd Atividade_Fixacao

# 2. Execute a aplicação
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

### Testando com curl

```bash
# Criar um produto
curl -X POST http://localhost:8080/api/produtos \
  -H "Content-Type: application/json" \
  -d '{"nome": "Mouse Gamer", "preco": 299.90}'

# Listar todos os produtos
curl http://localhost:8080/api/produtos

# Buscar produto por ID
curl http://localhost:8080/api/produtos/1

# Atualizar produto
curl -X PUT http://localhost:8080/api/produtos/1 \
  -H "Content-Type: application/json" \
  -d '{"nome": "Mouse Gamer Pro", "preco": 349.90}'

# Deletar produto
curl -X DELETE http://localhost:8080/api/produtos/1
```

---

## 📦 Dependências Maven

```xml
<dependencies>
    <!-- Spring Web: cria os endpoints REST -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Data JPA + Hibernate: ORM e acesso ao banco -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- Banco H2 em memória (para desenvolvimento e testes) -->
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Lombok: elimina boilerplate (@Data, @RequiredArgsConstructor, etc.) -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

---

## 📌 Resumo das Decisões Técnicas

| Decisão | Justificativa |
|---|---|
| **Java Records para DTOs** | Imutabilidade garantida, código conciso, sem necessidade de Lombok nos DTOs |
| **Mapper manual (`@Component`)** | Controle explícito da conversão sem dependência de bibliotecas externas como MapStruct |
| **`@Transactional(readOnly = true)`** | Otimiza consultas desativando o dirty checking do Hibernate |
| **`ResponseEntity` em todos os endpoints** | Controle explícito e semântico dos status HTTP da resposta |
| **`@RequiredArgsConstructor` + campos `final`** | Injeção de dependência via construtor (prática recomendada pelo Spring) |
| **`existsById` antes do `deleteById`** | Retorna erro explícito ao tentar deletar um recurso inexistente |

---

*Atividade de Fixação — Desenvolvimento de API RESTful com Spring Boot e Spring Data JPA*
