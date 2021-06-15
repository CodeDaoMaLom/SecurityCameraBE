package tech.thanhpham.homemanagementbe.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.thanhpham.homemanagementbe.Entity.Account;
import tech.thanhpham.homemanagementbe.Security.JwtUtils;
import tech.thanhpham.homemanagementbe.Security.LoginRequest;
import tech.thanhpham.homemanagementbe.Service.accountService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class loginController {
    private final accountService accountService;
    private final JwtUtils jwtTools;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest authenticationRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        Account account = accountService.findByUsername(authenticationRequest.getUsername());
        final String token = jwtTools.generateToken(account.getId());

        return ResponseEntity.ok(token);
    }
}
