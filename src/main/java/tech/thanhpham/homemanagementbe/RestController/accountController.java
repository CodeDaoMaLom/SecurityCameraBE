package tech.thanhpham.homemanagementbe.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.thanhpham.homemanagementbe.DTO.accountDTO;
import tech.thanhpham.homemanagementbe.Security.LoginRequest;
import tech.thanhpham.homemanagementbe.Service.accountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/account")
public class accountController {

    private final AuthenticationManager authenticationManager;
    private final accountService accountService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        accountDTO accountDTO = accountService.findByUsername(authenticationRequest.getUsername()).toDTO();
        return ResponseEntity.ok(accountDTO);
    }

    @GetMapping("/hello")
    public String getCurrentAccount() {
        return "ok";
    }
}
