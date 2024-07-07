package com.project.easytransfer.repository;

import com.project.easytransfer.models.Frais;
import com.project.easytransfer.models.Taux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TauxRepository extends JpaRepository<Taux, Long> {
    Optional<Taux> findByIdtaux(Long idtaux);
    void deleteByIdtaux(Long idtaux);
}
