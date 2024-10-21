package com.epam.learn.resource.util;

import com.epam.learn.resource.model.Resource;
import com.epam.learn.song.dto.CreateSongMetadataRequest;
import java.util.UUID;
import lombok.experimental.UtilityClass;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@UtilityClass
public class MetadataExtractor {

    public static CreateSongMetadataRequest extractMetadata(UUID resourceId, byte[] data) {
        try (InputStream input = new ByteArrayInputStream(data)) {
            ContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            ParseContext parseContext = new ParseContext();
            new Mp3Parser().parse(input, handler, metadata, parseContext);

            String title = metadata.get(TikaCoreProperties.TITLE);
            String artist = metadata.get(TikaCoreProperties.CREATOR);
            String album = metadata.get(TikaCoreProperties.SOURCE);
            String length = metadata.get("xmpDM:duration");
            String year = metadata.get(TikaCoreProperties.CREATED);

            return new CreateSongMetadataRequest(
                resourceId.toString(),
                title,
                artist,
                album,
                length,
                year
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract metadata", e);
        }
    }
}