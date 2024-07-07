package com.project.easytransfer.service.impl;

import com.project.easytransfer.dto.FraisDto;
import com.project.easytransfer.models.Frais;
import com.project.easytransfer.repository.FraisRepository;
import com.project.easytransfer.service.FraisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FraisServiceImpl implements FraisService{
    private FraisRepository fraisRepository;

    @Autowired
    public FraisServiceImpl (FraisRepository fraisRepository) { this.fraisRepository = fraisRepository; }

    @Override
    public List<FraisDto> findAllFrais() {
        List<Frais> frais = fraisRepository.findAll();
        return frais.stream().map((frais1) -> mapToFraisDto(frais1)).collect(Collectors.toList());
    }

    @Override
    public Frais saveFrais(Frais frais){
        return fraisRepository.save(frais);
    }

    @Override
    public FraisDto findFraisByidfrais(Long idfrais) {
        Frais frais = fraisRepository.findByIdfrais(idfrais).get();
        return mapToFraisDto(frais);
    }

    @Override
    public void updateFrais(FraisDto fraisDto) {
        Frais frais = mapTofrais(fraisDto);
        fraisRepository.save(frais);
    }

    @Override
    @Transactional
    public void delete(Long idfrais) {
        fraisRepository.deleteByIdfrais(idfrais);
    }

    private Frais mapTofrais(FraisDto frais) {
        Frais fraisDto = Frais.builder()
                .idfrais(frais.getIdfrais())
                .montant1(frais.getMontant1())
                .montant2(frais.getMontant2())
                .firais(frais.getFirais())
                .devise(frais.getDevise())
                .build();
        return fraisDto;
    }

    private FraisDto mapToFraisDto(Frais frais){
        FraisDto fraisDto = FraisDto.builder()
                .idfrais(frais.getIdfrais())
                .montant1(frais.getMontant1())
                .montant2(frais.getMontant2())
                .firais(frais.getFirais())
                .devise(frais.getDevise())
                .build();
        return fraisDto;
    }
}
