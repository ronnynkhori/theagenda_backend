package com.example.theagenda.entity;

import com.example.theagenda.enums.RequestStatus;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Tasks")
@EntityListeners(AuditingEntityListener.class)
public class Task extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String description;

    private Integer userId;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
