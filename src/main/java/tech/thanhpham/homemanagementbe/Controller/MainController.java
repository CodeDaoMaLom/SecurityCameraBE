package tech.thanhpham.homemanagementbe.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tech.thanhpham.homemanagementbe.Service.SettingsService;

@Controller
@RequiredArgsConstructor
public class MainController implements ErrorController {
    private final SettingsService settingsService;

    @GetMapping(value = {"/"})
    public String homePage(Model model) {
        String videoMode = settingsService.getVideoMode();
        model.addAttribute("videoMode", videoMode);
        return "index";
    }

    @GetMapping("/error")
    public String handleError() {
        return "404";
    }

    @GetMapping(value = {"/login"})
    public String loginPage() {
        return "login";
    }
}
