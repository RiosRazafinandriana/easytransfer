package com.project.easytransfer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDto {
    private String numtel;
    private String nom;
    private String sexe;
    private String pays;
    private float solde;
    private String mail;
    private String devise;
}
