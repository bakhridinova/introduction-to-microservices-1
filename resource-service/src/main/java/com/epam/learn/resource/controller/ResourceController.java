

package com.epam.learn.resource.controller;

import com.epam.learn.resource.dto.CreateResourceResponse;
import com.epam.learn.resource.dto.DeleteResourceBulkResponse;
import com.epam.learn.resource.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ResourceController implements ResourceApi {
    private final ResourceService resourceService;

    @Override
    public ResponseEntity<CreateResourceResponse> createResource(byte[] data) {
        CreateResourceResponse createdResource = resourceService.createResource(data);
        return new ResponseEntity<>(createdResource, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getResource(String id) {
        return new ResponseEntity<>(resourceService.getResourceById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DeleteResourceBulkResponse> deleteResourceBulk(String ids) {
        return new ResponseEntity<>(resourceService.deleteResources(ids), HttpStatus.OK);
    }
}