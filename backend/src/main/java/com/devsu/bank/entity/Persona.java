package com.devsu.bank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

// Superclase compartida por Cliente; no genera tabla propia
@MappedSuperclass
@Getter
@Setter
public abstract class Persona {

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String genero;

    @Positive
    @Column(nullable = false)
    private Integer edad;

    @NotBlank
    @Column(nullable = false, unique = true, length = 20)
    private String identificacion;

    @NotBlank
    @Column(nullable = false, length = 200)
    private String direccion;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String telefono;
}
