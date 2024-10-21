package com.epam.learn.resource.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;
    private byte[] data;
    private String description;
}


