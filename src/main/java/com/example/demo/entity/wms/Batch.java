package com.example.demo.entity.wms;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "batch")
@NoArgsConstructor
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private LocalDate mfgDate;
    private LocalDate ibdDate;
    private LocalDate slDate;
    private LocalDate expDate;

}
