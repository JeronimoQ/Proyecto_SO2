package com.proyectoSistemaOperativo.sistemaoperativo.models;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombredocumentooriginal")
    private String nombredocumentooriginal;

    @Column(name = "nuevonombre")
    private String nuevonombre;

  /*  @Column(name = "usuario")
    private String usu;

    @Column(name = "numerodocumento")
    private String numerodocumento;*/

    @Column(name = "rutadocumento")
    private String rutadocumento;

    @ManyToOne
    @JoinColumn(name = "usuario_dpi")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "numdoc_id")
    private Documento documento;

}
