import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { ConfigProvider } from 'antd';
import ptBR from 'antd/locale/pt_BR';
import MainLayout from './components/Layout/MainLayout';
import Dashboard from './pages/Dashboard/Dashboard';
import Categorias from './pages/Categorias/Categorias';
import Itens from './pages/Itens/Itens';
import Movimentacoes from './pages/Movimentacoes/Movimentacoes';

function App() {
  return (
    <ConfigProvider locale={ptBR}>
      <BrowserRouter>
        <MainLayout>
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/categorias" element={<Categorias />} />
            <Route path="/itens" element={<Itens />} />
            <Route path="/movimentacoes" element={<Movimentacoes />} />
          </Routes>
        </MainLayout>
      </BrowserRouter>
    </ConfigProvider>
  );
}

export default App;
