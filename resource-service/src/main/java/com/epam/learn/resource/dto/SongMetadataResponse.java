package com.epam.learn.resource.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SongMetadataResponse(
    Integer id,
    String name,
    String artist,
    String album,
    String length,
    Integer resourceId,
    String year
) {
}