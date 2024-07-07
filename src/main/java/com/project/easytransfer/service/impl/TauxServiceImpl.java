package com.project.easytransfer.service.impl;

import com.project.easytransfer.dto.TauxDto;
import com.project.easytransfer.models.Taux;
import com.project.easytransfer.repository.TauxRepository;
import com.project.easytransfer.service.TauxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TauxServiceImpl implements TauxService {
    private TauxRepository tauxRepository;

    @Autowired
    public TauxServiceImpl(TauxRepository tauxRepository) {
        this.tauxRepository = tauxRepository;
    }

    @Override
    public List<TauxDto> findAllTaux() {
        List<Taux> taux = tauxRepository.findAll();
        return taux.stream().map((taux1) -> mapToTauxDto(taux1)).collect(Collectors.toList());
    }

    @Override
    public Taux saveTaux(Taux taux){
        return tauxRepository.save(taux);
    }

    @Override
    public TauxDto findTauxByidtaux(Long idtaux) {
        Taux taux = tauxRepository.findByIdtaux(idtaux).get();
        return mapToTauxDto(taux);
    }

    @Override
    public void updateTaux(TauxDto tauxDto) {
        Taux taux = mapToTaux(tauxDto);
        tauxRepository.save(taux);
    }

    @Override
    @Transactional
    public void delete(Long idtaux) {
        tauxRepository.deleteByIdtaux(idtaux);
    }

    private Taux mapToTaux(TauxDto taux) {
        Taux tauxDto = Taux.builder()
                .idtaux(taux.getIdtaux())
                .montant1(taux.getMontant1())
                .montant2(taux.getMontant2())
                .devise(taux.getDevise())
                .build();
        return tauxDto;
    }

    private TauxDto mapToTauxDto(Taux taux){
        TauxDto tauxDto = TauxDto.builder()
                .idtaux(taux.getIdtaux())
                .montant1(taux.getMontant1())
                .montant2(taux.getMontant2())
                .devise(taux.getDevise())
                .build();
        return tauxDto;
    }
}
