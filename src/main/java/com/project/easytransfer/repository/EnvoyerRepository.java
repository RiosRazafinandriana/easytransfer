package com.project.easytransfer.repository;

import com.project.easytransfer.models.Envoyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnvoyerRepository extends JpaRepository<Envoyer, Long> {
    Optional<Envoyer> findByIdEnv(Long idEnv);
    void deleteByIdEnv(Long idenv);
    @Query("SELECT e FROM Envoyer e WHERE DATE(e.date) = :date")
    List<Envoyer> findEnvoyerByDate(@Param("date") LocalDateTime date);
    @Query("SELECT e FROM Envoyer e WHERE e.envoyeur.numtel = :numEnvoyeur AND to_char(e.date, 'YYYY-MM') = :date")
    List<Envoyer> findByNumEnvoyeurAndDate(@Param("numEnvoyeur") String numEnvoyeur, @Param("date") String date);
}
