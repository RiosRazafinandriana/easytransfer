package com.project.easytransfer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class Envoyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEnv;
    @ManyToOne
    @JoinColumn(name = "numEnvoyeur")
    private Client envoyeur;
    @ManyToOne
    @JoinColumn(name = "numRecepteur")
    private Client recepteur;
    private int montant;
    private String devise;
    @CreationTimestamp
    private LocalDate date;
    private String raison;

    @PrePersist
    protected void onCreate() {
        if (date == null) {
            date = LocalDate.now();
        }
    }

}
