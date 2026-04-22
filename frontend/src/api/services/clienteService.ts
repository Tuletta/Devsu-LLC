import apiClient from '../client';
import { Cliente } from '../../types';

export const clienteService = {
  getAll: () => apiClient.get<Cliente[]>('/clientes').then(res => res.data),
  getById: (id: number) => apiClient.get<Cliente>(`/clientes/${id}`).then(res => res.data),
  create: (data: Partial<Cliente>) => apiClient.post<Cliente>('/clientes', data).then(res => res.data),
  update: (id: number, data: Partial<Cliente>) => apiClient.put<Cliente>(`/clientes/${id}`, data).then(res => res.data),
  delete: (id: number) => apiClient.delete(`/clientes/${id}`),
};
