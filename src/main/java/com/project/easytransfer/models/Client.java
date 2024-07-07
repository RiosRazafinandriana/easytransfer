package com.project.easytransfer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class Client {
    @Id
    private String numtel;
    private String nom;
    private String sexe;
    private String pays;
    private float solde;
    private String devise;
    private String mail;
}
