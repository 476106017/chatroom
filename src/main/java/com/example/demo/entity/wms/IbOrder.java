package com.example.demo.entity.wms;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "ib_order")
@NoArgsConstructor
public class IbOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String supplier;

    private String owner;

    private String status;

    private Integer priority;

    private String remark;
}
