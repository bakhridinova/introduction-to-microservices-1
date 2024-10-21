
package com.epam.learn.resource.service;

import com.epam.learn.resource.client.SongClient;
import com.epam.learn.resource.dto.CreateResourceResponse;
import com.epam.learn.resource.dto.DeleteResourceBulkResponse;
import com.epam.learn.resource.exception.NotFoundException;
import com.epam.learn.resource.exception.ValidationException;
import com.epam.learn.resource.model.Resource;
import com.epam.learn.resource.repository.ResourceRepository;
import com.epam.learn.resource.util.MetadataExtractor;
import com.epam.learn.song.dto.SongMetadataResponse;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private static final int MAX_ALLOWED_IDS_TO_DELETE = 200;

    private final SongClient songClient;
    private final ResourceRepository resourceRepository;

    @Transactional
    public CreateResourceResponse createResource(byte[] data) {
        Resource resource = new Resource();
        resource.setData(data);
        resourceRepository.save(resource);

        SongMetadataResponse songMetadata = songClient.saveSongMetadata(
            MetadataExtractor.extractMetadata(resource.getId(), data));
        resource.setDescription(songMetadata.name());

        return new CreateResourceResponse(resource.getId().toString());
    }

    public byte[] getResourceById(String id) throws NotFoundException {
        Resource resource = resourceRepository.findById(Integer.parseInt(id))
            .orElseThrow(() -> new NotFoundException("Resource not found with id: " + id));
        return resource.getData();
    }

    @Transactional
    public DeleteResourceBulkResponse deleteResources(String ids) {
        List<Integer> idList = Arrays.stream(ids.split(","))
            .map(Integer::parseInt)
            .toList();

        if (idList.size() > MAX_ALLOWED_IDS_TO_DELETE) {
            throw new ValidationException("Number of ids should not exceed " + MAX_ALLOWED_IDS_TO_DELETE);
        }
        songClient.deleteSongMetadataBulk(ids);
        resourceRepository.deleteAllById(idList);
        return new DeleteResourceBulkResponse(idList);
    }
}
