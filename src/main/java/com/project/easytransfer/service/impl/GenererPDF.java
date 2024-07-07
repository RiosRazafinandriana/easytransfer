package com.project.easytransfer.service.impl;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.project.easytransfer.dto.ClientDto;
import com.project.easytransfer.dto.EnvoyerDto;
import com.project.easytransfer.service.ClientService;
import com.project.easytransfer.service.EnvoyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class GenererPDF {
    private final ClientService clientService;
    private final EnvoyerService envoyerService;

    @Autowired
    public GenererPDF(ClientService clientService, EnvoyerService envoyerService) {
        this.clientService = clientService;
        this.envoyerService = envoyerService;
    }

    public byte[] generatePDF(String numtel, String anneeMois) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

        // Retrieve client information
        ClientDto client = clientService.findClientBynumtel(numtel);

        // Add client information to the PDF
        document.add(new Paragraph("Numéro de téléphone : " + client.getNumtel()));
        document.add(new Paragraph("Nom : " + client.getNom()));
        document.add(new Paragraph("Sexe : " + client.getSexe()));
        document.add(new Paragraph("Pays : " + client.getPays()));
        document.add(new Paragraph("Solde : " + client.getSolde() + " Ar"));
        document.add(new Paragraph("Email : " + client.getMail()));
        document.add(new Paragraph("Date : " + anneeMois));

        // Retrieve transactions
        List<EnvoyerDto> transactions = envoyerService.findTransactionsByNumEnvoyeurAndDate(numtel, anneeMois);

        // Check if transactions are retrieved correctly
        System.out.println("Number of transactions: " + transactions.size());

        // Add a table to the PDF
        float[] pointColumnWidths = {150F, 150F, 150F, 150F};
        Table table = new Table(pointColumnWidths);
        table.addCell("Date");
        table.addCell("Raison");
        table.addCell("Nom du Récepteur");
        table.addCell("Montant");

        // Add transaction data to the table
        int totalMontant = 0;
        String devise = "euros";
        for (EnvoyerDto transaction : transactions) {
            table.addCell(transaction.getDate().toString());
            table.addCell(transaction.getRaison());
            String recepteurName = envoyerService.findRecepteurNameByNumtel(transaction.getNumRecepteur());
            table.addCell(recepteurName);
            table.addCell(String.valueOf(transaction.getMontant()));
            totalMontant += transaction.getMontant();
        }

        // Add the table to the document
        document.add(table);

        // Add total montant outside the table
        document.add(new Paragraph("Total Débit : " + totalMontant + ' ' +devise));

        // Close the document
        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}
