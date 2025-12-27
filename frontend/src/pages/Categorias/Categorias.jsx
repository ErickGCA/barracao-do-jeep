import React, { useState, useEffect } from 'react';
import { Table, Button, Modal, Form, Input, Space, Popconfirm, message, Tag } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { categoriasAPI } from '../../api/categorias';

const Categorias = () => {
  const [categorias, setCategorias] = useState([]);
  const [loading, setLoading] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
  const [editando, setEditando] = useState(null);
  const [form] = Form.useForm();

  useEffect(() => {
    carregarCategorias();
  }, []);

  const carregarCategorias = async () => {
    try {
      setLoading(true);
      const response = await categoriasAPI.listarTodas();
      setCategorias(response.data);
    } catch (error) {
      message.error('Erro ao carregar categorias');
    } finally {
      setLoading(false);
    }
  };

  const abrirModal = (categoria = null) => {
    setEditando(categoria);
    if (categoria) {
      form.setFieldsValue(categoria);
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
        await categoriasAPI.atualizar(editando.id, values);
        message.success('Categoria atualizada com sucesso!');
      } else {
        await categoriasAPI.criar(values);
        message.success('Categoria criada com sucesso!');
      }
      fecharModal();
      carregarCategorias();
    } catch (error) {
      message.error(error.response?.data?.message || 'Erro ao salvar categoria');
    }
  };

  const desativar = async (id) => {
    try {
      await categoriasAPI.desativar(id);
      message.success('Categoria desativada com sucesso!');
      carregarCategorias();
    } catch (error) {
      message.error('Erro ao desativar categoria');
    }
  };

  const colunas = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
      width: 80,
    },
    {
      title: 'Nome',
      dataIndex: 'nome',
      key: 'nome',
    },
    {
      title: 'Descricao',
      dataIndex: 'descricao',
      key: 'descricao',
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
      width: 150,
      render: (_, record) => (
        <Space>
          <Button
            type="link"
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
              <Button type="link" danger icon={<DeleteOutlined />}>
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
        <h1>Categorias</h1>
        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={() => abrirModal()}
        >
          Nova Categoria
        </Button>
      </div>

      <Table
        dataSource={categorias}
        columns={colunas}
        rowKey="id"
        loading={loading}
      />

      <Modal
        title={editando ? 'Editar Categoria' : 'Nova Categoria'}
        open={modalVisible}
        onCancel={fecharModal}
        footer={null}
      >
        <Form form={form} layout="vertical" onFinish={salvar}>
          <Form.Item
            label="Nome"
            name="nome"
            rules={[
              { required: true, message: 'Nome e obrigatorio' },
              { min: 3, message: 'Minimo 3 caracteres' },
              { max: 100, message: 'Maximo 100 caracteres' }
            ]}
          >
            <Input />
          </Form.Item>

          <Form.Item
            label="Descricao"
            name="descricao"
            rules={[
              { max: 500, message: 'Maximo 500 caracteres' }
            ]}
          >
            <Input.TextArea rows={4} />
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

export default Categorias;
