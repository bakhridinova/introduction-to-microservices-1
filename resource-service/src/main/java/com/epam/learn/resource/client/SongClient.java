package com.epam.learn.resource.client;

import com.epam.learn.resource.config.SongProperties;
import com.epam.learn.song.dto.CreateSongMetadataRequest;
import com.epam.learn.song.dto.DeleteSongMetadataBulkResponse;
import com.epam.learn.song.dto.SongMetadataResponse;
import java.net.URI;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class SongClient {
    private final SongProperties songProperties;
    private final RestTemplate restTemplate;

    public SongMetadataResponse saveSongMetadata(CreateSongMetadataRequest songCreateRequest) {
        String path = songProperties.baseUrl() + songProperties.getSaveSongMetadataPath();
        RequestEntity<CreateSongMetadataRequest> requestEntity = new RequestEntity<>(
            songCreateRequest, HttpMethod.POST, URI.create(path)
        );
        ResponseEntity<SongMetadataResponse> responseEntity = restTemplate
            .exchange(requestEntity, SongMetadataResponse.class);
        return responseEntity.getBody();
    }

    public DeleteSongMetadataBulkResponse deleteSongMetadataBulk(String ids) {
        String path = songProperties.baseUrl() + songProperties.getDeleteSongMetadataBulkPath();
        URI uri = UriComponentsBuilder.fromUriString(path)
            .queryParam("ids", ids)
            .build()
            .toUri();
        RequestEntity<Object> requestEntity = new RequestEntity<>(HttpMethod.DELETE, uri);
        ResponseEntity<DeleteSongMetadataBulkResponse> responseEntity = restTemplate
            .exchange(requestEntity, DeleteSongMetadataBulkResponse.class);
        return responseEntity.getBody();
    }
}
