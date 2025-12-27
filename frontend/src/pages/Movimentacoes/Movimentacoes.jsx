import React, { useState, useEffect } from 'react';
import { Table, Modal, Form, InputNumber, Select, Input, Space, message, Tag, Tabs, Spin } from 'antd';
import { ArrowUpOutlined, ArrowDownOutlined, SwapOutlined } from '@ant-design/icons';
import { movimentacoesAPI } from '../../api/movimentacoes';
import { itensAPI } from '../../api/itens';
import Card from '../../components/atoms/Card';
import Button from '../../components/atoms/Button';
import useModal from '../../hooks/useModal';
import dayjs from 'dayjs';
import './Movimentacoes.css';

const Movimentacoes = () => {
  const [movimentacoes, setMovimentacoes] = useState([]);
  const [itens, setItens] = useState([]);
  const [loading, setLoading] = useState(false);
  const modal = useModal();
  const [tipoMovimentacao, setTipoMovimentacao] = useState('ENTRADA');
  const [form] = Form.useForm();

  useEffect(() => {
    carregarMovimentacoes();
    carregarItens();
  }, []);

  const carregarMovimentacoes = async () => {
    try {
      setLoading(true);
      const response = await movimentacoesAPI.listarTodas();
      setMovimentacoes(response.data);
    } catch (error) {
      message.error('Erro ao carregar movimentacoes');
    } finally {
      setLoading(false);
    }
  };

  const carregarItens = async () => {
    try {
      const response = await itensAPI.listarTodos(true);
      setItens(response.data);
    } catch (error) {
      message.error('Erro ao carregar itens');
    }
  };

  const abrirModal = (tipo) => {
    setTipoMovimentacao(tipo);
    form.resetFields();
    modal.open(tipo);
  };

  const fecharModal = () => {
    form.resetFields();
    modal.close();
  };

  const salvar = async (values) => {
    try {
      if (tipoMovimentacao === 'ENTRADA') {
        await movimentacoesAPI.registrarEntrada(values);
        message.success('Entrada registrada com sucesso!');
      } else {
        await movimentacoesAPI.registrarSaida(values);
        message.success('Saida registrada com sucesso!');
      }
      fecharModal();
      carregarMovimentacoes();
    } catch (error) {
      message.error(error.response?.data?.message || 'Erro ao registrar movimentacao');
    }
  };

  const colunas = [
    {
      title: 'Data/Hora',
      dataIndex: 'dataMovimentacao',
      key: 'dataMovimentacao',
      width: 180,
      render: (data) => <strong>{dayjs(data).format('DD/MM/YYYY HH:mm')}</strong>,
    },
    {
      title: 'Tipo',
      dataIndex: 'tipoMovimentacao',
      key: 'tipoMovimentacao',
      width: 110,
      render: (tipo) => (
        <Tag color={tipo === 'ENTRADA' ? 'success' : 'error'} icon={tipo === 'ENTRADA' ? <ArrowUpOutlined /> : <ArrowDownOutlined />}>
          {tipo === 'ENTRADA' ? 'Entrada' : 'Saída'}
        </Tag>
      ),
    },
    {
      title: 'Item',
      dataIndex: ['item', 'nome'],
      key: 'item',
    },
    {
      title: 'Código',
      dataIndex: ['item', 'codigo'],
      key: 'codigo',
      width: 120,
      render: (text) => <Tag color="blue">{text}</Tag>,
    },
    {
      title: 'Quantidade',
      dataIndex: 'quantidade',
      key: 'quantidade',
      width: 110,
      render: (text, record) => (
        <Tag color={record.tipoMovimentacao === 'ENTRADA' ? 'success' : 'error'}>
          {record.tipoMovimentacao === 'ENTRADA' ? '+' : '-'}{text}
        </Tag>
      ),
    },
    {
      title: 'Qtd Anterior',
      dataIndex: 'quantidadeAnterior',
      key: 'quantidadeAnterior',
      width: 120,
      render: (text) => <Tag>{text}</Tag>,
    },
    {
      title: 'Qtd Nova',
      dataIndex: 'quantidadeNova',
      key: 'quantidadeNova',
      width: 110,
      render: (text) => <Tag color="cyan">{text}</Tag>,
    },
    {
      title: 'Responsável',
      dataIndex: 'responsavel',
      key: 'responsavel',
      width: 150,
    },
    {
      title: 'Observação',
      dataIndex: 'observacao',
      key: 'observacao',
    },
  ];

  const tabItems = [
    {
      key: '1',
      label: 'Todas as Movimentações',
      children: (
        <Table
          dataSource={movimentacoes}
          columns={colunas}
          rowKey="id"
          loading={loading}
          scroll={{ x: 1400 }}
          pagination={{
            pageSize: 10,
            showSizeChanger: true,
            showTotal: (total) => `Total: ${total} movimentações`,
          }}
          locale={{ emptyText: 'Nenhuma movimentação registrada' }}
          className="movimentacoes-table"
        />
      )
    },
    {
      key: '2',
      label: 'Apenas Entradas',
      children: (
        <Table
          dataSource={movimentacoes.filter(m => m.tipoMovimentacao === 'ENTRADA')}
          columns={colunas}
          rowKey="id"
          loading={loading}
          scroll={{ x: 1400 }}
          pagination={{
            pageSize: 10,
            showSizeChanger: true,
            showTotal: (total) => `Total: ${total} entradas`,
          }}
          locale={{ emptyText: 'Nenhuma entrada registrada' }}
          className="movimentacoes-table"
        />
      )
    },
    {
      key: '3',
      label: 'Apenas Saídas',
      children: (
        <Table
          dataSource={movimentacoes.filter(m => m.tipoMovimentacao === 'SAIDA')}
          columns={colunas}
          rowKey="id"
          loading={loading}
          scroll={{ x: 1400 }}
          pagination={{
            pageSize: 10,
            showSizeChanger: true,
            showTotal: (total) => `Total: ${total} saídas`,
          }}
          locale={{ emptyText: 'Nenhuma saída registrada' }}
          className="movimentacoes-table"
        />
      )
    }
  ];

  if (loading && movimentacoes.length === 0) {
    return (
      <div className="movimentacoes-loading">
        <Spin size="large" tip="Carregando movimentações..." />
      </div>
    );
  }

  return (
    <div className="movimentacoes">
      <div className="movimentacoes-header">
        <div>
          <h1 className="movimentacoes-title">
            <SwapOutlined /> Movimentações
          </h1>
          <p className="movimentacoes-subtitle">
            Registre e acompanhe entradas e saídas do estoque
          </p>
        </div>
        <Space size="middle">
          <Button
            variant="success"
            icon={<ArrowUpOutlined />}
            onClick={() => abrirModal('ENTRADA')}
          >
            Nova Entrada
          </Button>
          <Button
            variant="danger"
            icon={<ArrowDownOutlined />}
            onClick={() => abrirModal('SAIDA')}
          >
            Nova Saída
          </Button>
        </Space>
      </div>

      <Card className="movimentacoes-card">
        <Tabs items={tabItems} className="movimentacoes-tabs" />
      </Card>

      <Modal
        title={tipoMovimentacao === 'ENTRADA' ? 'Registrar Entrada' : 'Registrar Saída'}
        open={modal.isOpen}
        onCancel={fecharModal}
        footer={null}
        width={600}
        className="movimentacoes-modal"
      >
        <Form form={form} layout="vertical" onFinish={salvar}>
          <Form.Item
            label="Item"
            name="itemId"
            rules={[{ required: true, message: 'Item é obrigatório' }]}
          >
            <Select
              showSearch
              size="large"
              placeholder="Selecione um item"
              optionFilterProp="children"
              filterOption={(input, option) =>
                option.children.toLowerCase().includes(input.toLowerCase())
              }
            >
              {itens.map(item => (
                <Select.Option key={item.id} value={item.id}>
                  {item.codigo} - {item.nome} (Estoque: {item.quantidadeAtual})
                </Select.Option>
              ))}
            </Select>
          </Form.Item>

          <Form.Item
            label="Quantidade"
            name="quantidade"
            rules={[
              { required: true, message: 'Quantidade é obrigatória' },
              { type: 'number', min: 1, message: 'Quantidade deve ser maior que zero' }
            ]}
          >
            <InputNumber min={1} size="large" style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item
            label="Responsável"
            name="responsavel"
            rules={[
              { required: true, message: 'Responsável é obrigatório' },
              { min: 3, max: 100, message: 'Entre 3 e 100 caracteres' }
            ]}
          >
            <Input size="large" placeholder="Ex: João Silva" />
          </Form.Item>

          <Form.Item
            label="Observação"
            name="observacao"
            rules={[{ max: 500, message: 'Máximo 500 caracteres' }]}
          >
            <Input.TextArea rows={3} size="large" placeholder="Ex: Compra fornecedor XYZ" />
          </Form.Item>

          <Form.Item className="form-actions">
            <Space size="middle">
              <Button variant="primary" htmlType="submit" size="large">
                Registrar
              </Button>
              <Button variant="secondary" onClick={fecharModal} size="large">
                Cancelar
              </Button>
            </Space>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default Movimentacoes;
