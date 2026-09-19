package com.example.service_offering_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "service_offering")
public class ServiceOffering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;


    @Column(nullable = false, length = 500)
    private String description;

    @Column (nullable = false)
    private int price;

    @Column (nullable = false)
    private int duration;

    @Column (nullable = false)
    private Long salonId;

    @Column (nullable = false)
    private Long categoryId;

    private String image;
}
