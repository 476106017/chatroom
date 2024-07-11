package com.example.demo.entity.wms;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "ib_order_detail")
@NoArgsConstructor
public class IbOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private Long batchId;

    private Integer pcs;

    private Integer pcsReceived;

    private String status;

}
