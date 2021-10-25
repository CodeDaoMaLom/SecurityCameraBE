package tech.thanhpham.homemanagementbe.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.thanhpham.homemanagementbe.DTO.passcodeDTO;
import tech.thanhpham.homemanagementbe.Service.passcodeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/passcode")
public class passcodeController {

    private final passcodeService passcodeService;
    @GetMapping("/get")
    public passcodeDTO getPasscode(){
        return passcodeService.getPasscode();
    }

    @PostMapping("/set")
    public ResponseEntity<?> getCurrentAccount(@RequestBody passcodeDTO passcodeDTO) {
        passcodeService.setPasscode(passcodeDTO.getPasscode());
        return ResponseEntity.ok("Sucessfully!");
    }
}
