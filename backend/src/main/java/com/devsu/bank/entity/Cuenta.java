package com.devsu.bank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "cuentas")
@Getter
@Setter
@NoArgsConstructor
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuenta_id")
    private Long cuentaId;

    @NotBlank
    @Column(name = "numero_cuenta", nullable = false, unique = true, length = 20)
    private String numeroCuenta;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta", nullable = false, length = 20)
    private TipoCuenta tipoCuenta;

    @NotNull
    @PositiveOrZero
    @Column(name = "saldo_inicial", nullable = false, precision = 12, scale = 2)
    private BigDecimal saldoInicial;

    @Column(nullable = false)
    private Boolean estado = true;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
