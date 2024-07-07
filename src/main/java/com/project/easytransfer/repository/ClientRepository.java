package com.project.easytransfer.repository;

import com.project.easytransfer.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByNumtel(String numtel);
    void deleteByNumtel(String numtel);
    @Query("SELECT c FROM Client c WHERE c.numtel = :numtel")
    Optional<Client> findClientByNumtel(@Param("numtel") String numtel);
}
