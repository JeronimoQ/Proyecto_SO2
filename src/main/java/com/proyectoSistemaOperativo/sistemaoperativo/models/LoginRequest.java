package com.proyectoSistemaOperativo.sistemaoperativo.models;

//import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter

public class LoginRequest {
        @NotBlank
        private String username;

        @NotBlank
        private String password;

        // Constructor, getters y setters

        // Constructor vacío requerido por Thymeleaf
        public LoginRequest() {}

        // Constructor con campos requeridos
        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        // Getters y setters omitidos para mayor claridad


}
