package com.epam.learn.resource.dto;

import java.util.List;

public record DeleteResourceBulkResponse(
    List<Integer> ids
) {
}
