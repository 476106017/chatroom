package com.example.demo.entity.wms;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "container")
@NoArgsConstructor
public class Container {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 容器/库位 条码
     */
    private String barcode;

    private String location;

    private Long parentId;

    private String status;


    private Double weight;

    private Double volume;

    private Double height;

    private String dimensions;
}
