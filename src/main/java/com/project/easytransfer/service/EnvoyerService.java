package com.project.easytransfer.service;

import com.project.easytransfer.dto.EnvoyerDto;
import com.project.easytransfer.models.Envoyer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;

public interface EnvoyerService {
    List<EnvoyerDto> findAllEnvoyer();
    Envoyer saveEnvoyer(Envoyer envoyer);
    EnvoyerDto findEnvoyerByIdEnv(Long idEnv);
    void updateEnvoyer(EnvoyerDto envoyer);
    void deleteEnvoyer(Long idEnv);
    public List<EnvoyerDto> findEnvoyerByDate(LocalDateTime date);
    public double calculerTotalRecettes(List<EnvoyerDto> envoyers);
    List<EnvoyerDto> findTransactionsByNumEnvoyeurAndDate(String numEnvoyeur, String date);
    String findRecepteurNameByNumtel(String numtel);
}
