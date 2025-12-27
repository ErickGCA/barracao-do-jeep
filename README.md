# Sistema de Inventário - Oficina

Sistema back-end em Java/Spring Boot para gerenciamento de inventário de oficina mecânica.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- Spring Validation
- PostgreSQL (produção)
- H2 Database (desenvolvimento)
- Lombok
- Maven

## Funcionalidades

### Gerenciamento de Categorias
- Criar, listar, atualizar e desativar categorias
- Organização hierárquica de itens

### Gerenciamento de Itens
- CRUD completo de itens do inventário
- Controle de estoque atual e mínimo
- Busca por código único
- Filtro por categoria
- Alerta de estoque baixo
- Localização física do item

### Controle de Movimentações
- Registro de entradas de estoque
- Registro de saídas de estoque
- Histórico completo de movimentações por item
- Validação de quantidade disponível
- Auditoria com responsável e data/hora

## Estrutura do Projeto

```
src/main/java/com/oficina/inventario/
├── config/              # Configurações (CORS)
├── controller/          # Controllers REST
├── dto/                 # Data Transfer Objects
├── entity/              # Entidades JPA
├── enums/               # Enumerações
├── exception/           # Tratamento de exceções
├── repository/          # Repositories JPA
├── service/             # Lógica de negócio
└── InventarioApplication.java
```

## Pré-requisitos

- JDK 17 ou superior
- Maven 3.6+
- PostgreSQL 12+ (para produção)

## Instalação e Execução

### 1. Clonar o repositório

```bash
git clone <url-do-repositorio>
cd barracaodoJeep
```

### 2. Configurar o Banco de Dados

#### Opção A: Usando PostgreSQL (Produção)

Criar banco de dados:
```sql
CREATE DATABASE inventario_oficina;
```

Editar `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/inventario_oficina
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

#### Opção B: Usando H2 (Desenvolvimento)

Executar com profile dev (banco em memória):
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Acessar console H2: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:inventario_dev`
- Username: `sa`
- Password: (vazio)

### 3. Compilar e Executar

```bash
# Compilar o projeto
mvn clean install

# Executar a aplicação
mvn spring-boot:run

# Ou executar o JAR gerado
java -jar target/inventario-1.0.0.jar
```

A aplicação estará disponível em: http://localhost:8080

## Endpoints da API

### Categorias

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/categorias` | Lista todas as categorias |
| GET | `/api/categorias?apenasAtivas=true` | Lista apenas categorias ativas |
| GET | `/api/categorias/{id}` | Busca categoria por ID |
| POST | `/api/categorias` | Cria nova categoria |
| PUT | `/api/categorias/{id}` | Atualiza categoria |
| DELETE | `/api/categorias/{id}` | Desativa categoria |

### Itens

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/itens` | Lista todos os itens |
| GET | `/api/itens?apenasAtivos=true` | Lista apenas itens ativos |
| GET | `/api/itens/{id}` | Busca item por ID |
| GET | `/api/itens/codigo/{codigo}` | Busca item por código |
| GET | `/api/itens/categoria/{categoriaId}` | Lista itens por categoria |
| GET | `/api/itens/estoque-baixo` | Lista itens com estoque baixo |
| POST | `/api/itens` | Cria novo item |
| PUT | `/api/itens/{id}` | Atualiza item |
| DELETE | `/api/itens/{id}` | Desativa item |

### Movimentações

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/movimentacoes` | Lista todas as movimentações |
| GET | `/api/movimentacoes/{id}` | Busca movimentação por ID |
| GET | `/api/movimentacoes/item/{itemId}` | Histórico de movimentações de um item |
| POST | `/api/movimentacoes/entrada` | Registra entrada de estoque |
| POST | `/api/movimentacoes/saida` | Registra saída de estoque |

## Exemplos de Uso

### Criar Categoria

```bash
POST http://localhost:8080/api/categorias
Content-Type: application/json

{
  "nome": "Ferramentas",
  "descricao": "Ferramentas manuais e elétricas"
}
```

### Criar Item

```bash
POST http://localhost:8080/api/itens
Content-Type: application/json

{
  "codigo": "PARA-001",
  "nome": "Parafuso M8x50",
  "descricao": "Parafuso sextavado M8 comprimento 50mm",
  "unidadeMedida": "UN",
  "quantidadeMinima": 100,
  "localizacao": "Prateleira A3",
  "categoriaId": 1
}
```

### Registrar Entrada de Estoque

```bash
POST http://localhost:8080/api/movimentacoes/entrada
Content-Type: application/json

{
  "itemId": 1,
  "quantidade": 500,
  "observacao": "Compra fornecedor XYZ",
  "responsavel": "João Silva"
}
```

### Registrar Saída de Estoque

```bash
POST http://localhost:8080/api/movimentacoes/saida
Content-Type: application/json

{
  "itemId": 1,
  "quantidade": 50,
  "observacao": "Usado no serviço OS-123",
  "responsavel": "Maria Santos"
}
```

## Validações Implementadas

### Categoria
- Nome: obrigatório, 3-100 caracteres, único
- Descrição: opcional, máximo 500 caracteres

### Item
- Código: obrigatório, 3-20 caracteres, único
- Nome: obrigatório, 3-100 caracteres
- Unidade de medida: obrigatória
- Quantidade mínima: >= 0
- Categoria: obrigatória e deve existir

### Movimentação
- Quantidade: > 0
- Item: obrigatório e deve existir
- Responsável: obrigatório, 3-100 caracteres
- Validação de estoque disponível para saídas

## Regras de Negócio

1. **Itens** sempre começam com quantidade 0
2. **Entradas** adicionam ao estoque
3. **Saídas** não podem exceder quantidade disponível
4. **Códigos** de itens devem ser únicos
5. **Nomes** de categorias devem ser únicos
6. Não há exclusão física, apenas **desativação**
7. Não é possível movimentar itens inativos

## Logs e Debugging

A aplicação está configurada para exibir SQL queries no console:

```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.SQL=DEBUG
```

## Próximos Passos (Evolução)

### Fase 2
- [ ] Autenticação e autorização (Spring Security + JWT)
- [ ] Auditoria completa (created_by, updated_by)
- [ ] Notificações de estoque baixo
- [ ] Dashboard com métricas

### Fase 3
- [ ] Gerenciamento de fornecedores
- [ ] Controle de compras
- [ ] Relatórios exportáveis (PDF, Excel)
- [ ] API para código de barras

### Fase 4
- [ ] Ordem de serviço
- [ ] Controle financeiro
- [ ] Integração com clientes
- [ ] Aplicativo mobile

## Documentação Técnica

Consulte o arquivo `TECHNICAL_DESIGN.md` para:
- Modelo de dados detalhado
- Arquitetura completa
- Relacionamentos entre entidades
- Decisões técnicas

## Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

## Licença

Este projeto está sob a licença MIT.

## Suporte

Para dúvidas e suporte, entre em contato através do email: suporte@oficina.com
