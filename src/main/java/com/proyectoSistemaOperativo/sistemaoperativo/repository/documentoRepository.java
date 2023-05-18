package com.proyectoSistemaOperativo.sistemaoperativo.repository;

import com.proyectoSistemaOperativo.sistemaoperativo.models.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface documentoRepository extends JpaRepository<Documento, Long> {
}
