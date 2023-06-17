package com.proyectoSistemaOperativo.sistemaoperativo.repository;

import com.proyectoSistemaOperativo.sistemaoperativo.models.UsuarioDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioDocumentoRepository extends JpaRepository<UsuarioDocumento, Long> {
List<UsuarioDocumento> findAllByNuevonombreIn (List<String> nombre);

}
