package com.devsu.bank.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El género es obligatorio")
    private String genero;

    @NotNull(message = "La edad es obligatoria")
    @Positive(message = "La edad debe ser positiva")
    private Integer edad;

    @NotBlank(message = "La identificación es obligatoria")
    private String identificacion;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    private String contrasena;

    private Boolean estado = true;
}
