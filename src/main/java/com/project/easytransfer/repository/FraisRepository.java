package com.project.easytransfer.repository;

import com.project.easytransfer.models.Frais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FraisRepository extends JpaRepository<Frais, Long> {
    Optional<Frais> findByIdfrais(Long idfrais);
    void deleteByIdfrais(Long idfrais);
}
