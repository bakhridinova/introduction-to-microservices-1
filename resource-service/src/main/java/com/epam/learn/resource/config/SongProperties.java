package com.epam.learn.resource.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "client.song")
public class SongProperties {
    private String host;
    private String port;
    private String saveSongMetadataPath;
    private String getSongMetadataByIdPath;
    private String deleteSongMetadataBulkPath;

    public String baseUrl() {
        return host + ":" + port;
    }
}
