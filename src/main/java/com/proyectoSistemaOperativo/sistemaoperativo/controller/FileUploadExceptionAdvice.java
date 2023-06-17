/*package com.proyectoSistemaOperativo.sistemaoperativo.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("mensajearchivo", "Archivo mayor a 5MB")
                .addFlashAttribute("clase2", "danger");

        return "redirect:/carga";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex,RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensajearchivo", "Archivo mayor a 5MB")
                .addFlashAttribute("clase2", "danger");

        return "redirect:/carga";    }
}*/
