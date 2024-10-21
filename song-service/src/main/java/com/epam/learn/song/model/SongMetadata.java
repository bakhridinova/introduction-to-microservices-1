package com.epam.learn.song.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "song-metadata")
public class SongMetadata {
    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;
    private String name;
    private String artist;
    private String album;
    private String length;
    private String year;
}