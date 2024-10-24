package com.epam.learn.song.service;

import com.epam.learn.song.dto.CreateSongMetadataRequest;
import com.epam.learn.song.dto.CreateSongMetadataResponse;
import com.epam.learn.song.dto.DeleteSongMetadataBulkResponse;
import com.epam.learn.song.dto.SongMetadataResponse;
import com.epam.learn.song.exception.NotFoundException;
import com.epam.learn.song.exception.ValidationException;
import com.epam.learn.song.model.SongMetadata;
import com.epam.learn.song.repository.SongRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SongService {
    private final ObjectMapper objectMapper;
    private final SongRepository songRepository;

    @Transactional
    public CreateSongMetadataResponse createSong(CreateSongMetadataRequest createRequest) throws ValidationException {
        validateSong(createRequest);
        SongMetadata songMetadata = objectMapper
            .convertValue(createRequest, SongMetadata.class);
        songRepository.save(songMetadata);
        return new CreateSongMetadataResponse(songMetadata.getId().toString());
    }

    public SongMetadataResponse getSongById(String id) {
        SongMetadata song = songRepository.findById(Integer.parseInt(id))
            .orElseThrow(() -> new NotFoundException("Song not found with id: " + id));

        return objectMapper.convertValue(song, SongMetadataResponse.class);
    }

    @Transactional
    public DeleteSongMetadataBulkResponse deleteSongs(String ids) {
        List<Integer> idList = Arrays.stream(ids.split(","))
            .map(Integer::parseInt)
            .toList();
        songRepository.deleteAllById(idList);
        return new DeleteSongMetadataBulkResponse(idList);
    }

    private void validateSong(CreateSongMetadataRequest song) throws ValidationException {
        if (song.name() == null || song.artist() == null) {
            throw new ValidationException("Song name and artist are required");
        }
    }
}