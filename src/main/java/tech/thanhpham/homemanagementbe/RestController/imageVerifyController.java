package tech.thanhpham.homemanagementbe.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.thanhpham.homemanagementbe.DTO.FacialSetupDTO;
import tech.thanhpham.homemanagementbe.DTO.formDataVerifyDTO;
import tech.thanhpham.homemanagementbe.DTO.imageVerifyDTO;
import tech.thanhpham.homemanagementbe.DTO.imageVerifyRequest;
import tech.thanhpham.homemanagementbe.Service.VideoStreamingService;
import tech.thanhpham.homemanagementbe.Service.imageVerifyService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/image-verify")
public class imageVerifyController {
    private final imageVerifyService imageVerifyService;

    @PostMapping("verify")
    public ResponseEntity<?> imageVerify(@RequestBody imageVerifyRequest imageVerifyRequest) {
        imageVerifyDTO imageVerifyDTO = imageVerifyService.imageVerify(imageVerifyRequest);
        return ResponseEntity.ok(imageVerifyDTO);
    }

    @PostMapping("async-verify")
    public ResponseEntity<?> imageVerifyAsync(@RequestBody imageVerifyRequest imageVerifyRequest){
        imageVerifyService.imageVerifyAsync(imageVerifyRequest);
        return ResponseEntity.ok("Ok");
    }

    @PostMapping("setup")
    public ResponseEntity<?> imageSetup(@RequestBody imageVerifyRequest imageVerifyRequest) {
        return imageVerifyService.imageSetup(imageVerifyRequest);
    }

    @GetMapping("get-all-image-verify")
    public List<imageVerifyDTO> imageVerifyDTOList() {
        return imageVerifyService.getImageVerifyDTOList();
    }

    @GetMapping("/get-image/{imagePath}")
    public ResponseEntity<byte[]> getImage(@PathVariable("imagePath") String imagePath) {
        return imageVerifyService.getImage(imagePath);
    }
    @GetMapping("/get-image-setup/{path}/{imagePath}/{filename}")
    public ResponseEntity<byte[]> getImageSetup(@PathVariable("path") String path, @PathVariable("imagePath") String imagePath, @PathVariable("filename") String filename) {
        return imageVerifyService.getImageSetup(imagePath, filename);
    }
    @PostMapping(value = "/verify-file", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public imageVerifyDTO setupWithFiles(@ModelAttribute formDataVerifyDTO formDataVerifyDTO) throws IOException {
        return  imageVerifyService.verifyFromMultiPath(formDataVerifyDTO);
    }

    @PostMapping(value = "/setup-file", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> verifyWithFiles(@ModelAttribute formDataVerifyDTO formDataVerifyDTO) throws IOException {
        return  imageVerifyService.setupFromMultiPath(formDataVerifyDTO);
    }
}
