import apiClient from '../client';
import { Cuenta } from '../../types';

export const cuentaService = {
  getAll: () => apiClient.get<Cuenta[]>('/cuentas').then(res => res.data),
  create: (data: Partial<Cuenta>) => apiClient.post<Cuenta>('/cuentas', data).then(res => res.data),
  update: (id: number, data: Partial<Cuenta>) => apiClient.put<Cuenta>(`/cuentas/${id}`, data).then(res => res.data),
  delete: (id: number) => apiClient.delete(`/cuentas/${id}`),
};
