package com.oshovskii.server.models;

import com.oshovskii.common.dto.enums.TaskType;
import lombok.*;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "tasks")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TaskType type;

    @Column(name = "username")
    private String username;

    @ManyToMany
    @JoinTable(name = "tasks_statuses",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "status_id"))
    private Collection<Status> statuses;
}
