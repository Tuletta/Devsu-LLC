import { useEffect, useState } from 'react';
import { clienteService } from '../api/services/clienteService';
import { Cliente } from '../types';
import { Modal } from '../components/common/Modal';
import { Edit2, Trash2, UserPlus } from 'lucide-react';

export const ClientesPage = () => {
  const [clientes, setClientes] = useState<Cliente[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingCliente, setEditingCliente] = useState<Cliente | null>(null);
  const [loading, setLoading] = useState(true);

  const fetchClientes = async () => {
    try {
      const data = await clienteService.getAll();
      setClientes(data);
    } catch (error) {
      console.error('Error fetching clientes:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchClientes();
  }, []);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    const clienteData = Object.fromEntries(formData.entries());
    
    try {
      if (editingCliente) {
        await clienteService.update(editingCliente.clienteId, clienteData);
      } else {
        await clienteService.create(clienteData);
      }
      setIsModalOpen(false);
      setEditingCliente(null);
      fetchClientes();
    } catch (error) {
      alert('Error al guardar cliente');
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('¿Estás seguro de eliminar este cliente?')) {
      await clienteService.delete(id);
      fetchClientes();
    }
  };

  return (
    <div className="page-container glass-panel">
      <div className="page-header">
        <h1 className="page-title">Clientes</h1>
        <button className="btn btn-primary" onClick={() => { setEditingCliente(null); setIsModalOpen(true); }}>
          <UserPlus size={18} /> Nuevo
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
                  <th>Nombre</th>
                  <th>Identificación</th>
                  <th>Edad</th>
                  <th>Género</th>
                  <th>Teléfono</th>
                  <th>Estado</th>
                  <th>Acciones</th>
                </tr>
              </thead>
              <tbody>
                {clientes.map(cliente => (
                  <tr key={cliente.clienteId}>
                    <td>{cliente.nombre}</td>
                    <td>{cliente.identificacion}</td>
                    <td>{cliente.edad}</td>
                    <td>{cliente.genero}</td>
                    <td>{cliente.telefono}</td>
                    <td>
                      <span className={`badge ${cliente.estado ? 'badge-success' : 'badge-danger'}`}>
                        {cliente.estado ? 'Activo' : 'Inactivo'}
                      </span>
                    </td>
                    <td>
                      <div style={{ display: 'flex', gap: '8px' }}>
                        <button className="btn btn-secondary" style={{ padding: '6px' }} onClick={() => { setEditingCliente(cliente); setIsModalOpen(true); }}>
                          <Edit2 size={14} />
                        </button>
                        <button className="btn btn-danger" style={{ padding: '6px' }} onClick={() => handleDelete(cliente.clienteId)}>
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
        title={editingCliente ? 'Editar Cliente' : 'Nuevo Cliente'}
      >
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label">Nombre Completo</label>
            <input className="form-input" name="nombre" defaultValue={editingCliente?.nombre} required />
          </div>
          <div className="form-group">
            <label className="form-label">Identificación</label>
            <input className="form-input" name="identificacion" defaultValue={editingCliente?.identificacion} required />
          </div>
          <div className="form-group" style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '16px' }}>
            <div>
              <label className="form-label">Edad</label>
              <input className="form-input" type="number" name="edad" defaultValue={editingCliente?.edad} required />
            </div>
            <div>
              <label className="form-label">Género</label>
              <select className="form-select" name="genero" defaultValue={editingCliente?.genero} required>
                <option value="Masculino">Masculino</option>
                <option value="Femenino">Femenino</option>
                <option value="Otro">Otro</option>
              </select>
            </div>
          </div>
          <div className="form-group">
            <label className="form-label">Dirección</label>
            <input className="form-input" name="direccion" defaultValue={editingCliente?.direccion} required />
          </div>
          <div className="form-group">
            <label className="form-label">Teléfono</label>
            <input className="form-input" name="telefono" defaultValue={editingCliente?.telefono} required />
          </div>
          {!editingCliente && (
            <div className="form-group">
              <label className="form-label">Contraseña</label>
              <input className="form-input" type="password" name="contrasena" required />
            </div>
          )}
          <div className="form-group">
            <label className="form-label">Estado</label>
            <select className="form-select" name="estado" defaultValue={editingCliente?.estado ? 'true' : 'false'}>
              <option value="true">Activo</option>
              <option value="false">Inactivo</option>
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
