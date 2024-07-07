package com.project.easytransfer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FraisDto {
    private long idfrais;
    private int montant1;
    private int montant2;
    private float firais;
    private String devise;
}
