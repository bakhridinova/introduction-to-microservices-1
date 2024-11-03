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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SongService {
    private static final int MAX_ALLOWED_IDS_TO_DELETE = 200;
    private static final Pattern VALID_ID_PATTERN = Pattern.compile("^\\d+$");

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
        if (!VALID_ID_PATTERN.matcher(id).matches()) {
            throw new ValidationException("Invalid ID format: ID should be a positive integer");
        }

        int idAsInteger = Integer.parseInt(id);
        if (idAsInteger == 0) {
            throw new ValidationException("Invalid ID format: ID should be a positive integer");
        }
        SongMetadata song = songRepository.findById(idAsInteger)
            .orElseThrow(() -> new NotFoundException("Song not found with id: " + id));

        return objectMapper.convertValue(song, SongMetadataResponse.class);
    }

    @Transactional
    public DeleteSongMetadataBulkResponse deleteSongs(String ids) {
        List<Integer> idList = new ArrayList<>();
        try {
            Arrays.stream(ids.split(",")).forEach(id -> idList.add(Integer.parseInt(id)));
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format: IDs should be integers");
        }

        if (idList.size() > MAX_ALLOWED_IDS_TO_DELETE) {
            throw new ValidationException("Number of ids should not exceed " + MAX_ALLOWED_IDS_TO_DELETE);
        }
        songRepository.deleteAllById(idList);
        return new DeleteSongMetadataBulkResponse(idList);
    }

    private void validateSong(CreateSongMetadataRequest song) throws ValidationException {
        if (song.name() == null || song.artist() == null) {
            throw new ValidationException("Song name and artist are required");
        }

        if (song.year() != null && !song.year().matches("^[12][0-9]{3}$")) {
            throw new ValidationException("Year must be a four-digit number starting with 1 or 2");
        }

        if (song.length() != null && !song.length().matches("^\\d{2}:\\d{2}$")) {
            throw new ValidationException("Length must be in the format MM:SS");
        }
    }
}