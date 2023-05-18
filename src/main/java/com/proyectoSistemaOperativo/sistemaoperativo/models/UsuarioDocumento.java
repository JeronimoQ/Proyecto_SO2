package com.proyectoSistemaOperativo.sistemaoperativo.models;

import com.sun.tracing.dtrace.ProviderAttributes;
import lombok.*;


import javax.persistence.*;

@Entity
@Table(name = "usuariodocumento")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioDocumento {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "nombredocumento")
    private String nombredocumento;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "numerodocumento")
    private String numerodocumento;

    @Column(name = "rutadocumento")
    private String rutadocumento;

}
