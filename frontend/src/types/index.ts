export interface Cliente {
  clienteId: number;
  nombre: string;
  genero: string;
  edad: number;
  identificacion: string;
  direccion: string;
  telefono: string;
  estado: boolean;
}

export interface Cuenta {
  cuentaId: number;
  numeroCuenta: string;
  tipoCuenta: 'AHORRO' | 'CORRIENTE';
  saldoInicial: number;
  estado: boolean;
  clienteId: number;
  clienteNombre: string;
}

export interface Movimiento {
  movimientoId: number;
  fecha: string;
  tipoMovimiento: 'CREDITO' | 'DEBITO';
  valor: number;
  saldo: number;
  cuentaId: number;
  numeroCuenta: string;
}

export interface ReporteItem {
  fecha: string;
  cliente: string;
  numeroCuenta: string;
  tipoCuenta: string;
  saldoInicial: number;
  estado: boolean;
  movimiento: number;
  saldoDisponible: number;
}

export interface ReporteResponse {
  movimientos: ReporteItem[];
  pdfBase64: string;
}
