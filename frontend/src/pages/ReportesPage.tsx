import { useEffect, useState } from 'react';
import { reporteService } from '../api/services/movimientoService';
import { clienteService } from '../api/services/clienteService';
import { Cliente, ReporteResponse } from '../types';
import { Calendar, FileText, Download } from 'lucide-react';

export const ReportesPage = () => {
  const [clientes, setClientes] = useState<Cliente[]>([]);
  const [selectedCliente, setSelectedCliente] = useState('');
  const [fechaInicio, setFechaInicio] = useState('');
  const [fechaFin, setFechaFin] = useState('');
  const [reporte, setReporte] = useState<ReporteResponse | null>(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    clienteService.getAll().then(setClientes);
  }, []);

  const handleGenerar = async () => {
    if (!selectedCliente || !fechaInicio || !fechaFin) {
      alert('Por favor complete todos los filtros');
      return;
    }

    setLoading(true);
    try {
      const data = await reporteService.getEstadoCuenta(
        Number(selectedCliente),
        fechaInicio,
        fechaFin
      );
      setReporte(data);
    } catch (error) {
      alert('Error al generar reporte');
    } finally {
      setLoading(false);
    }
  };

  const handleDownloadPDF = () => {
    if (!reporte?.pdfBase64) return;
    const linkSource = `data:application/pdf;base64,${reporte.pdfBase64}`;
    const downloadLink = document.createElement("a");
    const clienteNombre = reporte.movimientos.length > 0 ? reporte.movimientos[0].cliente : 'Reporte';
    const fileName = `Estado_Cuenta_${clienteNombre}.pdf`;
    downloadLink.href = linkSource;
    downloadLink.download = fileName;
    downloadLink.click();
  };

  return (
    <div className="page-container glass-panel">
      <div className="page-header">
        <h1 className="page-title">Reportes</h1>
        <div style={{ display: 'flex', gap: '12px', alignItems: 'flex-end' }}>
          <div className="form-group" style={{ marginBottom: 0 }}>
            <label className="form-label">Cliente</label>
            <select className="form-select" value={selectedCliente} onChange={(e) => setSelectedCliente(e.target.value)}>
              <option value="">Seleccione...</option>
              {clientes.map(c => <option key={c.clienteId} value={c.clienteId}>{c.nombre}</option>)}
            </select>
          </div>
          <div className="form-group" style={{ marginBottom: 0 }}>
            <label className="form-label">Desde</label>
            <input type="date" className="form-input" value={fechaInicio} onChange={(e) => setFechaInicio(e.target.value)} />
          </div>
          <div className="form-group" style={{ marginBottom: 0 }}>
            <label className="form-label">Hasta</label>
            <input type="date" className="form-input" value={fechaFin} onChange={(e) => setFechaFin(e.target.value)} />
          </div>
          <button className="btn btn-primary" onClick={handleGenerar} disabled={loading}>
            <FileText size={18} /> {loading ? 'Generando...' : 'Consultar'}
          </button>
        </div>
      </div>

      <div className="page-content">
        {reporte ? (
          <div className="animate-fade-in">
            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '24px' }}>
              <div>
                <h2>Estado de Cuenta</h2>
                <p style={{ color: 'var(--text-secondary)' }}>Cliente: {reporte.movimientos.length > 0 ? reporte.movimientos[0].cliente : selectedCliente}</p>
                <p style={{ color: 'var(--text-secondary)' }}>Rango: {fechaInicio} a {fechaFin}</p>
              </div>
              <button className="btn btn-success" onClick={handleDownloadPDF}>
                <Download size={18} /> Descargar PDF
              </button>
            </div>

            <div className="glass-panel" style={{ padding: '20px' }}>
              <table className="data-table">
                <thead>
                  <tr>
                    <th>Fecha</th>
                    <th>Cuenta</th>
                    <th>Tipo</th>
                    <th>Saldo Inicial</th>
                    <th>Movimiento</th>
                    <th>Saldo Disponible</th>
                  </tr>
                </thead>
                <tbody>
                  {reporte.movimientos.map((m, idx) => (
                    <tr key={idx}>
                      <td>{new Date(m.fecha).toLocaleDateString()}</td>
                      <td>{m.numeroCuenta}</td>
                      <td>{m.tipoCuenta}</td>
                      <td>${m.saldoInicial.toFixed(2)}</td>
                      <td style={{ color: m.movimiento < 0 ? 'var(--danger)' : 'var(--success)' }}>
                        {m.movimiento < 0 ? '' : '+'}${m.movimiento.toFixed(2)}
                      </td>
                      <td>${m.saldoDisponible.toFixed(2)}</td>
                    </tr>
                  ))}
                  {reporte.movimientos.length === 0 && (
                    <tr>
                      <td colSpan={6} style={{ textAlign: 'center' }}>No hay movimientos en este rango</td>
                    </tr>
                  )}
                </tbody>
              </table>
            </div>
          </div>
        ) : (
          <div style={{ textAlign: 'center', padding: '60px', color: 'var(--text-secondary)' }}>
            <Calendar size={48} style={{ opacity: 0.2, marginBottom: '16px' }} />
            <p>Seleccione los filtros para generar el estado de cuenta</p>
          </div>
        )}
      </div>
    </div>
  );
};
