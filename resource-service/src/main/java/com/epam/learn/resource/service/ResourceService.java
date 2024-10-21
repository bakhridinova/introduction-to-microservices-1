
package com.epam.learn.resource.service;

import com.epam.learn.resource.client.SongClient;
import com.epam.learn.resource.dto.CreateResourceResponse;
import com.epam.learn.resource.dto.ResourceResponse;
import com.epam.learn.resource.exception.NotFoundException;
import com.epam.learn.resource.model.Resource;
import com.epam.learn.resource.repository.ResourceRepository;
import com.epam.learn.resource.util.MetadataExtractor;
import com.epam.learn.song.dto.SongMetadataResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private final SongClient songClient;
    private final ObjectMapper objectMapper;
    private final ResourceRepository resourceRepository;

    @Transactional
    public CreateResourceResponse createResource(byte[] data) {
        Resource resource = new Resource();
        resource.setData(data);
        resourceRepository.save(resource);

        SongMetadataResponse songMetadata = songClient
            .saveSongMetadata(MetadataExtractor.extractMetadata(resource.getId(), data));
        resource.setDescription(songMetadata.name());

        return new CreateResourceResponse(resource.getId().toString());
    }

    public ResourceResponse getResourceById(String id) throws NotFoundException {
        Resource resource = resourceRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new NotFoundException("Resource not found with id: " + id));
        return objectMapper.convertValue(resource, ResourceResponse.class);
    }

    @Transactional
    public void deleteResources(String ids) {
        List<UUID> idList = Arrays.stream(ids.split(","))
            .map(UUID::fromString)
            .toList();
        songClient.deleteSongMetadataBulk(ids);
        resourceRepository.deleteAllById(idList);
    }
}
