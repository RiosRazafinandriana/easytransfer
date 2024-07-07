package com.project.easytransfer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Taux {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idtaux;
    private int montant1;
    private int montant2;
    private String devise;
}
