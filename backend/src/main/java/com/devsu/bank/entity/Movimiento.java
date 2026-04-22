package com.devsu.bank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "movimientos")
@Getter
@Setter
@NoArgsConstructor
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movimiento_id")
    private Long movimientoId;

    @NotNull
    @Column(nullable = false)
    private LocalDate fecha;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false, length = 20)
    private TipoMovimiento tipoMovimiento;

    // Valor positivo = crédito, negativo = débito
    @NotNull
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    // Saldo disponible después de aplicar el movimiento
    @NotNull
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal saldo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;
}
