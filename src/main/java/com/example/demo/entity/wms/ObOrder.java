package com.example.demo.entity.wms;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "ob_order")
@NoArgsConstructor
public class ObOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customer;

    private String agent;

    private String dest;

    private String carrier;

    private String status;

    private Integer priority;

    private String remark;


}
