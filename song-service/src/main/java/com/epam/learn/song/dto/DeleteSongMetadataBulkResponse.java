package com.epam.learn.song.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DeleteSongMetadataBulkResponse(
    List<UUID> ids
) {
}
