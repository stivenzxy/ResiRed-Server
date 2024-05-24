package com.project.resiRed.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "resetPasswordToken")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O usa GenerationType.AUTO dependiendo de tu DB
    private Long id;

    @Column(unique = true) // Asegura que el token siga siendo único
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
}
