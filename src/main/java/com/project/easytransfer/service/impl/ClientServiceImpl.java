package com.project.easytransfer.service.impl;

import com.project.easytransfer.dto.ClientDto;
import com.project.easytransfer.models.Client;
import com.project.easytransfer.repository.ClientRepository;
import com.project.easytransfer.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }
    @Override
    public List<ClientDto> findAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map((client) -> mapToClientDto(client)).collect(Collectors.toList());
    }

    @Override
    public Client saveClient(Client client){
        return clientRepository.save(client);
    }

    @Override
    public ClientDto findClientBynumtel(String numtel) {
        Client client = clientRepository.findByNumtel(numtel).get();
        return mapToClientDto(client);
    }

    @Override
    public void updateClient(ClientDto clientDto) {
        Client client = mapToClient(clientDto);
        clientRepository.save(client);
    }

    public boolean doesPhoneNumberExist(String numtel) {
        return clientRepository.findByNumtel(numtel).isPresent();
    }

    @Override
    @Transactional
    public void delete(String numtel) {
        clientRepository.deleteByNumtel(numtel);
    }

    @Override
    public Optional<ClientDto> findClientByNumtel(String numtel) {
        return clientRepository.findClientByNumtel(numtel).map(this::mapToClientDto);
    }

    private Client mapToClient(ClientDto client) {
        Client clientDto = Client.builder()
                .numtel(client.getNumtel())
                .nom(client.getNom())
                .sexe(client.getSexe())
                .pays(client.getPays())
                .solde(client.getSolde())
                .devise(client.getDevise())
                .mail(client.getMail())
                .build();
        return clientDto;
    }

    private ClientDto mapToClientDto(Client client){
        ClientDto clientDto = ClientDto.builder()
                .numtel(client.getNumtel())
                .nom(client.getNom())
                .sexe(client.getSexe())
                .pays(client.getPays())
                .solde(client.getSolde())
                .devise(client.getDevise())
                .mail(client.getMail())
                .build();
        return clientDto;
    }
}
