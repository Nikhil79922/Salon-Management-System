package com.example.salon_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "salons")
public class Salon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 14)
    private String phoneNumber;

    @Column(nullable = false, length = 254)
    private String email;

    @Column(nullable = false, length = 50)
    private String city;

    @Column (nullable = false)
    private Long ownerId;

    @Column (nullable = false)
    private LocalTime openTime;

    @Column (nullable = false)
    private LocalTime closeTime;
}
