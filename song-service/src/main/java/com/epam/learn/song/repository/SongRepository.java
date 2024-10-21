package com.epam.learn.song.repository;

import com.epam.learn.song.model.SongMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<SongMetadata, Integer> {
}