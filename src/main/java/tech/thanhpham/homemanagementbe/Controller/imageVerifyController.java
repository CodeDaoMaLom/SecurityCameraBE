package tech.thanhpham.homemanagementbe.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.thanhpham.homemanagementbe.DTO.imageVerifyDTO;
import tech.thanhpham.homemanagementbe.DTO.imageVerifyRequest;
import tech.thanhpham.homemanagementbe.Service.imageVerifyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("image-verify")
public class imageVerifyController {
    private final imageVerifyService imageVerifyService;

    @PostMapping("verify")
    public ResponseEntity<?> imageVerify(@RequestBody imageVerifyRequest imageVerifyRequest) {
        imageVerifyDTO imageVerifyDTO = imageVerifyService.imageVerify(imageVerifyRequest);
        return ResponseEntity.ok(imageVerifyDTO);
    }

    @PostMapping("setup")
    public ResponseEntity<?> imageSetup(@RequestBody imageVerifyRequest imageVerifyRequest) {
        return imageVerifyService.imageSetup(imageVerifyRequest);
    }

    @GetMapping("get-all-image-verify")
    public List<imageVerifyDTO> imageVerifyDTOList() {
        return imageVerifyService.getImageVerifyDTOList();
    }

    @PostMapping("/get-images")
    public ResponseEntity<byte[]> getImage(@RequestBody imageVerifyDTO imageVerifyDTO) {
        return imageVerifyService.getImage(imageVerifyDTO.getPath());
    }


}
