package com.project.easytransfer.controller;

import com.project.easytransfer.dto.FraisDto;
import com.project.easytransfer.models.Frais;
import com.project.easytransfer.service.FraisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class FraisController {
    private FraisService fraisService;

    @Autowired
    public FraisController(FraisService fraisService) {
        this.fraisService = fraisService;
    }

    @GetMapping("/frais")
    public String listFrais(Model model){
        List<FraisDto> frais = fraisService.findAllFrais();
        model.addAttribute("frais", frais);
        return "frais-list";
    }

    @GetMapping("/frais/new")
    public String createFraisForm(Model model) {
        Frais frais = new Frais();
        model.addAttribute("frais", frais);
        return "frais-create";
    }

    @GetMapping("/frais/{idfrais}/delete")
    public String deleteFrais(@PathVariable("idfrais")Long idfrais) {
        fraisService.delete(idfrais);
        return "redirect:/frais";
    }

    @PostMapping("/frais/new")
    public String saveFrais(@ModelAttribute("frais") Frais frais) {
        fraisService.saveFrais(frais);
        return "redirect:/frais";
    }

    @GetMapping("/frais/{idfrais}/edit")
    public String editFraisForm(@PathVariable("idfrais") Long idfrais, Model model) {
        FraisDto frais = fraisService.findFraisByidfrais(idfrais);
        model.addAttribute("frais", frais);
        return "frais-edit";
    }

    @PostMapping("/frais/{idfrais}/edit")
    public String updateFrais(@PathVariable("idfrais") Long idfrais, @ModelAttribute("frais") FraisDto frais) {
        fraisService.updateFrais(frais);
        return "redirect:/frais";
    }
}
