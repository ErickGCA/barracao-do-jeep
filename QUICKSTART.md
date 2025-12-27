# Guia Rápido de Início

Este guia mostra como colocar o sistema rodando em 5 minutos.

## Pré-requisitos

- Java 17+ instalado
- Maven instalado

## Passos

### 1. Executar com Banco H2 (Em Memória)

A forma mais rápida de testar o sistema:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Pronto! O sistema estará rodando em `http://localhost:8080`

### 2. Acessar o Console H2 (Opcional)

Para visualizar o banco de dados:

- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:inventario_dev`
- Username: `sa`
- Password: (deixe em branco)

### 3. Testar a API

O sistema já vem com dados de exemplo. Teste com:

```bash
# Listar categorias
curl http://localhost:8080/api/categorias

# Listar itens
curl http://localhost:8080/api/itens
```

Ou use o arquivo `http-requests.http` com o REST Client do VS Code.

### 4. Exemplos Rápidos

#### Criar uma categoria:
```bash
curl -X POST http://localhost:8080/api/categorias \
  -H "Content-Type: application/json" \
  -d '{"nome":"Ferramentas","descricao":"Ferramentas diversas"}'
```

#### Criar um item:
```bash
curl -X POST http://localhost:8080/api/itens \
  -H "Content-Type: application/json" \
  -d '{
    "codigo":"TESTE-001",
    "nome":"Item Teste",
    "unidadeMedida":"UN",
    "quantidadeMinima":10,
    "categoriaId":1
  }'
```

#### Registrar entrada de estoque:
```bash
curl -X POST http://localhost:8080/api/movimentacoes/entrada \
  -H "Content-Type: application/json" \
  -d '{
    "itemId":1,
    "quantidade":100,
    "observacao":"Compra inicial",
    "responsavel":"Administrador"
  }'
```

## Próximos Passos

- Consulte o `README.md` para documentação completa
- Veja o `TECHNICAL_DESIGN.md` para detalhes técnicos
- Use o `http-requests.http` para testar todos os endpoints

## Trocar para PostgreSQL

1. Instalar PostgreSQL
2. Criar banco: `CREATE DATABASE inventario_oficina;`
3. Editar `application.properties` com suas credenciais
4. Executar sem o profile dev: `mvn spring-boot:run`

## Dúvidas?

Consulte a documentação completa no `README.md`
