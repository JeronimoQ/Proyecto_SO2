package com.proyectoSistemaOperativo.sistemaoperativo.repository;

import com.proyectoSistemaOperativo.sistemaoperativo.models.Documento;
import com.proyectoSistemaOperativo.sistemaoperativo.models.UsuarioDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface documentoRepository extends JpaRepository<Documento, Long> {

    @Query("SELECT ud FROM UsuarioDocumento ud INNER JOIN ud.documento d WHERE d.propietariodocumento = :propietario OR d.fechaelaboracion >= :fechaInicio AND d.fechaelaboracion <= :fechaFin")
    List<UsuarioDocumento> findUsuarioDocumentosByPropietarioAndFechas(@Param("propietario") String propietario,
                                                                       @Param("fechaInicio") Date fechaInicio,
                                                                       @Param("fechaFin") Date fechaFin);

    List<Documento> findAllByNumerdocIn(List<String> numerdocs);

}
