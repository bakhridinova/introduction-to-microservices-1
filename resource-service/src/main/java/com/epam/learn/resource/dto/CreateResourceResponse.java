package com.epam.learn.resource.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateResourceResponse(
    String id
) {
}
