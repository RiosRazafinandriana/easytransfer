package com.project.easytransfer.service;

import com.project.easytransfer.dto.TauxDto;
import com.project.easytransfer.models.Taux;

import java.util.List;

public interface TauxService {
    List<TauxDto> findAllTaux();
    Taux saveTaux(Taux taux);

    TauxDto findTauxByidtaux(Long idtaux);

    void updateTaux(TauxDto taux);
    void delete(Long idtaux);
}
