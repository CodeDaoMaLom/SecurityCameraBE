package tech.thanhpham.homemanagementbe.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class accountController {

    @GetMapping("/hello")
    public String getCurrentAccount(){
        return "ok";
    }
}
