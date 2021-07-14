package tech.thanhpham.homemanagementbe.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.thanhpham.homemanagementbe.DTO.imageVerifyDTO;
import tech.thanhpham.homemanagementbe.DTO.imageVerifyRequest;
import tech.thanhpham.homemanagementbe.DTO.imageVerifyResponse;
import tech.thanhpham.homemanagementbe.Service.imageVerifyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("image-verify")
public class imageVerifyController {
    private final imageVerifyService imageVerifyService;

    @PostMapping("")
    public ResponseEntity<?> imageVerify(@RequestBody imageVerifyRequest imageVerifyRequest) {
        imageVerifyDTO imageVerifyDTO = imageVerifyService.imageVerify(imageVerifyRequest);
        return ResponseEntity.ok(imageVerifyDTO);
    }

}
