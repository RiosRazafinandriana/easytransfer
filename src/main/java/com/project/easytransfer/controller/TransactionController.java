package com.project.easytransfer.controller;

import com.itextpdf.io.IOException;
import com.project.easytransfer.dto.EnvoyerDto;
import com.project.easytransfer.models.Envoyer;
import com.project.easytransfer.service.EnvoyerService;
import com.project.easytransfer.service.impl.GenererPDF;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class TransactionController {

    private EnvoyerService envoyerService;

    @Autowired
    public TransactionController(EnvoyerService envoyerService) {
        this.envoyerService = envoyerService;
    }

    @GetMapping("/envoyers")
    public String listEnvoyers(Model model) {
        List<EnvoyerDto> envoyers = envoyerService.findAllEnvoyer();
        double totalRecettes = envoyerService.calculerTotalRecettes(envoyers);
        model.addAttribute("envoyers", envoyers);
        model.addAttribute("totalRecettes", totalRecettes);
        return "envoyers-list";
    }

    @GetMapping("/envoyers/new")
    public String createEnvoyerForm(Model model) {
        Envoyer envoyer = new Envoyer();
        model.addAttribute("envoyer", envoyer);
        return "envoyers-create";
    }

    @PostMapping("/envoyers/new")
    public String saveEnvoyer(@ModelAttribute("envoyer") Envoyer envoyer, BindingResult result, Model model) {
        envoyerService.saveEnvoyer(envoyer);
        return "redirect:/envoyers";
    }

    @GetMapping("/envoyers/{idEnv}/delete")
    public String deleteEnvoyer(@PathVariable("idEnv") Long idEnv) {
        envoyerService.deleteEnvoyer(idEnv);
        return "redirect:/envoyers";
    }

    @GetMapping("/envoyers/{idEnv}/edit")
    public String editEnvoyerForm(@PathVariable("idEnv") Long idEnv, Model model) {
        EnvoyerDto envoyer = envoyerService.findEnvoyerByIdEnv(idEnv);
        model.addAttribute("envoyer", envoyer);
        return "envoyers-edit";
    }

    @PostMapping("/envoyers/{idEnv}/edit")
    public String updateEnvoyer(@PathVariable("idEnv") Long idEnv, @ModelAttribute("envoyer") EnvoyerDto envoyer) {
        envoyerService.updateEnvoyer(envoyer);
        return "redirect:/envoyers";
    }

    @GetMapping("/envoyers/search")
    public String searchEnvoyerByDate(@RequestParam("date") String date, Model model) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime dateTime = LocalDate.parse(date, formatter).atStartOfDay();
            List<EnvoyerDto> envoyers = envoyerService.findEnvoyerByDate(dateTime);
            model.addAttribute("envoyers", envoyers);

        return "envoyers-list";
    }
}
