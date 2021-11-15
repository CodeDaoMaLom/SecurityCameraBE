package tech.thanhpham.homemanagementbe.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.thanhpham.homemanagementbe.Service.MailWarningService;
import tech.thanhpham.homemanagementbe.Service.VideoStreamingService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class VideoStreamingController {
    private final VideoStreamingService videoStreamService;
    private final MailWarningService mailWarningService;

    @RequestMapping(path = "/video/{date}/{name}", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(@PathVariable String name, @PathVariable String date) throws IOException {
        return videoStreamService.VideoStreaming(date, name);
    }

    @GetMapping("/api/video")
    public String VideoUploader(@RequestParam String name, @RequestParam String flag) throws MessagingException, UnsupportedEncodingException {
        videoStreamService.VideoUploader(name, flag);
        return "OK";
    }

    @GetMapping("/api/testmail")
    public String TestMail(@RequestParam String mail, @RequestParam String link) throws MessagingException, UnsupportedEncodingException {
        mailWarningService.sendMail(mail, link);
        return "ok";
    }
}
