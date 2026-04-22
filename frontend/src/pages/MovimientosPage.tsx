import { useEffect, useState } from 'react';
import { movimientoService } from '../api/services/movimientoService';
import { cuentaService } from '../api/services/cuentaService';
import { Movimiento, Cuenta } from '../types';
import { Modal } from '../components/common/Modal';
import { PlusCircle } from 'lucide-react';

export const MovimientosPage = () => {
  const [movimientos, setMovimientos] = useState<Movimiento[]>([]);
  const [cuentas, setCuentas] = useState<Cuenta[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [loading, setLoading] = useState(true);

  const fetchData = async () => {
    try {
      const [movsData, cuentasData] = await Promise.all([
        movimientoService.getAll(),
        cuentaService.getAll()
      ]);
      setMovimientos(movsData);
      setCuentas(cuentasData);
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    const data: any = Object.fromEntries(formData.entries());
    
    data.valor = Number(data.valor);
    data.cuentaId = Number(data.cuentaId);

    try {
      await movimientoService.create(data);
      setIsModalOpen(false);
      fetchData();
    } catch (error: any) {
      const errorMsg = error.response?.data?.message || 'Error al procesar movimiento';
      alert(errorMsg);
    }
  };

  return (
    <div className="page-container glass-panel">
      <div className="page-header">
        <h1 className="page-title">Movimientos</h1>
        <button className="btn btn-primary" onClick={() => setIsModalOpen(true)}>
          <PlusCircle size={18} /> Nuevo Movimiento
        </button>
      </div>

      <div className="page-content">
        {loading ? (
          <p>Cargando...</p>
        ) : (
          <div className="table-container">
            <table className="data-table">
              <thead>
                <tr>
                  <th>Fecha</th>
                  <th>Cuenta</th>
                  <th>Tipo</th>
                  <th>Valor</th>
                  <th>Saldo Resultante</th>
                </tr>
              </thead>
              <tbody>
                {movimientos.map(mov => (
                  <tr key={mov.movimientoId}>
                    <td>{new Date(mov.fecha).toLocaleDateString()}</td>
                    <td>{mov.numeroCuenta}</td>
                    <td>
                      <span className={`badge ${mov.tipoMovimiento === 'CREDITO' ? 'badge-success' : 'badge-danger'}`}>
                        {mov.tipoMovimiento}
                      </span>
                    </td>
                    <td style={{ color: mov.tipoMovimiento === 'DEBITO' ? 'var(--danger)' : 'var(--success)', fontWeight: '600' }}>
                      {mov.tipoMovimiento === 'DEBITO' ? '-' : '+'}${Math.abs(mov.valor).toFixed(2)}
                    </td>
                    <td>${mov.saldo.toFixed(2)}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>

      <Modal 
        isOpen={isModalOpen} 
        onClose={() => setIsModalOpen(false)} 
        title="Nuevo Movimiento"
      >
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label">Cuenta</label>
            <select className="form-select" name="cuentaId" required>
              <option value="">Seleccione una cuenta</option>
              {cuentas.map(c => (
                <option key={c.cuentaId} value={c.cuentaId}>{c.numeroCuenta} - {c.clienteNombre}</option>
              ))}
            </select>
          </div>
          <div className="form-group">
            <label className="form-label">Tipo de Movimiento</label>
            <select className="form-select" name="tipoMovimiento" required>
              <option value="CREDITO">Depósito (Crédito)</option>
              <option value="DEBITO">Retiro (Débito)</option>
            </select>
          </div>
          <div className="form-group">
            <label className="form-label">Valor</label>
            <input className="form-input" type="number" step="0.01" min="0.01" name="valor" placeholder="Ej: 100.00" required />
          </div>
          
          <div style={{ marginTop: '24px', display: 'flex', justifyContent: 'flex-end', gap: '12px' }}>
            <button type="button" className="btn btn-secondary" onClick={() => setIsModalOpen(false)}>Cancelar</button>
            <button type="submit" className="btn btn-primary">Ejecutar Transacción</button>
          </div>
        </form>
      </Modal>
    </div>
  );
};
