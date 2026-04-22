import { render, screen, waitFor } from '@testing-library/react';
import { ClientesPage } from '../pages/ClientesPage';
import { clienteService } from '../api/services/clienteService';
import { describe, it, expect, vi } from 'vitest';

// Simulamos el servicio de clientes
vi.mock('../api/services/clienteService', () => ({
  clienteService: {
    getAll: vi.fn(),
  },
}));

describe('ClientesPage', () => {
  it('debe mostrar el título de la página', async () => {
    (clienteService.getAll as any).mockResolvedValue([]);
    render(<ClientesPage />);
    expect(screen.getByText('Clientes')).toBeInTheDocument();
  });

  it('debe mostrar la lista de clientes cuando la API responde', async () => {
    const mockClientes = [
      {
        clienteId: 1,
        nombre: 'Jose Lema',
        identificacion: '12345',
        edad: 30,
        genero: 'Masculino',
        telefono: '0988',
        estado: true,
      },
    ];

    (clienteService.getAll as any).mockResolvedValue(mockClientes);

    render(<ClientesPage />);

    await waitFor(() => {
      expect(screen.getByText('Jose Lema')).toBeInTheDocument();
      expect(screen.getByText('12345')).toBeInTheDocument();
    });
  });
});
