package com.example.demo.entity.wms;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "stock")
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long batchId;

    private Long containerId;

    /**
     * 未拆封（这组货处在唯一的容器内）
     */
    private Boolean unopened;

    private Integer pcs;

    private String status;

}
