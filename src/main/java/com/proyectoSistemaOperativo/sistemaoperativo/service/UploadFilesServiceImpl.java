package com.proyectoSistemaOperativo.sistemaoperativo.service;

import com.proyectoSistemaOperativo.sistemaoperativo.models.Documento;
import com.proyectoSistemaOperativo.sistemaoperativo.models.UsuarioDocumento;
import com.proyectoSistemaOperativo.sistemaoperativo.repository.UsuarioDocumentoRepository;
import com.proyectoSistemaOperativo.sistemaoperativo.repository.documentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class UploadFilesServiceImpl implements UploadFilesService {

    @Autowired
    private documentoRepository documentoRepository;

    @Autowired
    private UsuarioDocumentoRepository usuarioDocumentoRepository;

    @Override
    public void subirArchivo(MultipartFile file, Documento doc) throws Exception {
        //String mensaje = "";
        try {
            // nombre aleatorio para el archivo
            //String nombreArchivoNuevo = UUID.randomUUID().toString();

            // archivo en byte
            //byte[] bytes = file.getBytes();

            // nombre original del archivo
            //String nombreArchivoOriginal = file.getOriginalFilename();

            /*//capturamos el tamaño del archivo
            long tamañoArchivo = file.getSize();

            //tamaño permitido 5MB
            long tamañoMaximoArchivo = 5 * 1024 * 1024;

            //comprobando tamaño del archivo
            if (tamañoArchivo > tamañoMaximoArchivo) {
                return mensaje = "El archivo es mayor de 5 MB";
            }*/

            //comprobando si el archivo es pdf
           /* if (!nombreArchivoOriginal.endsWith(".pdf")) {
                return mensaje = "Ingrese solo archivos pdf";
            }*/

            //capturamos la extencion del archivo
            //String archivoExtension = nombreArchivoOriginal.substring(nombreArchivoOriginal.lastIndexOf("."));

            //nuevo nombre para el archivo
            //String nuevoNomArchivo = nombreArchivoNuevo + archivoExtension;

            //creando ruta para el archivo
            /*StringBuilder builder = new StringBuilder();
            builder.append(System.getProperty("user.home"));
            builder.append(File.separator);
            builder.append("upload");*/
            //builder.append(File.separator);
            //builder.append(nuevoNomArchivo);

            /*File folder = new File(builder.toString());
            if (!folder.exists()) {
                folder.mkdirs();
            }

            Path path = Paths.get(builder.append(File.separator) + nuevoNomArchivo);
            Files.write(path, bytes);
            //mensaje = "archivo cargado con exito";

            doc.setNombre(nombreArchivoOriginal);
            doc.setRuta(path.toString());
            documentoRepository.save(doc);*/
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public void save(MultipartFile file, Documento doc) throws Exception {
        UsuarioDocumento usuarioDocumento = new UsuarioDocumento();
        String nombreArchivoNuevo = UUID.randomUUID().toString();
        byte[] bytes = file.getBytes();
        String nombreArchivoOriginal = file.getOriginalFilename();
        String archivoExtension = nombreArchivoOriginal.substring(nombreArchivoOriginal.lastIndexOf("."));
        String nuevoNomArchivo = nombreArchivoNuevo + archivoExtension;

        //creando ruta para el archivo
        StringBuilder builder = new StringBuilder();
        builder.append(System.getProperty("user.home"));
        builder.append(File.separator);
        builder.append("upload");
        //builder.append(File.separator);
        //builder.append(nuevoNomArchivo);

        File folder = new File(builder.toString());
        if (!folder.exists()) {
            folder.mkdirs();
        }
        builder.append(File.separator);
        builder.append(doc.getNumerdoc() + " - " + doc.getPropietariodocumento());

        File folder2 = new File(builder.toString());
        if (!folder2.exists()) {
            folder2.mkdirs();
        }

        Path path = Paths.get(builder.append(File.separator) + nuevoNomArchivo);
        Files.write(path, bytes);

        //usuarioDocumento.setUsuario();
        //usuarioDocumento.setNumerodocumento(doc.getNumerdoc());
        usuarioDocumento.setDocumento(doc);
        usuarioDocumento.setRutadocumento(path.toString());
        usuarioDocumento.setNombredocumento(nombreArchivoOriginal);

        usuarioDocumentoRepository.save(usuarioDocumento);


        documentoRepository.save(doc);

    }

    @Override
    public void save(List<MultipartFile> files, Documento doc) throws Exception {

        for (MultipartFile file : files) {
            this.save(file, doc);

        }

    }
}
