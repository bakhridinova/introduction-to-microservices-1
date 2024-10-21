package com.epam.learn.song.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateSongMetadataResponse(
    String id
) {
}