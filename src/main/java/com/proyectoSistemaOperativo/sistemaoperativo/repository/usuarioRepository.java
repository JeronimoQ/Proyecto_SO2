package com.proyectoSistemaOperativo.sistemaoperativo.repository;

import com.proyectoSistemaOperativo.sistemaoperativo.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface usuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String usuario);
}
