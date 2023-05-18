package com.proyectoSistemaOperativo.sistemaoperativo.controller;


import com.proyectoSistemaOperativo.sistemaoperativo.models.Documento;
import com.proyectoSistemaOperativo.sistemaoperativo.repository.documentoRepository;
import com.proyectoSistemaOperativo.sistemaoperativo.service.UploadFilesService;
import com.proyectoSistemaOperativo.sistemaoperativo.service.UsuarioService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class controller {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UploadFilesService uploadFilesService;

    //Documento documentos = new Documento();
    @Autowired
    private documentoRepository documentoRepository;


    @GetMapping("/")
    public String inicioSesion() {
        return "login";
    }

    @GetMapping("/carga")
    public String cargaArchivo() {
        return "carga";
    }

    @GetMapping("/upload")
    public String update() {
        return "carga";
    }

    @GetMapping("/descarga")
    public String descarga() {
        return "descarga";
    }

    @GetMapping("/consulta")
    public String consulta() {
        return "consulta";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @PostMapping("/home")
    public String login(@Param("username") String username,
                        @Param("password") String password,
                        RedirectAttributes redirectAttrs) {
        if (usuarioService.authenticate(username, password)) {
            return "/home";
        } else {
            redirectAttrs.addFlashAttribute("mensaje", "Credenciales incorrectas")
                    .addFlashAttribute("clase", "danger");
            return "redirect:/";
        }
    }

   /* @PostMapping("/upload")
    public String uploadFile(@RequestParam("archivo") MultipartFile file,
                             RedirectAttributes redirectAttrs,
                             @ModelAttribute("documento") Documento doc, Model model) {
        if (file == null || file.isEmpty()) {
            // Manejo de archivo vacío
            redirectAttrs.addFlashAttribute("mensajearchivo", "Credenciales incorrectas")
                    .addFlashAttribute("clase2", "danger");
            return "/carga";
        }

        try {
            // Generar un nombre único para el archivo
            //String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            // Guardar el archivo en el sistema de archivos
            //Path filePath = Paths.get("/sistemaoperativo/archivos/" + fileName);
            //Files.copy(file.getInputStream(), filePath);

            StringBuilder builder = new StringBuilder();
            builder.append(System.getProperty("user.home"));
            builder.append(File.separator);
            builder.append("upload");
            builder.append(File.separator);
            builder.append(fileName + "-" + file.getOriginalFilename());

            byte[] fileByte = file.getBytes();
            Path patn = Paths.get(builder.toString());
            Files.write(patn, fileByte);
            *//*int id = 1;
            doc.setDocnum(id);
            doc.setRuta(builder.toString());
            System.out.println(doc.getDocnum());
            System.out.println(doc.getRuta());*//*
            Date date = Date.from(Instant.now());
            doc.setFechaingresosistema(date);
            //System.out.println(date);
            doc.setRuta(builder.toString());
            // Guardar la ruta del archivo en la base de datos
            documentoRepository.save(doc);
            //archivoService.guardarRutaArchivo(builder.toString());

            // Redireccionar a una página de éxito o mostrar un mensaje de éxito
            return "redirect:/carga";
        } catch (IOException e) {
            // Manejo de error de E/S
            return "redirect:/upload?error";
        }
    }*/

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("archivo") List<MultipartFile> file,
                             RedirectAttributes redirectAttrs,
                             @ModelAttribute("documento") Documento doc,
                             Model model) throws Exception {

        if (file == null || file.isEmpty()) {
            // Manejo de archivo vacío
            redirectAttrs.addFlashAttribute("mensajearchivo", "Selecciones archivo")
                    .addFlashAttribute("clase2", "danger");
            return "redirect:/carga";

        } else {
            Date date = Date.from(Instant.now());
            doc.setFechaingresosistema(date);
            //uploadFilesService.subirArchivo(file, doc);
            uploadFilesService.save(file, doc);
            return "home";
        }


    }
}