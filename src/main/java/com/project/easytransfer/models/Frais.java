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
public class Frais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idfrais;
    private int montant1;
    private int montant2;
    private float firais;
    private String devise;
}
