package com.example.theagenda.model;

import lombok.Data;

import java.util.List;

@Data
public class TaskDTO {
    private Integer id;
    private String description;
    private String phoneNumber;
    private String firstname;
    private String lastname;
    // other fields
    private List<String> imageBase64s;
}
