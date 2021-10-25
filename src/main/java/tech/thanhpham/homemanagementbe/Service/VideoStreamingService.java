package tech.thanhpham.homemanagementbe.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tech.thanhpham.homemanagementbe.Entity.Video;
import tech.thanhpham.homemanagementbe.Repository.VideoRepository;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class VideoStreamingService {
    private final VideoRepository videoRepository;
    private final MailWarningService mailWarningService;
    private final SettingsService settingsService;
    @Value("${tech.thanhpham.video-path}")
    private String videoPath;

    public ResponseEntity VideoStreaming(String date, String name) throws IOException {
        File file = new File(videoPath + File.separator + date + File.separator + name);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);

    }

    @Async
    public void VideoUploader(String videoDTO) throws MessagingException, UnsupportedEncodingException {
        if (settingsService.getVideoRecorder()) {
            LocalDate date = LocalDate.now();
            File f = new File(videoPath + File.separator + date + File.separator + videoDTO);
            if (f.exists() && !f.isDirectory()) {
                Video video = new Video(videoDTO, LocalDateTime.now(), true);
                videoRepository.save(video);
                if (settingsService.getMailNotification()) {
                    mailWarningService.sendAllMail("http://localhost:8000/video/" + videoDTO);
                }
            }
        }

    }
}
