package com.project.easytransfer.controller;

import com.itextpdf.io.IOException;
import com.project.easytransfer.dto.ClientDto;
import com.project.easytransfer.models.Client;
import com.project.easytransfer.service.ClientService;
import com.project.easytransfer.service.impl.GenererPDF;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {
    private ClientService clientService;
    private final GenererPDF genererPDF;

    @Autowired
    public ClientController(ClientService clientService, GenererPDF genererPDF) {
        this.clientService = clientService;
        this.genererPDF = genererPDF;
    }

    @GetMapping("/clients")
    public String listClients(Model model){
        List<ClientDto> clients = clientService.findAllClients();
        model.addAttribute("clients", clients);
        return "clients-list";
    }

    @GetMapping("/clients/new")
    public String createClientForm(Model model) {
        Client client = new Client();
        model.addAttribute("client", client);
        return "clients-create";
    }

    @GetMapping("/clients/{numtel}/delete")
    public String deleteClient(@PathVariable("numtel")String numtel) {
        clientService.delete(numtel);
        return "redirect:/clients";
    }

    @PostMapping("/clients/new")
    public String saveClient(@ModelAttribute("client") Client client, BindingResult result, Model model) {
        if (clientService.doesPhoneNumberExist(client.getNumtel())) {
            result.rejectValue("numtel", "error.client", "Ce numéro de téléphone existe déjà");
            return "clients-create";
        }

        clientService.saveClient(client);
        return "redirect:/clients";
    }

    @GetMapping("/clients/{numtel}/edit")
    public String editClientForm(@PathVariable("numtel") String numtel, Model model) {
        ClientDto client = clientService.findClientBynumtel(numtel);
        model.addAttribute("client", client);
        return "clients-edit";
    }

    @PostMapping("/clients/{numtel}/edit")
    public String updateClient(@PathVariable("numtel") String numtel, @ModelAttribute("client") ClientDto client) {
        clientService.updateClient(client);
        return "redirect:/clients";
    }

    @GetMapping("/clients/search")
    public String searchClient(@RequestParam("numtel") String numtel, Model model) {
        Optional<ClientDto> clients = clientService.findClientByNumtel(numtel);
        model.addAttribute("clients", clients.orElse(null));
        return "clients-list";
    }


    @GetMapping("/generatePDF/{numtel}")
    public String requestDate(@PathVariable("numtel") String numtel, Model model) {
        model.addAttribute("numtel", numtel);
        return "année-mois-formulaire"; // View name for the form to input date
    }

    @PostMapping("/generatePDF")
    public void generateRelevePDF(@RequestParam("numtel") String numtel,
                                  @RequestParam("anneeMois") String anneeMois,
                                  HttpServletResponse response) throws IOException, java.io.IOException {
        // Pass the numtel and anneeMois to the PDF generation service
        byte[] pdfBytes = genererPDF.generatePDF(numtel, anneeMois);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=releve.pdf");
        response.getOutputStream().write(pdfBytes);
    }

}
