import React, { useState, useEffect } from 'react';
import { Table, Modal, Form, Input, Space, Popconfirm, message, Tag, Spin } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined, AppstoreOutlined } from '@ant-design/icons';
import { categoriasAPI } from '../../api/categorias';
import Card from '../../components/atoms/Card';
import Button from '../../components/atoms/Button';
import useModal from '../../hooks/useModal';
import './Categorias.css';

const Categorias = () => {
  const [categorias, setCategorias] = useState([]);
  const [loading, setLoading] = useState(false);
  const modal = useModal();
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
    if (categoria) {
      form.setFieldsValue(categoria);
    } else {
      form.resetFields();
    }
    modal.open(categoria);
  };

  const fecharModal = () => {
    form.resetFields();
    modal.close();
  };

  const salvar = async (values) => {
    try {
      if (modal.data) {
        await categoriasAPI.atualizar(modal.data.id, values);
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
      title: 'Nome',
      dataIndex: 'nome',
      key: 'nome',
      render: (text) => <strong>{text}</strong>,
    },
    {
      title: 'Descrição',
      dataIndex: 'descricao',
      key: 'descricao',
    },
    {
      title: 'Status',
      dataIndex: 'ativo',
      key: 'ativo',
      width: 120,
      render: (ativo) => (
        <Tag color={ativo ? 'success' : 'default'}>
          {ativo ? 'Ativo' : 'Inativo'}
        </Tag>
      ),
    },
    {
      title: 'Ações',
      key: 'acoes',
      width: 180,
      render: (_, record) => (
        <Space size="small">
          <Button
            variant="link"
            icon={<EditOutlined />}
            onClick={() => abrirModal(record)}
            size="small"
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
              <Button variant="danger" icon={<DeleteOutlined />} size="small">
                Desativar
              </Button>
            </Popconfirm>
          )}
        </Space>
      ),
    },
  ];

  if (loading && categorias.length === 0) {
    return (
      <div className="categorias-loading">
        <Spin size="large" tip="Carregando categorias..." />
      </div>
    );
  }

  return (
    <div className="categorias">
      <div className="categorias-header">
        <div>
          <h1 className="categorias-title">
            <AppstoreOutlined /> Categorias
          </h1>
          <p className="categorias-subtitle">
            Gerencie as categorias de itens do inventário
          </p>
        </div>
        <Button
          variant="primary"
          icon={<PlusOutlined />}
          onClick={() => abrirModal()}
        >
          Nova Categoria
        </Button>
      </div>

      <Card className="categorias-card">
        <Table
          dataSource={categorias}
          columns={colunas}
          rowKey="id"
          loading={loading}
          pagination={{
            pageSize: 10,
            showSizeChanger: true,
            showTotal: (total) => `Total: ${total} categorias`,
          }}
          locale={{ emptyText: 'Nenhuma categoria cadastrada' }}
          className="categorias-table"
        />
      </Card>

      <Modal
        title={modal.data ? 'Editar Categoria' : 'Nova Categoria'}
        open={modal.isOpen}
        onCancel={fecharModal}
        footer={null}
        className="categorias-modal"
        width={600}
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
            <Input size="large" placeholder="Ex: Ferramentas" />
          </Form.Item>

          <Form.Item
            label="Descricao"
            name="descricao"
            rules={[
              { max: 500, message: 'Maximo 500 caracteres' }
            ]}
          >
            <Input.TextArea
              rows={4}
              placeholder="Descreva a categoria..."
              size="large"
            />
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

export default Categorias;
