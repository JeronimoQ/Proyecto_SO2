package com.proyectoSistemaOperativo.sistemaoperativo.service;

import com.proyectoSistemaOperativo.sistemaoperativo.models.Documento;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadFilesService {

    public void subirArchivo (MultipartFile file, Documento doc) throws Exception;

    public void save(MultipartFile file, Documento doc) throws  Exception;

    public void save(List<MultipartFile> files, Documento doc) throws Exception;

}
