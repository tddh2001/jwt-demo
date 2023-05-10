package com.example.demo.model.db;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String classRoom;
    private String phone;
    private String email;
    private String description;
    @CreationTimestamp
    private LocalDateTime updatedAt;

}
