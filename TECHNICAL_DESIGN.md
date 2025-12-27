# Sistema de Inventário - Oficina (Back-end)

## 1. Modelo de Dados

### Entidades Principais

#### 1.1 Categoria
Organiza os itens em grupos lógicos
- `id` (Long) - PK
- `nome` (String) - Nome da categoria (ex: "Ferramentas", "Peças", "Consumíveis")
- `descricao` (String) - Descrição opcional
- `ativo` (Boolean) - Flag de ativação
- `createdAt` (LocalDateTime)
- `updatedAt` (LocalDateTime)

#### 1.2 Item
Representa cada produto do inventário
- `id` (Long) - PK
- `codigo` (String) - Código único do item (ex: "PARA-001")
- `nome` (String) - Nome do item
- `descricao` (String) - Descrição detalhada
- `unidadeMedida` (String) - Unidade (ex: "UN", "KG", "PC")
- `quantidadeAtual` (Integer) - Quantidade em estoque
- `quantidadeMinima` (Integer) - Estoque mínimo (alerta)
- `localizacao` (String) - Onde está guardado
- `categoria_id` (Long) - FK para Categoria
- `ativo` (Boolean)
- `createdAt` (LocalDateTime)
- `updatedAt` (LocalDateTime)

#### 1.3 Movimentacao
Registra todas as entradas e saídas
- `id` (Long) - PK
- `item_id` (Long) - FK para Item
- `tipoMovimentacao` (Enum) - ENTRADA ou SAIDA
- `quantidade` (Integer) - Quantidade movimentada
- `quantidadeAnterior` (Integer) - Estoque antes da movimentação
- `quantidadeNova` (Integer) - Estoque após movimentação
- `observacao` (String) - Motivo/observação
- `responsavel` (String) - Quem fez a movimentação
- `dataMovimentacao` (LocalDateTime)

### Relacionamentos
- Categoria (1) ↔ (N) Item
- Item (1) ↔ (N) Movimentacao

---

## 2. Endpoints REST

### 2.1 Categorias
```
GET    /api/categorias              - Listar todas
GET    /api/categorias/{id}         - Buscar por ID
POST   /api/categorias              - Criar nova
PUT    /api/categorias/{id}         - Atualizar
DELETE /api/categorias/{id}         - Desativar
```

### 2.2 Itens
```
GET    /api/itens                   - Listar todos (com filtros)
GET    /api/itens/{id}              - Buscar por ID
GET    /api/itens/codigo/{codigo}   - Buscar por código
GET    /api/itens/categoria/{catId} - Listar por categoria
GET    /api/itens/estoque-baixo     - Itens abaixo do mínimo
POST   /api/itens                   - Criar novo item
PUT    /api/itens/{id}              - Atualizar
DELETE /api/itens/{id}              - Desativar
```

### 2.3 Movimentações
```
GET    /api/movimentacoes           - Listar todas (com filtros)
GET    /api/movimentacoes/{id}      - Buscar por ID
GET    /api/movimentacoes/item/{itemId} - Histórico de um item
POST   /api/movimentacoes/entrada   - Registrar entrada
POST   /api/movimentacoes/saida     - Registrar saída
```

---

## 3. Arquitetura do Projeto

### Estrutura de Pastas
```
src/main/java/com/oficina/inventario/
├── config/              # Configurações (CORS, etc)
├── controller/          # REST Controllers
├── dto/                 # Data Transfer Objects
├── entity/              # Entidades JPA
├── enums/               # Enumerações
├── exception/           # Tratamento de exceções
├── repository/          # Repositories JPA
├── service/             # Lógica de negócio
└── InventarioApplication.java
```

### Camadas
1. **Controller** - Recebe requisições HTTP, valida DTOs, retorna responses
2. **Service** - Lógica de negócio, validações, orquestração
3. **Repository** - Acesso ao banco de dados (Spring Data JPA)
4. **Entity** - Mapeamento JPA das tabelas

---

## 4. Tecnologias Utilizadas

### Core
- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- Spring Web
- Spring Validation

### Banco de Dados
- PostgreSQL (produção)
- H2 Database (desenvolvimento/testes)

### Utilitários
- Lombok - Redução de boilerplate
- MapStruct (opcional) - Mapeamento Entity ↔ DTO

---

## 5. Regras de Negócio

### Itens
- Código do item deve ser único
- Quantidade não pode ser negativa
- Ao criar item, quantidade inicial = 0
- Não permitir exclusão física, apenas desativação

### Movimentações
- ENTRADA: soma quantidade ao estoque
- SAIDA: subtrai quantidade do estoque
- Não permitir saída maior que estoque disponível
- Sempre registrar quantidade antes/depois
- Data da movimentação é gerada automaticamente

### Categorias
- Nome deve ser único
- Não permitir desativar categoria com itens ativos

---

## 6. Validações

### Item
- `codigo`: obrigatório, único, 3-20 caracteres
- `nome`: obrigatório, 3-100 caracteres
- `unidadeMedida`: obrigatório
- `quantidadeMinima`: >= 0
- `categoria_id`: obrigatório, deve existir

### Movimentação
- `quantidade`: > 0
- `item_id`: obrigatório, deve existir
- `responsavel`: obrigatório

---

## 7. Evolução Futura (Fora do MVP)

### Fase 2
- Autenticação e autorização (Spring Security + JWT)
- Auditoria completa (quem criou, quem modificou)
- Alertas automáticos de estoque baixo
- Dashboard com estatísticas

### Fase 3
- Fornecedores e compras
- Integração com código de barras
- Relatórios exportáveis (PDF, Excel)
- Histórico completo de alterações

### Fase 4
- Ordem de serviço
- Controle de custos
- Integração com clientes
- App mobile

---

## 8. Boas Práticas Implementadas

- ✅ DTOs para separar camada de apresentação
- ✅ Validações com Bean Validation
- ✅ Tratamento centralizado de exceções
- ✅ Soft delete (desativação ao invés de exclusão)
- ✅ Timestamps automáticos (created/updated)
- ✅ Código limpo e bem organizado
- ✅ Nomenclatura em português (contexto do negócio)
- ✅ REST API seguindo convenções
