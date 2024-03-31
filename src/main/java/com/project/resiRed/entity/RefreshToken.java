package com.project.resiRed.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O usa GenerationType.AUTO dependiendo de tu DB
    private Long id;

    @Column(unique = true) // Asegura que el token siga siendo único
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    private Date expirationDate;

    // Constructor, Getters y Setters
}

