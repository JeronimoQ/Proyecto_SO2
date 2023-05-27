package com.proyectoSistemaOperativo.sistemaoperativo.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "registroactividad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroActividad {

    @Id
    @Column(name = "idRegistro")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idRegistro;

//    @Column(name = "dpi")
//    private long dpi;

    @Column(name = "ipUsuario")
    private String ip;

    @Column(name = "numerdoc")
    private String numerdoc;


    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss a")
    private Date fechaRegistro;

    @Column(name = "accion")
    private String accion;

    @ManyToOne
    @JoinColumn(name = "usuario_dpi")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "numdoc_id")
    private Documento documento;
//
//    @ManyToOne
//    @JoinColumn(name = "id")
//    private Documento documento;


}
