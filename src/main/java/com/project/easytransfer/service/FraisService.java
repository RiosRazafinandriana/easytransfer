package com.project.easytransfer.service;

import com.project.easytransfer.dto.FraisDto;
import com.project.easytransfer.models.Frais;

import java.util.List;

public interface FraisService {
    List<FraisDto> findAllFrais();
    Frais saveFrais(Frais frais);

    FraisDto findFraisByidfrais(Long idfrais);

    void updateFrais(FraisDto frais);
    void delete(Long idfrais);
}
