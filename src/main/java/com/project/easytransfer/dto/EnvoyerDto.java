package com.project.easytransfer.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class EnvoyerDto {
    private Long idEnv;
    private String numEnvoyeur;
    private String numRecepteur;
    private int montant;
    private LocalDate date;
    private String raison;
    private String devise;
}
