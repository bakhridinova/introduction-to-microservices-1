package com.epam.learn.song.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateSongMetadataRequest(
    String name,
    String artist,
    String album,
    String length,
    Integer resourceId,
    String year
) {
}