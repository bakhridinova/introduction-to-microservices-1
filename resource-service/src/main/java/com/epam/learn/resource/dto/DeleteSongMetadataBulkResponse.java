package com.epam.learn.resource.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DeleteSongMetadataBulkResponse(
    List<Integer> ids
) {
}
