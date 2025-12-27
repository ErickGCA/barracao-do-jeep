# Guia Rapido - Front-end

## Como executar

### 1. Certifique-se que o back-end esta rodando

```bash
# No diretorio raiz do projeto
mvn spring-boot:run
```

O back-end deve estar rodando em: http://localhost:8080

### 2. Instale as dependencias do front-end

```bash
cd frontend
npm install
```

### 3. Inicie o front-end

```bash
npm run dev
```

### 4. Acesse no navegador

```
http://localhost:3000
```

## O que voce vai ver

### Dashboard (/)
- Estatisticas gerais do inventario
- Total de categorias
- Total de itens
- Itens com estoque baixo
- Alertas importantes

### Categorias (/categorias)
- Tabela com todas as categorias
- Botao "Nova Categoria" para adicionar
- Acoes: Editar e Desativar
- Status visual (Ativo/Inativo)

### Itens (/itens)
- Tabela completa de itens
- Informacoes: Codigo, Nome, Categoria, Estoque, etc
- Botao "Novo Item" para adicionar
- Acoes: Historico, Editar e Desativar
- Indicador visual de estoque baixo (vermelho)

### Movimentacoes (/movimentacoes)
- Botoes "Nova Entrada" e "Nova Saida"
- Tabs: Todas, Apenas Entradas, Apenas Saidas
- Historico completo de todas as movimentacoes
- Data/hora, tipo, item, quantidades e responsavel

## Fluxo de Uso Basico

1. **Criar Categorias**
   - Acesse "Categorias"
   - Clique em "Nova Categoria"
   - Preencha nome e descricao
   - Salve

2. **Criar Itens**
   - Acesse "Itens"
   - Clique em "Novo Item"
   - Preencha todos os campos
   - Selecione a categoria
   - Salve

3. **Registrar Entrada de Estoque**
   - Acesse "Movimentacoes"
   - Clique em "Nova Entrada"
   - Selecione o item
   - Informe quantidade
   - Adicione observacao e responsavel
   - Registre

4. **Registrar Saida de Estoque**
   - Acesse "Movimentacoes"
   - Clique em "Nova Saida"
   - Selecione o item
   - Informe quantidade (sera validado com estoque disponivel)
   - Adicione observacao e responsavel
   - Registre

5. **Acompanhar Estoque**
   - Dashboard mostra itens com estoque baixo
   - Itens em vermelho na listagem = abaixo do minimo
   - Clique em "Historico" em qualquer item para ver movimentacoes

## Recursos Implementados

### Validacoes
- Formularios com validacao em tempo real
- Campos obrigatorios marcados
- Limites de caracteres
- Validacao de quantidade disponivel nas saidas

### Feedback Visual
- Mensagens de sucesso/erro
- Confirmacoes antes de desativar
- Loading states
- Cores semanticas (verde=ok, vermelho=alerta)

### Navegacao
- Menu lateral colapsavel
- Navegacao rapida entre paginas
- Breadcrumbs visuais

### Tabelas
- Ordenacao por colunas
- Paginacao automatica
- Scroll horizontal em telas pequenas

## Troubleshooting

### Erro de conexao com back-end
- Verifique se o back-end esta rodando
- Verifique a porta (deve ser 8080)
- Veja o console do navegador (F12)

### Pagina em branco
- Abra o console do navegador (F12)
- Verifique erros de JavaScript
- Certifique-se que executou `npm install`

### Mudancas nao aparecem
- Pare o servidor (Ctrl+C)
- Execute `npm run dev` novamente
- Limpe o cache do navegador (Ctrl+Shift+R)

## Proximos Passos

Aproveite o sistema! Esta tudo integrado e funcionando.

Se precisar adicionar funcionalidades, consulte a documentacao do Ant Design:
https://ant.design/components/overview/
