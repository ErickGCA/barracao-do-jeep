import React, { useState, useEffect } from 'react';
import { Table, Button, Modal, Form, InputNumber, Select, Input, Space, message, Tag, Tabs } from 'antd';
import { PlusOutlined, ArrowUpOutlined, ArrowDownOutlined } from '@ant-design/icons';
import { movimentacoesAPI } from '../../api/movimentacoes';
import { itensAPI } from '../../api/itens';
import dayjs from 'dayjs';

const Movimentacoes = () => {
  const [movimentacoes, setMovimentacoes] = useState([]);
  const [itens, setItens] = useState([]);
  const [loading, setLoading] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
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
    setModalVisible(true);
  };

  const fecharModal = () => {
    setModalVisible(false);
    form.resetFields();
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
      render: (data) => dayjs(data).format('DD/MM/YYYY HH:mm'),
    },
    {
      title: 'Tipo',
      dataIndex: 'tipoMovimentacao',
      key: 'tipoMovimentacao',
      width: 100,
      render: (tipo) => (
        <Tag color={tipo === 'ENTRADA' ? 'success' : 'error'}>
          {tipo === 'ENTRADA' ? 'Entrada' : 'Saida'}
        </Tag>
      ),
    },
    {
      title: 'Item',
      dataIndex: ['item', 'nome'],
      key: 'item',
    },
    {
      title: 'Codigo',
      dataIndex: ['item', 'codigo'],
      key: 'codigo',
      width: 120,
    },
    {
      title: 'Quantidade',
      dataIndex: 'quantidade',
      key: 'quantidade',
      width: 100,
    },
    {
      title: 'Qtd Anterior',
      dataIndex: 'quantidadeAnterior',
      key: 'quantidadeAnterior',
      width: 120,
    },
    {
      title: 'Qtd Nova',
      dataIndex: 'quantidadeNova',
      key: 'quantidadeNova',
      width: 100,
    },
    {
      title: 'Responsavel',
      dataIndex: 'responsavel',
      key: 'responsavel',
      width: 150,
    },
    {
      title: 'Observacao',
      dataIndex: 'observacao',
      key: 'observacao',
    },
  ];

  const tabItems = [
    {
      key: '1',
      label: 'Todas as Movimentacoes',
      children: (
        <Table
          dataSource={movimentacoes}
          columns={colunas}
          rowKey="id"
          loading={loading}
          scroll={{ x: 1400 }}
          pagination={{ pageSize: 10 }}
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
          pagination={{ pageSize: 10 }}
        />
      )
    },
    {
      key: '3',
      label: 'Apenas Saidas',
      children: (
        <Table
          dataSource={movimentacoes.filter(m => m.tipoMovimentacao === 'SAIDA')}
          columns={colunas}
          rowKey="id"
          loading={loading}
          scroll={{ x: 1400 }}
          pagination={{ pageSize: 10 }}
        />
      )
    }
  ];

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: 16 }}>
        <h1>Movimentacoes</h1>
        <Space>
          <Button
            type="primary"
            icon={<ArrowUpOutlined />}
            onClick={() => abrirModal('ENTRADA')}
            style={{ backgroundColor: '#52c41a' }}
          >
            Nova Entrada
          </Button>
          <Button
            type="primary"
            danger
            icon={<ArrowDownOutlined />}
            onClick={() => abrirModal('SAIDA')}
          >
            Nova Saida
          </Button>
        </Space>
      </div>

      <Tabs items={tabItems} />

      <Modal
        title={tipoMovimentacao === 'ENTRADA' ? 'Registrar Entrada' : 'Registrar Saida'}
        open={modalVisible}
        onCancel={fecharModal}
        footer={null}
      >
        <Form form={form} layout="vertical" onFinish={salvar}>
          <Form.Item
            label="Item"
            name="itemId"
            rules={[{ required: true, message: 'Item e obrigatorio' }]}
          >
            <Select
              showSearch
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
              { required: true, message: 'Quantidade e obrigatoria' },
              { type: 'number', min: 1, message: 'Quantidade deve ser maior que zero' }
            ]}
          >
            <InputNumber min={1} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item
            label="Observacao"
            name="observacao"
            rules={[{ max: 500, message: 'Maximo 500 caracteres' }]}
          >
            <Input.TextArea rows={3} placeholder="Ex: Compra fornecedor XYZ" />
          </Form.Item>

          <Form.Item
            label="Responsavel"
            name="responsavel"
            rules={[
              { required: true, message: 'Responsavel e obrigatorio' },
              { min: 3, max: 100, message: 'Entre 3 e 100 caracteres' }
            ]}
          >
            <Input placeholder="Ex: Joao Silva" />
          </Form.Item>

          <Form.Item>
            <Space>
              <Button type="primary" htmlType="submit">
                Registrar
              </Button>
              <Button onClick={fecharModal}>
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
