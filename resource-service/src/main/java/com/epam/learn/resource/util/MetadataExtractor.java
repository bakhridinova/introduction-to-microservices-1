package com.epam.learn.resource.util;

import com.epam.learn.resource.dto.CreateSongMetadataRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import lombok.experimental.UtilityClass;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

@UtilityClass
public class MetadataExtractor {

    public static CreateSongMetadataRequest extractMetadata(Integer resourceId, byte[] data) {
        try (InputStream input = new ByteArrayInputStream(data)) {
            ContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            ParseContext parseContext = new ParseContext();
            new Mp3Parser().parse(input, handler, metadata, parseContext);

            String title = metadata.get(TikaCoreProperties.TITLE);
            String artist = metadata.get(TikaCoreProperties.CREATOR);
            String album = metadata.get(TikaCoreProperties.SOURCE);
            String rawLength = metadata.get("xmpDM:duration");
            String length = formatDuration(rawLength);
            String year = parseYear(metadata.get(TikaCoreProperties.CREATED));

            return new CreateSongMetadataRequest(
                title,
                artist,
                album,
                length,
                resourceId,
                year
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract metadata", e);
        }
    }

    private static String formatDuration(String rawDuration) {
        if (rawDuration != null && !rawDuration.isEmpty()) {
            long totalSeconds = Double.valueOf(rawDuration).longValue();
            long minutes = TimeUnit.SECONDS.toMinutes(totalSeconds);
            long seconds = totalSeconds - TimeUnit.MINUTES.toSeconds(minutes);
            return String.format("%02d:%02d", minutes, seconds);
        }
        return null;
    }

    private static String parseYear(String rawDate) {
        if (rawDate != null && rawDate.length() >= 4) {
            return rawDate.substring(0, 4);
        }
        return null;
    }
}