import React, { useState, useEffect } from 'react';
import { Table, Modal, Form, Input, InputNumber, Select, Space, Popconfirm, message, Tag, Spin, Row, Col } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined, HistoryOutlined, InboxOutlined } from '@ant-design/icons';
import { itensAPI } from '../../api/itens';
import { categoriasAPI } from '../../api/categorias';
import { useNavigate } from 'react-router-dom';
import Card from '../../components/atoms/Card';
import Button from '../../components/atoms/Button';
import useModal from '../../hooks/useModal';
import './Itens.css';

const Itens = () => {
  const [itens, setItens] = useState([]);
  const [categorias, setCategorias] = useState([]);
  const [loading, setLoading] = useState(false);
  const modal = useModal();
  const [form] = Form.useForm();
  const navigate = useNavigate();

  useEffect(() => {
    carregarItens();
    carregarCategorias();
  }, []);

  const carregarItens = async () => {
    try {
      setLoading(true);
      const response = await itensAPI.listarTodos();
      setItens(response.data);
    } catch (error) {
      message.error('Erro ao carregar itens');
    } finally {
      setLoading(false);
    }
  };

  const carregarCategorias = async () => {
    try {
      const response = await categoriasAPI.listarTodas(true);
      setCategorias(response.data);
    } catch (error) {
      message.error('Erro ao carregar categorias');
    }
  };

  const abrirModal = (item = null) => {
    if (item) {
      form.setFieldsValue({
        ...item,
        categoriaId: item.categoria.id
      });
    } else {
      form.resetFields();
    }
    modal.open(item);
  };

  const fecharModal = () => {
    form.resetFields();
    modal.close();
  };

  const salvar = async (values) => {
    try {
      if (modal.data) {
        await itensAPI.atualizar(modal.data.id, values);
        message.success('Item atualizado com sucesso!');
      } else {
        await itensAPI.criar(values);
        message.success('Item criado com sucesso!');
      }
      fecharModal();
      carregarItens();
    } catch (error) {
      message.error(error.response?.data?.message || 'Erro ao salvar item');
    }
  };

  const desativar = async (id) => {
    try {
      await itensAPI.desativar(id);
      message.success('Item desativado com sucesso!');
      carregarItens();
    } catch (error) {
      message.error('Erro ao desativar item');
    }
  };

  const colunas = [
    {
      title: 'Código',
      dataIndex: 'codigo',
      key: 'codigo',
      width: 120,
      render: (text) => <strong>{text}</strong>,
    },
    {
      title: 'Nome',
      dataIndex: 'nome',
      key: 'nome',
    },
    {
      title: 'Categoria',
      dataIndex: ['categoria', 'nome'],
      key: 'categoria',
      width: 150,
      render: (text) => <Tag color="blue">{text}</Tag>,
    },
    {
      title: 'Qtd Atual',
      dataIndex: 'quantidadeAtual',
      key: 'quantidadeAtual',
      width: 110,
      render: (qtd, record) => {
        const cor = qtd <= record.quantidadeMinima ? 'error' : 'success';
        return <Tag color={cor}>{qtd}</Tag>;
      }
    },
    {
      title: 'Qtd Mín',
      dataIndex: 'quantidadeMinima',
      key: 'quantidadeMinima',
      width: 100,
      render: (text) => <Tag color="warning">{text}</Tag>,
    },
    {
      title: 'Unidade',
      dataIndex: 'unidadeMedida',
      key: 'unidadeMedida',
      width: 80,
    },
    {
      title: 'Status',
      dataIndex: 'ativo',
      key: 'ativo',
      width: 100,
      render: (ativo) => (
        <Tag color={ativo ? 'success' : 'default'}>
          {ativo ? 'Ativo' : 'Inativo'}
        </Tag>
      ),
    },
    {
      title: 'Ações',
      key: 'acoes',
      width: 220,
      fixed: 'right',
      render: (_, record) => (
        <Space size="small">
          <Button
            variant="link"
            size="small"
            icon={<HistoryOutlined />}
            onClick={() => navigate(`/movimentacoes?itemId=${record.id}`)}
          >
            Histórico
          </Button>
          <Button
            variant="link"
            size="small"
            icon={<EditOutlined />}
            onClick={() => abrirModal(record)}
          >
            Editar
          </Button>
          {record.ativo && (
            <Popconfirm
              title="Tem certeza que deseja desativar?"
              onConfirm={() => desativar(record.id)}
              okText="Sim"
              cancelText="Não"
            >
              <Button variant="danger" size="small" icon={<DeleteOutlined />}>
                Desativar
              </Button>
            </Popconfirm>
          )}
        </Space>
      ),
    },
  ];

  if (loading && itens.length === 0) {
    return (
      <div className="itens-loading">
        <Spin size="large" tip="Carregando itens..." />
      </div>
    );
  }

  return (
    <div className="itens">
      <div className="itens-header">
        <div>
          <h1 className="itens-title">
            <InboxOutlined /> Itens
          </h1>
          <p className="itens-subtitle">
            Gerencie os itens do inventário
          </p>
        </div>
        <Button
          variant="primary"
          icon={<PlusOutlined />}
          onClick={() => abrirModal()}
        >
          Novo Item
        </Button>
      </div>

      <Card className="itens-card">
        <Table
          dataSource={itens}
          columns={colunas}
          rowKey="id"
          loading={loading}
          scroll={{ x: 1200 }}
          pagination={{
            pageSize: 10,
            showSizeChanger: true,
            showTotal: (total) => `Total: ${total} itens`,
          }}
          locale={{ emptyText: 'Nenhum item cadastrado' }}
          className="itens-table"
        />
      </Card>

      <Modal
        title={modal.data ? 'Editar Item' : 'Novo Item'}
        open={modal.isOpen}
        onCancel={fecharModal}
        footer={null}
        width={700}
        className="itens-modal"
      >
        <Form form={form} layout="vertical" onFinish={salvar}>
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                label="Código"
                name="codigo"
                rules={[
                  { required: true, message: 'Código é obrigatório' },
                  { min: 3, max: 20, message: 'Entre 3 e 20 caracteres' }
                ]}
              >
                <Input size="large" placeholder="Ex: FERR-001" />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                label="Unidade de Medida"
                name="unidadeMedida"
                rules={[{ required: true, message: 'Unidade é obrigatória' }]}
              >
                <Select size="large" placeholder="Selecione">
                  <Select.Option value="UN">Unidade (UN)</Select.Option>
                  <Select.Option value="KG">Quilograma (KG)</Select.Option>
                  <Select.Option value="L">Litro (L)</Select.Option>
                  <Select.Option value="M">Metro (M)</Select.Option>
                  <Select.Option value="PC">Peça (PC)</Select.Option>
                </Select>
              </Form.Item>
            </Col>
          </Row>

          <Form.Item
            label="Nome"
            name="nome"
            rules={[
              { required: true, message: 'Nome é obrigatório' },
              { min: 3, max: 100, message: 'Entre 3 e 100 caracteres' }
            ]}
          >
            <Input size="large" placeholder="Ex: Chave de Fenda 3/16" />
          </Form.Item>

          <Form.Item
            label="Descrição"
            name="descricao"
            rules={[{ max: 500, message: 'Máximo 500 caracteres' }]}
          >
            <Input.TextArea rows={3} size="large" placeholder="Descreva o item..." />
          </Form.Item>

          <Row gutter={16}>
            <Col span={8}>
              <Form.Item
                label="Quantidade Mínima"
                name="quantidadeMinima"
                rules={[{ required: true, message: 'Quantidade mínima é obrigatória' }]}
                initialValue={0}
              >
                <InputNumber min={0} size="large" style={{ width: '100%' }} />
              </Form.Item>
            </Col>
            <Col span={16}>
              <Form.Item
                label="Localização"
                name="localizacao"
                rules={[{ max: 200, message: 'Máximo 200 caracteres' }]}
              >
                <Input size="large" placeholder="Ex: Prateleira A3" />
              </Form.Item>
            </Col>
          </Row>

          <Form.Item
            label="Categoria"
            name="categoriaId"
            rules={[{ required: true, message: 'Categoria é obrigatória' }]}
          >
            <Select size="large" placeholder="Selecione uma categoria">
              {categorias.map(cat => (
                <Select.Option key={cat.id} value={cat.id}>
                  {cat.nome}
                </Select.Option>
              ))}
            </Select>
          </Form.Item>

          <Form.Item className="form-actions">
            <Space size="middle">
              <Button variant="primary" htmlType="submit" size="large">
                Salvar
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

export default Itens;
