import React, { useState, useEffect } from 'react';
import { Card, Row, Col, Statistic, Table, Tag, Alert } from 'antd';
import {
  AppstoreOutlined,
  InboxOutlined,
  WarningOutlined,
  ArrowUpOutlined,
  ArrowDownOutlined
} from '@ant-design/icons';
import { categoriasAPI } from '../../api/categorias';
import { itensAPI } from '../../api/itens';
import { movimentacoesAPI } from '../../api/movimentacoes';

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
      title: 'Codigo',
      dataIndex: 'codigo',
      key: 'codigo',
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
      title: 'Qtd Minima',
      dataIndex: 'quantidadeMinima',
      key: 'quantidadeMinima',
    },
  ];

  return (
    <div>
      <h1>Dashboard</h1>

      <Row gutter={16} style={{ marginBottom: 24 }}>
        <Col span={6}>
          <Card loading={loading}>
            <Statistic
              title="Total de Categorias"
              value={stats.totalCategorias}
              prefix={<AppstoreOutlined />}
            />
          </Card>
        </Col>
        <Col span={6}>
          <Card loading={loading}>
            <Statistic
              title="Total de Itens"
              value={stats.totalItens}
              prefix={<InboxOutlined />}
            />
          </Card>
        </Col>
        <Col span={6}>
          <Card loading={loading}>
            <Statistic
              title="Itens com Estoque Baixo"
              value={stats.itensEstoqueBaixo}
              prefix={<WarningOutlined />}
              valueStyle={{ color: stats.itensEstoqueBaixo > 0 ? '#cf1322' : '#3f8600' }}
            />
          </Card>
        </Col>
        <Col span={6}>
          <Card loading={loading}>
            <Statistic
              title="Movimentacoes Recentes"
              value={stats.ultimasMovimentacoes.length}
              prefix={<ArrowUpOutlined />}
            />
          </Card>
        </Col>
      </Row>

      {stats.itensEstoqueBaixo > 0 && (
        <Alert
          message="Atencao!"
          description={`Existem ${stats.itensEstoqueBaixo} itens com estoque abaixo do minimo.`}
          type="warning"
          showIcon
          style={{ marginBottom: 24 }}
        />
      )}

      <Card title="Itens com Estoque Baixo" loading={loading}>
        <Table
          dataSource={itensEstoqueBaixo}
          columns={colunas}
          rowKey="id"
          pagination={false}
          locale={{ emptyText: 'Nenhum item com estoque baixo' }}
        />
      </Card>
    </div>
  );
};

export default Dashboard;
