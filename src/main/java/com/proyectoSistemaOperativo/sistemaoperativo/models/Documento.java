package com.proyectoSistemaOperativo.sistemaoperativo.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "documento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Documento {

    @Id
    @Column(name = "numerdoc")
    private String numerdoc;

    /*@Column(name = "ruta")
    private String ruta;

    @Column(name = "nombrearchivo")
    private String nombre;*/

    @Column(name = "propietariodocumento")
    private String propietariodocumento;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss a")
    private Date fechaingresosistema;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyy-MM-dd")
    private Date fechaelaboracion;

    @OneToMany(mappedBy = "documento")
    private List<UsuarioDocumento> documentarchivo;
}
