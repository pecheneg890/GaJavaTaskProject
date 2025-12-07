package com.oa.tracker.database.Entity;

import com.oa.tracker.database.categories.Complexity;
import com.oa.tracker.database.categories.Priority;
import com.oa.tracker.database.categories.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="task")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    public String name;

    @Column
    public String description;

    @Column
    public Complexity complexity;

    @Column
    private Date deadline;

    @Column
    private Priority priority;

    @Column
    private Status status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "task_group_id", nullable = false)
    private TaskGroup taskGroup;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Task(){

    }
    public Task(String name, Date deadline, Priority priority, Status status){
        this.name = name;
        this.deadline = deadline;
        this.priority = priority;
        this.status = status;
    }
}
