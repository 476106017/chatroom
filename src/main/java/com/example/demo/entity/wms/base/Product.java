package com.example.demo.entity.wms.base;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "product")
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String desc;

    private String brand;

    private String category;

    private BigDecimal price;


    private Double weight;

    private Double height;

    private Double volume;

    private String dimensions;


}
