import { useEffect, useState } from 'react';
import { cuentaService } from '../api/services/cuentaService';
import { clienteService } from '../api/services/clienteService';
import { Cuenta, Cliente } from '../types';
import { Modal } from '../components/common/Modal';
import { Edit2, Trash2, PlusCircle } from 'lucide-react';

export const CuentasPage = () => {
  const [cuentas, setCuentas] = useState<Cuenta[]>([]);
  const [clientes, setClientes] = useState<Cliente[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingCuenta, setEditingCuenta] = useState<Cuenta | null>(null);
  const [loading, setLoading] = useState(true);

  const fetchData = async () => {
    try {
      const [cuentasData, clientesData] = await Promise.all([
        cuentaService.getAll(),
        clienteService.getAll()
      ]);
      setCuentas(cuentasData);
      setClientes(clientesData);
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
    
    // Parse types
    data.saldoInicial = Number(data.saldoInicial);
    data.clienteId = Number(data.clienteId);
    data.estado = data.estado === 'true';

    try {
      if (editingCuenta) {
        await cuentaService.update(editingCuenta.cuentaId, data);
      } else {
        await cuentaService.create(data);
      }
      setIsModalOpen(false);
      setEditingCuenta(null);
      fetchData();
    } catch (error) {
      alert('Error al guardar cuenta');
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('¿Estás seguro de eliminar esta cuenta?')) {
      await cuentaService.delete(id);
      fetchData();
    }
  };

  return (
    <div className="page-container glass-panel">
      <div className="page-header">
        <h1 className="page-title">Cuentas</h1>
        <button className="btn btn-primary" onClick={() => { setEditingCuenta(null); setIsModalOpen(true); }}>
          <PlusCircle size={18} /> Nueva Cuenta
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
                  <th>Número</th>
                  <th>Cliente</th>
                  <th>Tipo</th>
                  <th>Saldo Inicial</th>
                  <th>Estado</th>
                  <th>Acciones</th>
                </tr>
              </thead>
              <tbody>
                {cuentas.map(cuenta => (
                  <tr key={cuenta.cuentaId}>
                    <td>{cuenta.numeroCuenta}</td>
                    <td>{cuenta.clienteNombre}</td>
                    <td>
                      <span className="badge badge-info">{cuenta.tipoCuenta}</span>
                    </td>
                    <td>${cuenta.saldoInicial.toFixed(2)}</td>
                    <td>
                      <span className={`badge ${cuenta.estado ? 'badge-success' : 'badge-danger'}`}>
                        {cuenta.estado ? 'Activa' : 'Inactiva'}
                      </span>
                    </td>
                    <td>
                      <div style={{ display: 'flex', gap: '8px' }}>
                        <button className="btn btn-secondary" style={{ padding: '6px' }} onClick={() => { setEditingCuenta(cuenta); setIsModalOpen(true); }}>
                          <Edit2 size={14} />
                        </button>
                        <button className="btn btn-danger" style={{ padding: '6px' }} onClick={() => handleDelete(cuenta.cuentaId)}>
                          <Trash2 size={14} />
                        </button>
                      </div>
                    </td>
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
        title={editingCuenta ? 'Editar Cuenta' : 'Nueva Cuenta'}
      >
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label">Cliente</label>
            <select className="form-select" name="clienteId" defaultValue={editingCuenta?.clienteId} required>
              <option value="">Seleccione un cliente</option>
              {clientes.map(c => (
                <option key={c.clienteId} value={c.clienteId}>{c.nombre}</option>
              ))}
            </select>
          </div>
          <div className="form-group">
            <label className="form-label">Número de Cuenta</label>
            <input className="form-input" name="numeroCuenta" defaultValue={editingCuenta?.numeroCuenta} required />
          </div>
          <div className="form-group">
            <label className="form-label">Tipo de Cuenta</label>
            <select className="form-select" name="tipoCuenta" defaultValue={editingCuenta?.tipoCuenta} required>
              <option value="AHORRO">Ahorro</option>
              <option value="CORRIENTE">Corriente</option>
            </select>
          </div>
          <div className="form-group">
            <label className="form-label">Saldo Inicial</label>
            <input className="form-input" type="number" step="0.01" name="saldoInicial" defaultValue={editingCuenta?.saldoInicial} required disabled={!!editingCuenta} />
          </div>
          <div className="form-group">
            <label className="form-label">Estado</label>
            <select className="form-select" name="estado" defaultValue={editingCuenta?.estado ? 'true' : 'false'}>
              <option value="true">Activa</option>
              <option value="false">Inactiva</option>
            </select>
          </div>
          
          <div style={{ marginTop: '24px', display: 'flex', justifyContent: 'flex-end', gap: '12px' }}>
            <button type="button" className="btn btn-secondary" onClick={() => setIsModalOpen(false)}>Cancelar</button>
            <button type="submit" className="btn btn-primary">Guardar</button>
          </div>
        </form>
      </Modal>
    </div>
  );
};
