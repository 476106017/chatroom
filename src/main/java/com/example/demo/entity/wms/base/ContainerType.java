package com.example.demo.entity.wms.base;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "containerType")
@NoArgsConstructor
public class ContainerType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String desc;

    private String category;


    private Double weightCapacity;

    private Double volumeCapacity;

    private Double height;

    private String dimensions;
}
