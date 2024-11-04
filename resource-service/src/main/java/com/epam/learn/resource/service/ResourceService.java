
package com.epam.learn.resource.service;

import com.epam.learn.resource.client.SongClient;
import com.epam.learn.resource.dto.CreateResourceResponse;
import com.epam.learn.resource.dto.DeleteResourceBulkResponse;
import com.epam.learn.resource.dto.SongMetadataResponse;
import com.epam.learn.resource.exception.NotFoundException;
import com.epam.learn.resource.exception.ValidationException;
import com.epam.learn.resource.model.Resource;
import com.epam.learn.resource.repository.ResourceRepository;
import com.epam.learn.resource.util.MetadataExtractor;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private static final int MAX_ALLOWED_IDS_LENGTH = 200;
    private static final Pattern VALID_ID_PATTERN = Pattern.compile("^\\d+$");

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

    public byte[] getResourceById(String id) {
        if (!VALID_ID_PATTERN.matcher(id).matches()) {
            throw new ValidationException("Invalid ID format: ID should be a positive integer");
        }

        int idAsInteger = Integer.parseInt(id);
        if (idAsInteger == 0) {
            throw new ValidationException("Invalid ID format: ID should be a positive integer");
        }

        Resource resource = resourceRepository.findById(idAsInteger)
            .orElseThrow(() -> new NotFoundException("Resource not found with id: " + id));
        return resource.getData();
    }

    @Transactional
    public DeleteResourceBulkResponse deleteResources(String ids) {
        if (ids.length() > MAX_ALLOWED_IDS_LENGTH) {
            throw new ValidationException("The length of ids should not exceed " + MAX_ALLOWED_IDS_LENGTH);
        }

        List<Integer> idList = new ArrayList<>();
        try {
            Arrays.stream(ids.split(",")).forEach(id -> idList.add(Integer.parseInt(id)));
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format: IDs should be integers");
        }

        songClient.deleteSongMetadataBulk(ids);
        resourceRepository.deleteAllById(idList);
        return new DeleteResourceBulkResponse(idList);
    }
}
