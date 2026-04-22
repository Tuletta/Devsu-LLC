-- DDL Generado para BaseDatos.sql
CREATE TABLE IF NOT EXISTS clientes (
    cliente_id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(20) NOT NULL,
    edad INTEGER NOT NULL,
    identificacion VARCHAR(20) NOT NULL UNIQUE,
    direccion VARCHAR(200) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS cuentas (
    cuenta_id BIGSERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(20) NOT NULL UNIQUE,
    tipo_cuenta VARCHAR(20) NOT NULL,
    saldo_inicial NUMERIC(12, 2) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    cliente_id BIGINT NOT NULL,
    CONSTRAINT fk_cuentas_clientes FOREIGN KEY (cliente_id) REFERENCES clientes(cliente_id)
);

CREATE TABLE IF NOT EXISTS movimientos (
    movimiento_id BIGSERIAL PRIMARY KEY,
    fecha DATE NOT NULL,
    tipo_movimiento VARCHAR(20) NOT NULL,
    valor NUMERIC(12, 2) NOT NULL,
    saldo NUMERIC(12, 2) NOT NULL,
    cuenta_id BIGINT NOT NULL,
    CONSTRAINT fk_movimientos_cuentas FOREIGN KEY (cuenta_id) REFERENCES cuentas(cuenta_id)
);

-- Datos iniciales del caso de uso
INSERT INTO clientes (nombre, genero, edad, identificacion, direccion, telefono, contrasena, estado) VALUES
('Jose Lema', 'Masculino', 30, '0000000001', 'Otavalo sn y principal', '098254785', '$2a$10$T9P4T9hP4T9hP4T9hP4T9.8', true),
('Marianela Montalvo', 'Femenino', 35, '0000000002', 'Amazonas y NNUU', '097548965', '$2a$10$T9P4T9hP4T9hP4T9hP4T9.8', true),
('Juan Osorio', 'Masculino', 40, '0000000003', '13 junio y Equinoccial', '098874587', '$2a$10$T9P4T9hP4T9hP4T9hP4T9.8', true)
ON CONFLICT (identificacion) DO NOTHING;

INSERT INTO cuentas (numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id) VALUES
('478758', 'AHORRO', 2000.00, true, (SELECT cliente_id FROM clientes WHERE identificacion = '0000000001')),
('225487', 'CORRIENTE', 100.00, true, (SELECT cliente_id FROM clientes WHERE identificacion = '0000000002')),
('495878', 'AHORRO', 0.00, true, (SELECT cliente_id FROM clientes WHERE identificacion = '0000000003')),
('496825', 'AHORRO', 540.00, true, (SELECT cliente_id FROM clientes WHERE identificacion = '0000000002'))
ON CONFLICT (numero_cuenta) DO NOTHING;
