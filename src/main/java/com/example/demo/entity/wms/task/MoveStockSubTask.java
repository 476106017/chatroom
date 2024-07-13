package com.example.demo.entity.wms.task;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "move_container_sub_task")
@NoArgsConstructor
public class MoveStockSubTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long taskId;

    private String sourceStockId;

    private String toContainer;

    private String movedPcs;

    private String backContainer;

    private String backedPcs;

    private String status;

    private Long operatorId;
}
