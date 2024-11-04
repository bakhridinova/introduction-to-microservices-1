package com.epam.learn.song.model;

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
@Table(name = "song_metadata")
public class SongMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SongMetadataSequenceId")
    @SequenceGenerator(name = "SongMetadataSequenceId", sequenceName = "song_metadata_seq", allocationSize = 1)
    @Column(updatable = false, nullable = false)
    private Integer id;
    private String name;
    private String artist;
    private String album;
    private String length;
    private String year;
    private Integer resourceId;
}