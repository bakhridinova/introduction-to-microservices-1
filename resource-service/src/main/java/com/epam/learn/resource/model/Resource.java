package com.epam.learn.resource.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ResourceSequenceId")
    @SequenceGenerator(name = "ResourceSequenceId", sequenceName = "resources_seq", allocationSize = 1)
    @Column(updatable = false, nullable = false)
    private Integer id;
    private byte[] data;
    private String description;
}


