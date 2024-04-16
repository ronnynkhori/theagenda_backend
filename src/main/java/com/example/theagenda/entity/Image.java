package com.example.theagenda.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob  // Use this to indicate a Large Object
    @Column(columnDefinition = "TEXT")  // Ensures the database uses a text column type
    private String base64;  // This will store the Base64 encoded image data
}