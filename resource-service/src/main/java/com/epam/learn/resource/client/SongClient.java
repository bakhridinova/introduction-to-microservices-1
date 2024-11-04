package com.epam.learn.resource.client;

import com.epam.learn.resource.config.SongClientConfig;
import com.epam.learn.resource.dto.CreateSongMetadataRequest;
import com.epam.learn.resource.dto.DeleteSongMetadataBulkResponse;
import com.epam.learn.resource.dto.SongMetadataResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class SongClient {
    private final SongClientConfig songClientConfig;
    private final RestTemplate restTemplate;

    public SongMetadataResponse saveSongMetadata(CreateSongMetadataRequest songCreateRequest) {
        String path = songClientConfig.getHost() + songClientConfig.getSaveSongMetadataPath();
        RequestEntity<CreateSongMetadataRequest> requestEntity = new RequestEntity<>(
            songCreateRequest, HttpMethod.POST, URI.create(path)
        );
        ResponseEntity<SongMetadataResponse> responseEntity = restTemplate
            .exchange(requestEntity, SongMetadataResponse.class);
        return responseEntity.getBody();
    }

    public DeleteSongMetadataBulkResponse deleteSongMetadataBulk(String ids) {
        String path = songClientConfig.getHost() + songClientConfig.getDeleteSongMetadataBulkPath();
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
