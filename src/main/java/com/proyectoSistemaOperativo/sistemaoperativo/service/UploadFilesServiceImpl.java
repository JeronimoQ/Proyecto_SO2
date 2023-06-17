package com.proyectoSistemaOperativo.sistemaoperativo.service;

import com.proyectoSistemaOperativo.sistemaoperativo.models.Documento;
import com.proyectoSistemaOperativo.sistemaoperativo.models.RegistroActividad;
import com.proyectoSistemaOperativo.sistemaoperativo.models.Usuario;
import com.proyectoSistemaOperativo.sistemaoperativo.models.UsuarioDocumento;
import com.proyectoSistemaOperativo.sistemaoperativo.repository.RegistroActividadRepository;
import com.proyectoSistemaOperativo.sistemaoperativo.repository.UsuarioDocumentoRepository;
import com.proyectoSistemaOperativo.sistemaoperativo.repository.documentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UploadFilesServiceImpl implements UploadFilesService {

    @Autowired
    private documentoRepository documentoRepository;

    @Autowired
    private UsuarioDocumentoRepository usuarioDocumentoRepository;

    @Autowired
    private RegistroActividadRepository registroActividadRepository;

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
    public void save(MultipartFile file, Documento doc) {
        Documento documento = new Documento();
        UsuarioDocumento usuarioDocumento = new UsuarioDocumento();
        RegistroActividad registroActividad = new RegistroActividad();
        String nombreArchivoNuevo = UUID.randomUUID().toString();

        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

       /* byte[] fileByte = new byte[0];
        try {
            fileByte = file.getBytes();
            Path path = Paths.get("/sistemaoperativo/archivo/" + nuevoNomArchivo);
            Files.write(path, fileByte);
            usuarioDocumento.setRutadocumento(path.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }*/


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
        try {
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        documento.setNumerdoc(doc.getNumerdoc());
        documento.setFechaelaboracion(doc.getFechaelaboracion());
        documento.setPropietariodocumento(doc.getPropietariodocumento());
        documento.setFechaingresosistema(new Date());


        documentoRepository.save(documento);

        usuarioDocumento.setUsuario(new Usuario());
        usuarioDocumento.getUsuario().setDpi(123456789);
        usuarioDocumento.setDocumento(new Documento());
        usuarioDocumento.getDocumento().setNumerdoc(doc.getNumerdoc());
        usuarioDocumento.setRutadocumento(path.toString());
        //usuarioDocumento.getUsuario().setUsername("Angel");
        usuarioDocumento.setNombredocumentooriginal(nombreArchivoOriginal);
        usuarioDocumento.setNuevonombre(nuevoNomArchivo);

        usuarioDocumentoRepository.save(usuarioDocumento);

        registroActividad.setFechaRegistro(new Date());
        registroActividad.setAccion("carga");
        registroActividad.setDocumento(new Documento());
        registroActividad.getDocumento().setNumerdoc(doc.getNumerdoc());
        registroActividad.setUsuario(new Usuario());
        registroActividad.getUsuario().setDpi(123456789);
        registroActividad.setIp("1");

        registroActividadRepository.save(registroActividad);

    }

    @Override
    public void save(List<MultipartFile> files, Documento doc) {

        for (MultipartFile file : files) {
            this.save(file, doc);

        }

    }
}
