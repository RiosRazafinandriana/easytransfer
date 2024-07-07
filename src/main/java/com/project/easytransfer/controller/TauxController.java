package com.project.easytransfer.controller;

import com.project.easytransfer.dto.TauxDto;
import com.project.easytransfer.models.Taux;
import com.project.easytransfer.service.TauxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TauxController {
    private TauxService tauxService;

    @Autowired
    public TauxController(TauxService tauxService) {
        this.tauxService = tauxService;
    }

    @GetMapping("/taux")
    public String listTaux(Model model){
        List<TauxDto> taux = tauxService.findAllTaux();
        model.addAttribute("taux", taux);
        return "taux-list";
    }

    @GetMapping("/taux/new")
    public String createTauxForm(Model model) {
        Taux taux = new Taux();
        model.addAttribute("taux", taux);
        return "taux-create";
    }

    @GetMapping("/taux/{idtaux}/delete")
    public String deleteTaux(@PathVariable("idtaux")Long idtaux) {
        tauxService.delete(idtaux);
        return "redirect:/taux";
    }

    @PostMapping("/taux/new")
    public String saveTaux(@ModelAttribute("taux") Taux taux) {
        tauxService.saveTaux(taux);
        return "redirect:/taux";
    }

    @GetMapping("/taux/{idtaux}/edit")
    public String editTauxForm(@PathVariable("idtaux") Long idtaux, Model model) {
        TauxDto taux = tauxService.findTauxByidtaux(idtaux);
        model.addAttribute("taux", taux);
        return "taux-edit";
    }

    @PostMapping("/taux/{idtaux}/edit")
    public String updateTaux(@PathVariable("idtaux") Long idtaux, @ModelAttribute("taux") TauxDto taux) {
        tauxService.updateTaux(taux);
        return "redirect:/taux";
    }
}
