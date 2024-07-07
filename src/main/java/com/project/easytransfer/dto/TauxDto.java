package com.project.easytransfer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TauxDto {
    private long idtaux;
    private int montant1;
    private int montant2;
    private String devise;
}
