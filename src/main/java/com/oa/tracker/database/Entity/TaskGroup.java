package com.oa.tracker.database.Entity;

import com.oa.tracker.database.categories.Category;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="task_group")
@Data
public class TaskGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    public String name;

    @Column
    private String description;

    @Column
    private Category category;

    @OneToMany(mappedBy = "taskGroup")
    private List<Task> task = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public TaskGroup(){

    }
    public TaskGroup(String name, String description, Category category){
        this.name = name;
        this.description = description;
        this.category = category;
    }

}
