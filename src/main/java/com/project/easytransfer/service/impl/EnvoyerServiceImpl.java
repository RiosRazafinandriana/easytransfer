package com.project.easytransfer.service.impl;

import com.project.easytransfer.dto.EnvoyerDto;
import com.project.easytransfer.models.Client;
import com.project.easytransfer.models.Envoyer;
import com.project.easytransfer.models.Frais;
import com.project.easytransfer.models.Taux;
import com.project.easytransfer.repository.ClientRepository;
import com.project.easytransfer.repository.EnvoyerRepository;
import com.project.easytransfer.repository.FraisRepository;
import com.project.easytransfer.repository.TauxRepository;
import com.project.easytransfer.service.ClientService;
import com.project.easytransfer.service.EnvoyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnvoyerServiceImpl implements EnvoyerService {
    private final EnvoyerRepository envoyerRepository;
    private final ClientRepository clientRepository;
    private final FraisRepository fraisRepository;
    private final ClientService clientService;
    private final TauxRepository tauxRepository;

    @Autowired
    public EnvoyerServiceImpl(EnvoyerRepository envoyerRepository, ClientRepository clientRepository, FraisRepository fraisRepository, ClientService clientService, TauxRepository tauxRepository) {
        this.envoyerRepository = envoyerRepository;
        this.clientRepository = clientRepository;
        this.fraisRepository = fraisRepository;
        this.clientService = clientService;
        this.tauxRepository = tauxRepository;
    }

    @Override
    public List<EnvoyerDto> findAllEnvoyer() {
        List<Envoyer> envoyers = envoyerRepository.findAll();
        return envoyers.stream().map(this::mapToEnvoyerDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Envoyer saveEnvoyer(Envoyer envoyer) {
        // Récupérer l'envoyeur et le récepteur
        Client envoyeur = clientRepository.findByNumtel(envoyer.getEnvoyeur().getNumtel())
                .orElseThrow(() -> new IllegalArgumentException("Envoyeur non trouvé"));
        Client recepteur = clientRepository.findByNumtel(envoyer.getRecepteur().getNumtel())
                .orElseThrow(() -> new IllegalArgumentException("Recepteur non trouvé"));
        List<Frais> fraisList = fraisRepository.findAll();
        List<Taux> tauxList = tauxRepository.findAll();

        float valfrais = 0;
        int montant = envoyer.getMontant();
        String devise = envoyer.getDevise();

        // Vérifier que les soldes sont suffisants
        if (envoyeur.getSolde() < envoyer.getMontant()) {
            throw new IllegalArgumentException("Solde insuffisant pour l'envoyeur");
        }

        Integer soldFinal = null;
        for (Frais frais : fraisList) {
            for (Taux taux : tauxList) {
                if (montant >= frais.getMontant1() && montant <= frais.getMontant2()
                        && devise.equals(frais.getDevise()) && devise.equals(taux.getDevise())) {
                    valfrais = frais.getFirais();
                    soldFinal = taux.getMontant2();
                    break;
                }
            }
        }

        if (soldFinal == null) {
            throw new IllegalArgumentException("Les frais ou le taux ne peuvent pas être calculés pour le montant et la devise donnés.");
        }

        // Mettre à jour les soldes
        envoyeur.setSolde((envoyeur.getSolde() - envoyer.getMontant() - valfrais));
        recepteur.setSolde((recepteur.getSolde() + (envoyer.getMontant() * soldFinal)));

        // Sauvegarder les clients mis à jour
        clientRepository.save(envoyeur);
        clientRepository.save(recepteur);

        // Sauvegarder la transaction
        return envoyerRepository.save(envoyer);
    }


    @Override
    public EnvoyerDto findEnvoyerByIdEnv(Long idEnv) {
        Envoyer envoyer = envoyerRepository.findByIdEnv(idEnv).get();
        return mapToEnvoyerDto(envoyer);
    }

    @Override
    @Transactional
    public void updateEnvoyer(EnvoyerDto envoyerDto) {
        Envoyer envoyer = mapToEnvoyer(envoyerDto);
        envoyerRepository.save(envoyer);
    }

    @Override
    @Transactional
    public void deleteEnvoyer(Long idEnv) {
        envoyerRepository.deleteByIdEnv(idEnv);
    }

    @Override
    public List<EnvoyerDto> findEnvoyerByDate(LocalDateTime date) {
        return envoyerRepository.findEnvoyerByDate(date).stream()
                .map(this::mapToEnvoyerDto)
                .collect(Collectors.toList());
    }

    private Envoyer mapToEnvoyer(EnvoyerDto envoyerDto) {
        Client envoyeur = clientRepository.findById(envoyerDto.getNumEnvoyeur()).orElse(null);
        Client recepteur = clientRepository.findById(envoyerDto.getNumRecepteur()).orElse(null);
        return Envoyer.builder()
                .idEnv(envoyerDto.getIdEnv())
                .date(envoyerDto.getDate())
                .montant(envoyerDto.getMontant())
                .raison(envoyerDto.getRaison())
                .envoyeur(envoyeur)
                .recepteur(recepteur)
                .devise(envoyerDto.getDevise())
                .build();
    }

    private EnvoyerDto mapToEnvoyerDto(Envoyer envoyer) {
        return EnvoyerDto.builder()
                .idEnv(envoyer.getIdEnv())
                .date(envoyer.getDate())
                .montant(envoyer.getMontant())
                .raison(envoyer.getRaison())
                .numEnvoyeur(envoyer.getEnvoyeur().getNumtel())
                .numRecepteur(envoyer.getRecepteur().getNumtel())
                .devise(envoyer.getDevise())
                .build();
    }

    public double calculerTotalRecettes(List<EnvoyerDto> envoyers) {
        List<Frais> fraisList = fraisRepository.findAll();

        double totalRecettes = 0.0;

        for (EnvoyerDto envoyer : envoyers) {
            int montant = envoyer.getMontant();
            for (Frais frais : fraisList) {
                if (montant >= frais.getMontant1() && montant <= frais.getMontant2()) {
                    totalRecettes += frais.getFirais();
                    break;
                }
            }
        }

        return totalRecettes;
    }

    @Override
    public List<EnvoyerDto> findTransactionsByNumEnvoyeurAndDate(String numEnvoyeur, String date) {
        List<Envoyer> transactions = envoyerRepository.findByNumEnvoyeurAndDate(numEnvoyeur, date);
        return transactions.stream()
                .map(transaction -> EnvoyerDto.builder()
                        .idEnv(transaction.getIdEnv())
                        .numEnvoyeur(transaction.getEnvoyeur().getNumtel())
                        .numRecepteur(transaction.getRecepteur().getNumtel())
                        .montant(transaction.getMontant())
                        .date(transaction.getDate())
                        .raison(transaction.getRaison())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public String findRecepteurNameByNumtel(String numtel) {
        return clientService.findClientBynumtel(numtel).getNom();
    }

}

