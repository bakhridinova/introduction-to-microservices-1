

package com.epam.learn.resource.controller;

import com.epam.learn.resource.dto.CreateResourceResponse;
import com.epam.learn.resource.dto.DeleteResourceBulkResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/resources")
public interface ResourceApi {
    @PostMapping(consumes = "audio/mpeg")
    @Operation(
        summary = "Create a new resource", responses = {
        @ApiResponse(
            description = "ID of created resource", responseCode = "200",
            content = @Content(schema = @Schema(implementation = CreateResourceResponse.class))
        ),
        @ApiResponse(description = "Bad request if validation fails", responseCode = "400")
    }
    )
    ResponseEntity<CreateResourceResponse> createResource(
        @RequestBody byte[] data
    );

    @GetMapping(value = "/{id}", produces = "audio/mpeg")
    @Operation(
        summary = "Get a resource by ID", responses = {
        @ApiResponse(
            description = "The resource", responseCode = "200",
            content = @Content(schema = @Schema(implementation = byte[].class))
        ),
        @ApiResponse(
            description = "Not found if the resource does not exist", responseCode = "404",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    }
    )
    ResponseEntity<byte[]> getResource(
        @Parameter(description = "ID of the resource to get") @PathVariable String id
    );

    @DeleteMapping
    @Operation(
        summary = "Delete resources by IDs", responses = {
        @ApiResponse(
            description = "Successful deletion", responseCode = "200"
        ),
        @ApiResponse(description = "Internal server error", responseCode = "500",
            content = @Content(schema = @Schema(implementation = DeleteResourceBulkResponse.class))
        ),
    }
    )
    ResponseEntity<DeleteResourceBulkResponse> deleteResourceBulk(
        @Parameter(description = "Comma-separated IDs of resources to delete") @RequestParam("ids") String ids
    );
}