package com.epam.learn.song.controller;

import com.epam.learn.song.dto.CreateSongMetadataRequest;
import com.epam.learn.song.dto.CreateSongMetadataResponse;
import com.epam.learn.song.dto.DeleteSongMetadataBulkResponse;
import com.epam.learn.song.dto.SongMetadataResponse;
import com.epam.learn.song.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SongController implements SongApi {
    private final SongService songService;

    @Override
    public ResponseEntity<CreateSongMetadataResponse> saveSongMetadata(CreateSongMetadataRequest createRequest) {
        CreateSongMetadataResponse createdSong = songService.createSong(createRequest);
        return new ResponseEntity<>(createdSong, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SongMetadataResponse> getSongMetadataById(String id) {
        SongMetadataResponse song = songService.getSongById(id);
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DeleteSongMetadataBulkResponse> deleteSongMetadataBulk(String ids) {
        return new ResponseEntity<>(songService.deleteSongs(ids), HttpStatus.OK);
    }
}