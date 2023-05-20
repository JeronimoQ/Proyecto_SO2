package com.proyectoSistemaOperativo.sistemaoperativo.models;

//import jakarta.persistence.*;
//import lombok.*;
//import javax.persistence.*;


import lombok.*;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @Column(name = "dpi")
    private long dpi;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "tel")
    private int tel;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "perfil")
    private String perfil;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "usuario")
    private List<UsuarioDocumento> documentarchivo;
}
