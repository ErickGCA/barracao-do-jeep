# Front-end - Sistema de Inventario

Interface web desenvolvida com React + Ant Design para gerenciamento de inventario de oficina.

## Tecnologias Utilizadas

- **React** 18.2
- **Vite** 5.0 (Build tool)
- **Ant Design** 5.12 (Componentes UI)
- **React Router** 6.20 (Navegacao)
- **Axios** 1.6 (HTTP client)
- **Day.js** (Manipulacao de datas)

## Funcionalidades Implementadas

### Dashboard
- Cards com estatisticas gerais
- Alertas de estoque baixo
- Listagem de itens criticos

### Categorias
- Listagem completa
- Criar nova categoria
- Editar categoria
- Desativar categoria
- Indicador de status (Ativo/Inativo)

### Itens
- Listagem completa com filtros
- Criar novo item
- Editar item
- Desativar item
- Visualizar historico de movimentacoes
- Alerta visual para estoque baixo
- Indicador de status

### Movimentacoes
- Registrar entrada de estoque
- Registrar saida de estoque
- Historico completo de movimentacoes
- Filtros por tipo (Entrada/Saida)
- Validacao de estoque disponivel
- Rastreamento de responsavel

## Instalacao

### Pre-requisitos
- Node.js 18+ instalado
- Back-end rodando em http://localhost:8080

### Passos

1. Entre na pasta do frontend:
```bash
cd frontend
```

2. Instale as dependencias:
```bash
npm install
```

3. Inicie o servidor de desenvolvimento:
```bash
npm run dev
```

4. Acesse no navegador:
```
http://localhost:3000
```

## Scripts Disponiveis

```bash
# Desenvolvimento
npm run dev

# Build para producao
npm run build

# Preview da build de producao
npm run preview
```

## Estrutura do Projeto

```
frontend/
├── public/
├── src/
│   ├── api/                 # Servicos de API
│   │   ├── axios.js        # Configuracao do Axios
│   │   ├── categorias.js   # API de Categorias
│   │   ├── itens.js        # API de Itens
│   │   └── movimentacoes.js# API de Movimentacoes
│   ├── components/          # Componentes reutilizaveis
│   │   └── Layout/
│   │       └── MainLayout.jsx
│   ├── pages/               # Paginas da aplicacao
│   │   ├── Dashboard/
│   │   ├── Categorias/
│   │   ├── Itens/
│   │   └── Movimentacoes/
│   ├── App.jsx             # Componente raiz
│   └── main.jsx            # Entry point
├── index.html
├── package.json
└── vite.config.js
```

## Integracao com Back-end

O front-end esta configurado para se comunicar com o back-end em `http://localhost:8080/api`.

Todos os endpoints estao integrados:

### Categorias
- `GET /api/categorias` - Listar
- `POST /api/categorias` - Criar
- `PUT /api/categorias/{id}` - Atualizar
- `DELETE /api/categorias/{id}` - Desativar

### Itens
- `GET /api/itens` - Listar
- `GET /api/itens/estoque-baixo` - Estoque baixo
- `POST /api/itens` - Criar
- `PUT /api/itens/{id}` - Atualizar
- `DELETE /api/itens/{id}` - Desativar

### Movimentacoes
- `GET /api/movimentacoes` - Listar
- `POST /api/movimentacoes/entrada` - Registrar entrada
- `POST /api/movimentacoes/saida` - Registrar saida

## Customizacao

### Alterar porta do servidor
Edite `vite.config.js`:
```js
export default defineConfig({
  server: {
    port: 3000 // Altere aqui
  }
})
```

### Alterar URL do back-end
Edite `src/api/axios.js`:
```js
const api = axios.create({
  baseURL: 'http://localhost:8080/api' // Altere aqui
});
```

## Componentes Ant Design Utilizados

- Table - Tabelas com paginacao e ordenacao
- Form - Formularios com validacao
- Modal - Dialogos
- Button - Botoes
- Input - Campos de texto
- Select - Campos de selecao
- InputNumber - Campos numericos
- Tag - Etiquetas de status
- Card - Cards informativos
- Statistic - Estatisticas
- Space - Espacamento
- Popconfirm - Confirmacoes
- Message - Notificacoes
- Tabs - Abas

## Proximos Passos (Futuro)

- [ ] Graficos de movimentacoes
- [ ] Exportacao de relatorios
- [ ] Filtros avancados
- [ ] Busca global
- [ ] Tema escuro
- [ ] Responsividade mobile
- [ ] Autenticacao de usuarios
- [ ] Permissoes de acesso
