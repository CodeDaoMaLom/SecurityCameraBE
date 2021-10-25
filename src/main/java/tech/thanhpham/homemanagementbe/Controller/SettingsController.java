package tech.thanhpham.homemanagementbe.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import tech.thanhpham.homemanagementbe.Entity.MailWarning;
import tech.thanhpham.homemanagementbe.Service.MailWarningService;
import tech.thanhpham.homemanagementbe.Service.SettingsService;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class SettingsController {
    private final SettingsService settingsService;
    private final MailWarningService mailWarningService;

    @GetMapping("/settings")
    public String index(Model model) {
        Map<String, Boolean> settingsList = settingsService.getConfig();
        model.addAttribute("settings", settingsList);
        List<MailWarning> mailWarningList = mailWarningService.getAllMail();
        model.addAttribute("maillists", mailWarningList);
        return "setting";
    }

    @GetMapping("/settings/{key}")
    public String setAI(@PathVariable String key) {
        settingsService.setConfig(key);
        return "redirect:/settings";
    }


    @GetMapping("/settings/{id}/delete")
    public String delete(@PathVariable String id) {
        mailWarningService.deleteMail(id);
        return "redirect:/settings";
    }

    @GetMapping("/settings/add")
    public String addMail(@RequestParam String mail) {
        mailWarningService.addMail(mail);
        return "redirect:/settings";
    }

    @GetMapping("/settings/{id}/edit")
    public String addMail(@PathVariable String id, @RequestParam("new-email") String mail) {
        mailWarningService.editMail(id, mail);
        return "redirect:/settings";
    }

}
