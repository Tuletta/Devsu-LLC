import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { MainLayout } from './components/layout/MainLayout';
import { ClientesPage } from './pages/ClientesPage';
import { CuentasPage } from './pages/CuentasPage';
import { MovimientosPage } from './pages/MovimientosPage';
import { ReportesPage } from './pages/ReportesPage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route index element={<Navigate to="/clientes" replace />} />
          <Route path="clientes" element={<ClientesPage />} />
          <Route path="cuentas" element={<CuentasPage />} />
          <Route path="movimientos" element={<MovimientosPage />} />
          <Route path="reportes" element={<ReportesPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
