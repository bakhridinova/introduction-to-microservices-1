package com.epam.learn.resource.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "client.song")
public class SongClientConfig {
    private String host;
    private String saveSongMetadataPath;
    private String getSongMetadataByIdPath;
    private String deleteSongMetadataBulkPath;
}
