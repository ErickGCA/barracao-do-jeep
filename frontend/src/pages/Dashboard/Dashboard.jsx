import React, { useState, useEffect } from 'react';
import { Row, Col, Table, Tag, Alert, Spin } from 'antd';
import {
  AppstoreOutlined,
  InboxOutlined,
  WarningOutlined,
  SwapOutlined,
} from '@ant-design/icons';
import StatCard from '../../components/molecules/StatCard';
import Card from '../../components/atoms/Card';
import { categoriasAPI } from '../../api/categorias';
import { itensAPI } from '../../api/itens';
import { movimentacoesAPI } from '../../api/movimentacoes';
import './Dashboard.css';

const Dashboard = () => {
  const [loading, setLoading] = useState(true);
  const [stats, setStats] = useState({
    totalCategorias: 0,
    totalItens: 0,
    itensEstoqueBaixo: 0,
    ultimasMovimentacoes: []
  });
  const [itensEstoqueBaixo, setItensEstoqueBaixo] = useState([]);

  useEffect(() => {
    carregarDados();
  }, []);

  const carregarDados = async () => {
    try {
      setLoading(true);
      const [categoriasRes, itensRes, estoqueBaixoRes, movimentacoesRes] = await Promise.all([
        categoriasAPI.listarTodas(true),
        itensAPI.listarTodos(true),
        itensAPI.listarEstoqueBaixo(),
        movimentacoesAPI.listarTodas()
      ]);

      setStats({
        totalCategorias: categoriasRes.data.length,
        totalItens: itensRes.data.length,
        itensEstoqueBaixo: estoqueBaixoRes.data.length,
        ultimasMovimentacoes: movimentacoesRes.data.slice(0, 5)
      });

      setItensEstoqueBaixo(estoqueBaixoRes.data);
    } catch (error) {
      console.error('Erro ao carregar dados:', error);
    } finally {
      setLoading(false);
    }
  };

  const colunas = [
    {
      title: 'Código',
      dataIndex: 'codigo',
      key: 'codigo',
      render: (text) => <strong>{text}</strong>
    },
    {
      title: 'Nome',
      dataIndex: 'nome',
      key: 'nome',
    },
    {
      title: 'Qtd Atual',
      dataIndex: 'quantidadeAtual',
      key: 'quantidadeAtual',
      render: (qtd) => <Tag color="error">{qtd}</Tag>
    },
    {
      title: 'Qtd Mínima',
      dataIndex: 'quantidadeMinima',
      key: 'quantidadeMinima',
      render: (qtd) => <Tag color="warning">{qtd}</Tag>
    },
    {
      title: 'Unidade',
      dataIndex: 'unidadeMedida',
      key: 'unidadeMedida',
    },
  ];

  if (loading) {
    return (
      <div className="dashboard-loading">
        <Spin size="large" tip="Carregando dashboard..." />
      </div>
    );
  }

  return (
    <div className="dashboard">
      <div className="dashboard-header">
        <h1 className="dashboard-title">Dashboard</h1>
        <p className="dashboard-subtitle">Visão geral do inventário</p>
      </div>

      <Row gutter={[24, 24]} className="dashboard-stats">
        <Col xs={24} sm={12} lg={6}>
          <StatCard
            title="Total de Categorias"
            value={stats.totalCategorias}
            icon={<AppstoreOutlined />}
            color="primary"
            loading={loading}
          />
        </Col>
        <Col xs={24} sm={12} lg={6}>
          <StatCard
            title="Total de Itens"
            value={stats.totalItens}
            icon={<InboxOutlined />}
            color="success"
            loading={loading}
          />
        </Col>
        <Col xs={24} sm={12} lg={6}>
          <StatCard
            title="Estoque Baixo"
            value={stats.itensEstoqueBaixo}
            icon={<WarningOutlined />}
            color={stats.itensEstoqueBaixo > 0 ? 'warning' : 'success'}
            loading={loading}
          />
        </Col>
        <Col xs={24} sm={12} lg={6}>
          <StatCard
            title="Movimentações"
            value={stats.ultimasMovimentacoes.length}
            icon={<SwapOutlined />}
            color="cyan"
            loading={loading}
          />
        </Col>
      </Row>

      {stats.itensEstoqueBaixo > 0 && (
        <Alert
          message="Atenção: Itens com estoque baixo!"
          description={`Existem ${stats.itensEstoqueBaixo} ${stats.itensEstoqueBaixo === 1 ? 'item' : 'itens'} com estoque abaixo do mínimo. Verifique a lista abaixo.`}
          type="warning"
          showIcon
          closable
          className="dashboard-alert"
        />
      )}

      <Card
        title="Itens com Estoque Baixo"
        className="dashboard-table-card"
        hoverable={false}
      >
        <Table
          dataSource={itensEstoqueBaixo}
          columns={colunas}
          rowKey="id"
          pagination={{
            pageSize: 10,
            showSizeChanger: true,
            showTotal: (total) => `Total: ${total} itens`,
          }}
          locale={{ emptyText: 'Nenhum item com estoque baixo' }}
          className="dashboard-table"
        />
      </Card>
    </div>
  );
};

export default Dashboard;
