package com.epam.learn.song.controller;

import com.epam.learn.song.dto.CreateSongMetadataRequest;
import com.epam.learn.song.dto.CreateSongMetadataResponse;
import com.epam.learn.song.dto.DeleteSongMetadataBulkResponse;
import com.epam.learn.song.dto.SongMetadataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/songs")
public interface SongApi {
    @PostMapping
    @Operation(
        summary = "Save new song metadata", responses = {
        @ApiResponse(
            description = "ID of created song metadata", responseCode = "200",
            content = @Content(schema = @Schema(implementation = SongMetadataResponse.class))
        ),
        @ApiResponse(description = "Bad request if validation fails", responseCode = "400")
    }
    )
    ResponseEntity<CreateSongMetadataResponse> saveSongMetadata(
        @RequestBody CreateSongMetadataRequest createRequest
    );

    @GetMapping("/{id}")
    @Operation(
        summary = "Get song metadata by ID", responses = {
        @ApiResponse(
            description = "The song metadata", responseCode = "200",
            content = @Content(schema = @Schema(implementation = SongMetadataResponse.class))
        ),
        @ApiResponse(description = "Not found if the song metadata does not exist", responseCode = "404")
    }
    )
    ResponseEntity<SongMetadataResponse> getSongMetadataById(
        @Parameter(description = "ID of the song to get") @PathVariable String id
    );

    @DeleteMapping
    @Operation(
        summary = "Delete song metadata by IDs", responses = {
        @ApiResponse(
            description = "IDs of deleted song metadata", responseCode = "200",
            content = @Content(schema = @Schema(implementation = Map.class))
        ),
        @ApiResponse(description = "Internal server error", responseCode = "500")
    }
    )
    ResponseEntity<DeleteSongMetadataBulkResponse> deleteSongMetadataBulk(
        @Parameter(description = "Comma-separated IDs of song metadata to delete") @RequestParam("ids") String ids
    );
}
