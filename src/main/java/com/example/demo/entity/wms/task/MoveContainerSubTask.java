package com.example.demo.entity.wms.task;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "move_container_sub_task")
@NoArgsConstructor
public class MoveContainerSubTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long taskId;

    private String containerId;

    private String fromContainer;

    private String toContainer;

    private String status;

    private Long operatorId;
}
