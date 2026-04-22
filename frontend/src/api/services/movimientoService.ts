import apiClient from '../client';
import { Movimiento, ReporteResponse } from '../../types';

export const movimientoService = {
  getAll: () => apiClient.get<Movimiento[]>('/movimientos').then(res => res.data),
  create: (data: any) => apiClient.post<Movimiento>('/movimientos', data).then(res => res.data),
};

export const reporteService = {
  getEstadoCuenta: (clienteId: number, fechaInicio: string, fechaFin: string) => 
    apiClient.get<ReporteResponse>('/reportes', {
      params: { clienteId, fechaInicio, fechaFin }
    }).then(res => res.data),
};
