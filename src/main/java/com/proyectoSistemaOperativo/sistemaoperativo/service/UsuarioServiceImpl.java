package com.proyectoSistemaOperativo.sistemaoperativo.service;

import com.proyectoSistemaOperativo.sistemaoperativo.models.Usuario;
import com.proyectoSistemaOperativo.sistemaoperativo.repository.usuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private usuarioRepository usuarioRepository;


    @Override
    public boolean authenticate(String username, String pass) {
        Usuario user = usuarioRepository.findByUsername(username);
        if(user == null){
            return false;
        }
        return user.getPassword().equals(pass);
    }


}
