package com.project.easytransfer.service;

import com.project.easytransfer.dto.ClientDto;
import com.project.easytransfer.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<ClientDto> findAllClients();
    Client saveClient(Client client);

    ClientDto findClientBynumtel(String numtel);

    void updateClient(ClientDto client);

    boolean doesPhoneNumberExist(String numtel);

    void delete(String numtel);
    public Optional<ClientDto> findClientByNumtel(String numtel);
}
