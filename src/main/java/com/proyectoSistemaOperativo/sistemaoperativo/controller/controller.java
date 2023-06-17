package com.proyectoSistemaOperativo.sistemaoperativo.controller;


import com.proyectoSistemaOperativo.sistemaoperativo.models.Documento;
import com.proyectoSistemaOperativo.sistemaoperativo.models.RegistroActividad;
import com.proyectoSistemaOperativo.sistemaoperativo.models.Usuario;
import com.proyectoSistemaOperativo.sistemaoperativo.models.UsuarioDocumento;
import com.proyectoSistemaOperativo.sistemaoperativo.repository.RegistroActividadRepository;
import com.proyectoSistemaOperativo.sistemaoperativo.repository.UsuarioDocumentoRepository;
import com.proyectoSistemaOperativo.sistemaoperativo.repository.documentoRepository;
import com.proyectoSistemaOperativo.sistemaoperativo.service.UploadFilesService;
import com.proyectoSistemaOperativo.sistemaoperativo.service.UsuarioService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Controller
public class controller {
    private static final Logger logger = LoggerFactory.getLogger(controller.class);
    @Autowired
    private documentoRepository documentoRepository;

    @Autowired
    private UsuarioDocumentoRepository usuarioDocumentoRepository;

    @Autowired
    private RegistroActividadRepository registroActividadRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UploadFilesService uploadFilesService;

    private UsuarioDocumento usuarioDocumento;
    private RegistroActividad registroActividad;
    //Documento documentos = new Documento();


    @GetMapping("/")
    public String inicioSesion() {
        logger.info("inicio de sesión");
        return "login";
    }

    @GetMapping("/login")
    public String inicioSesionn() {
        logger.info("inicio de sesión");
        logger.info("Se a realizado una accion GET inicio de sesión");
        return "login";
    }

    @GetMapping("/carga")
    public String cargaArchivo() {
        logger.info("Carga de archivos");
        return "carga";
    }

    @GetMapping("/upload")
    public String update() {
        logger.info("Carga de archivos");
        return "carga";
    }

    @GetMapping("/descarga")
    public String descarga() {
        logger.info("Descarga de archivos");
        return "descarga";
    }

    @GetMapping("/consulta")
    public String consulta() {
        logger.info("Consultas de archivos");
        return "consulta";
    }

    @GetMapping("/home")
    public String home() {
        logger.info("Pagina principal");
        return "home";
    }

    @PostMapping("/home")
    public String login(@Param("username") String username,
                        @Param("password") String password,
                        RedirectAttributes redirectAttrs) {
        logger.info("inicio de sesion");
        if (usuarioService.authenticate(username, password)) {
            logger.info("inicio de sesion exitosa");
            return "/home";
        } else {
            logger.info("credenciales invalidos");
            redirectAttrs.addFlashAttribute("mensaje", "Credenciales incorrectas")
                    .addFlashAttribute("clase", "danger");
            return "redirect:/";
        }
    }


    @PostMapping("/upload")
    public String uploadFile(@RequestParam("archivo") List<MultipartFile> files,
                             RedirectAttributes redirectAttrs,
                             @ModelAttribute("documento") Documento doc,
                             Model model) {
        logger.info("inicio de carga de archivos");

        if (files == null || files.isEmpty()) {
            logger.info("No se selecciono archivos");
            // Manejo de archivo vacío
            redirectAttrs.addFlashAttribute("mensajearchivo", "Selecciones archivo")
                    .addFlashAttribute("clase2", "danger");
            return "redirect:/carga";

        } else {

            try {
                logger.info("Carga de archivo exitoso");
                uploadFilesService.save(files, doc);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "home";

        }
    }

    @PostMapping("/descarga")
    public String getFile(@RequestParam("propietario") String propietario,
                          @RequestParam("fechainicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
                          @RequestParam("fechafin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
                          @RequestParam("accion") String accion,
                          @RequestParam(value = "documentosSeleccionados", required = false)
                                  List<String> documentosSeleccionados,
                          RedirectAttributes redirectAttrs,
                          HttpServletResponse response,
                          Model model) {
        logger.info("inicio de descarga");

        switch (accion) {
            case "buscar":
                logger.info("Busqueda de archivos");
                List<UsuarioDocumento> resultados = documentoRepository.findUsuarioDocumentosByPropietarioAndFechas(propietario, fechaInicio, fechaFin);

                model.addAttribute("resultados", resultados);
                break;
            case "descargar":
                RegistroActividad registro = new RegistroActividad();
                List<UsuarioDocumento> usuarioDocumentos = usuarioDocumentoRepository.findAllByNuevonombreIn(documentosSeleccionados);
                List<Resource> recursosArchivos = new ArrayList<>();
                //List<Documento> doc = new ArrayList<>();
                for (UsuarioDocumento documento : usuarioDocumentos) {
                   /* System.out.println("ID: " + documento.getId());
                    System.out.println("Nombre original del documento: " + documento.getNombredocumentooriginal());
                    System.out.println("Nuevo nombre: " + documento.getNuevonombre());
                    System.out.println("Ruta del documento: " + documento.getRutadocumento());
                    System.out.println("Usuario: " + documento.getUsuario().getNombre());
                    System.out.println("Propietario: " + documento.getDocumento().getPropietariodocumento());
                    System.out.println("----------------------------------");
                    doc.add(documento.getDocumento());*/

                    String rutaArchivo = documento.getRutadocumento();
                    Path archivoPath = Paths.get(rutaArchivo);
                    Resource archivoResource = new FileSystemResource(archivoPath);
                    recursosArchivos.add(archivoResource);

                }

                for (UsuarioDocumento reg : usuarioDocumentos) {
                    registro.setUsuario(new Usuario());
                    registro.getUsuario().setDpi(123456789);
                    registro.setAccion("Descarga");
                    registro.setIp("1");
                    registro.setDocumento(new Documento());
                    registro.setFechaRegistro(new Date());
                    registro.getDocumento().setNumerdoc(reg.getDocumento().getNumerdoc());


                    registroActividadRepository.save(registro);

                }

                /*for(Resource docs : recursosArchivos){
                    System.out.println("Nombre: " + docs.getFilename());
                    System.out.println("----------------------------------");

                }*/
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= " + usuarioDocumentos.get(0).getDocumento().getNumerdoc() + "-" + usuarioDocumentos.get(0).getDocumento().getPropietariodocumento() + ".zip");
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

                try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
                    for (Resource archivoResource : recursosArchivos) {
                        ZipEntry zipEntry = new ZipEntry(archivoResource.getFilename());
                        zipOutputStream.putNextEntry(zipEntry);
                        FileCopyUtils.copy(archivoResource.getInputStream(), zipOutputStream);

                    }
                    zipOutputStream.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                logger.info("Descarga de archivo exitoso");

                break;

            default:

        }

        return "descarga";
    }

//    @PostMapping("/descargaarch")
//    public void descargarArchivos(@RequestParam(value = "documentosSeleccionados", required = false)
//                                          List<String> documentosSeleccionados,
//                                  HttpServletResponse response) throws IOException {
//
//        // Obtener los documentos seleccionados desde la base de datos
//        List<Documento> documentos = documentoRepository.findAllByNumerdocIn(documentosSeleccionados);
//        System.out.println(documentos);
//        /*// Crear una lista de recursos para los archivos seleccionados
//        List<Resource> recursosArchivos = new ArrayList<>();
//        for (UsuarioDocumento documento : documentos) {
//            String rutaArchivo = "/sistemaoperativo/archivo/" + usuarioDocumento.getRutadocumento(); // Reemplaza "ruta/de/tus/archivos/" con la ruta real de tus archivos
//            Path archivoPath = Paths.get(rutaArchivo);
//            Resource archivoResource = new FileSystemResource(archivoPath);
//            recursosArchivos.add(archivoResource);
//        }
//
//        // Establecer cabeceras de respuesta para el archivo ZIP
//        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=archivos.zip");
//        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//
//        // Crear un flujo de salida para el archivo ZIP
//        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
//            // Agregar los recursos de los archivos seleccionados al archivo ZIP
//            for (Resource archivoResource : recursosArchivos) {
//                ZipEntry zipEntry = new ZipEntry(archivoResource.getFilename());
//                zipOutputStream.putNextEntry(zipEntry);
//                FileCopyUtils.copy(archivoResource.getInputStream(), zipOutputStream);
//                zipOutputStream.closeEntry();
//            }
//        }*/
//    }


}