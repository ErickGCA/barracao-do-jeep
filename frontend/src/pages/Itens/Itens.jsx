import React, { useState, useEffect } from 'react';
import { Table, Button, Modal, Form, Input, InputNumber, Select, Space, Popconfirm, message, Tag } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined, HistoryOutlined } from '@ant-design/icons';
import { itensAPI } from '../../api/itens';
import { categoriasAPI } from '../../api/categorias';
import { useNavigate } from 'react-router-dom';

const Itens = () => {
  const [itens, setItens] = useState([]);
  const [categorias, setCategorias] = useState([]);
  const [loading, setLoading] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
  const [editando, setEditando] = useState(null);
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
    setEditando(item);
    if (item) {
      form.setFieldsValue({
        ...item,
        categoriaId: item.categoria.id
      });
    } else {
      form.resetFields();
    }
    setModalVisible(true);
  };

  const fecharModal = () => {
    setModalVisible(false);
    setEditando(null);
    form.resetFields();
  };

  const salvar = async (values) => {
    try {
      if (editando) {
        await itensAPI.atualizar(editando.id, values);
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
      title: 'Codigo',
      dataIndex: 'codigo',
      key: 'codigo',
      width: 120,
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
    },
    {
      title: 'Qtd Atual',
      dataIndex: 'quantidadeAtual',
      key: 'quantidadeAtual',
      width: 100,
      render: (qtd, record) => {
        const cor = qtd <= record.quantidadeMinima ? 'error' : 'success';
        return <Tag color={cor}>{qtd}</Tag>;
      }
    },
    {
      title: 'Qtd Min',
      dataIndex: 'quantidadeMinima',
      key: 'quantidadeMinima',
      width: 100,
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
      title: 'Acoes',
      key: 'acoes',
      width: 200,
      render: (_, record) => (
        <Space>
          <Button
            type="link"
            size="small"
            icon={<HistoryOutlined />}
            onClick={() => navigate(`/movimentacoes?itemId=${record.id}`)}
          >
            Historico
          </Button>
          <Button
            type="link"
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
              cancelText="Nao"
            >
              <Button type="link" size="small" danger icon={<DeleteOutlined />}>
                Desativar
              </Button>
            </Popconfirm>
          )}
        </Space>
      ),
    },
  ];

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: 16 }}>
        <h1>Itens</h1>
        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={() => abrirModal()}
        >
          Novo Item
        </Button>
      </div>

      <Table
        dataSource={itens}
        columns={colunas}
        rowKey="id"
        loading={loading}
        scroll={{ x: 1200 }}
      />

      <Modal
        title={editando ? 'Editar Item' : 'Novo Item'}
        open={modalVisible}
        onCancel={fecharModal}
        footer={null}
        width={600}
      >
        <Form form={form} layout="vertical" onFinish={salvar}>
          <Form.Item
            label="Codigo"
            name="codigo"
            rules={[
              { required: true, message: 'Codigo e obrigatorio' },
              { min: 3, max: 20, message: 'Entre 3 e 20 caracteres' }
            ]}
          >
            <Input />
          </Form.Item>

          <Form.Item
            label="Nome"
            name="nome"
            rules={[
              { required: true, message: 'Nome e obrigatorio' },
              { min: 3, max: 100, message: 'Entre 3 e 100 caracteres' }
            ]}
          >
            <Input />
          </Form.Item>

          <Form.Item
            label="Descricao"
            name="descricao"
            rules={[{ max: 500, message: 'Maximo 500 caracteres' }]}
          >
            <Input.TextArea rows={3} />
          </Form.Item>

          <Form.Item
            label="Unidade de Medida"
            name="unidadeMedida"
            rules={[{ required: true, message: 'Unidade e obrigatoria' }]}
          >
            <Select>
              <Select.Option value="UN">Unidade (UN)</Select.Option>
              <Select.Option value="KG">Quilograma (KG)</Select.Option>
              <Select.Option value="L">Litro (L)</Select.Option>
              <Select.Option value="M">Metro (M)</Select.Option>
              <Select.Option value="PC">Peca (PC)</Select.Option>
            </Select>
          </Form.Item>

          <Form.Item
            label="Quantidade Minima"
            name="quantidadeMinima"
            rules={[{ required: true, message: 'Quantidade minima e obrigatoria' }]}
            initialValue={0}
          >
            <InputNumber min={0} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item
            label="Localizacao"
            name="localizacao"
            rules={[{ max: 200, message: 'Maximo 200 caracteres' }]}
          >
            <Input placeholder="Ex: Prateleira A3" />
          </Form.Item>

          <Form.Item
            label="Categoria"
            name="categoriaId"
            rules={[{ required: true, message: 'Categoria e obrigatoria' }]}
          >
            <Select placeholder="Selecione uma categoria">
              {categorias.map(cat => (
                <Select.Option key={cat.id} value={cat.id}>
                  {cat.nome}
                </Select.Option>
              ))}
            </Select>
          </Form.Item>

          <Form.Item>
            <Space>
              <Button type="primary" htmlType="submit">
                Salvar
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

export default Itens;
